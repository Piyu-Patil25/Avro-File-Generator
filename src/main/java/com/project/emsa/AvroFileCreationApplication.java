package com.project.emsa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AvroFileCreationApplication{

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AvroFileCreationApplication.class, args);
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/emsa?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "password";
        String tableName = "employee_avro";
        String avroSchemaFilePath = "Avro-File-Generator\\src\\main\\resources\\avro_test_file.avsc";
        String avroOutputFilePath = "Avro-File-Generator\\src\\main\\resources\\avro_test_file.avro";
        String tabledataFile = "Avro-File-Generator\\src\\main\\resources\\table.csv";
        
        String[][] tableData = {
	            {"Name", "Age", "City"},
	            {"Jay", "18", "Jalgaon"},
	            {"Rucha", "28", "Mumbai"},
	            {"Shweta", "25", "Pune"}
		};
        try (FileWriter fileWriter = new FileWriter(tabledataFile);
	             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

	            for (String[] row : tableData) {
	                csvPrinter.printRecord((Object[]) row);
	            }

	            System.out.println("Table data has been exported to " + tabledataFile);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
           

            // Define Avro schema based on your data structure
            Schema avroSchema = new Schema.Parser().parse(new File(avroSchemaFilePath));
                       
            // Create a DataFileWriter to write Avro records to a file
            DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(avroSchema);
            
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
            dataFileWriter.setCodec(CodecFactory.snappyCodec()); // Set the desired compression codec
            dataFileWriter.create(avroSchema, new File(avroOutputFilePath));

            while (resultSet.next()) {
            	 GenericRecord avroRecord = new GenericData.Record(avroSchema);
            	 
            	 System.out.println("Emp Id: " + resultSet.getString("EMP_Id"));
            	 avroRecord.put("id", resultSet.getInt("EMP_Id"));
            	 
            	 System.out.println("First Name: " + resultSet.getString("FIRST_NAME"));
            	 avroRecord.put("firstName", resultSet.getString("FIRST_NAME"));
            	 
            	 System.out.println("Last Name: " + resultSet.getString("LAST_NAME"));
            	 avroRecord.put("lastName", resultSet.getString("LAST_NAME"));
            	 
            	 System.out.println("mail Id: " + resultSet.getString("EMAIL_ID"));
            	 avroRecord.put("email", resultSet.getString("EMAIL_ID"));
            	 
            	 dataFileWriter.append(avroRecord);
            	}
            	// Close the data file writer
            	dataFileWriter.close();
            	
            	// Close the database connection
                connection.close();

                System.out.println("Data successfully extracted and saved to " + avroOutputFilePath);
	            System.out.println("Table data has been exported to " + tabledataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
}
