package org.cineclark.fileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.cineclark.datacontainers.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {
	
	public void jsonConverterPerson(List<Person> persons) {
		
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File jsonOutput = new File("data/Persons.json");
		
		PrintWriter jsonPrintWriter = null;
		
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		for(Person aPerson : persons) {
			// Use toJson method to convert Person object into a String
			String personOutput = gson.toJson(aPerson); 
			jsonPrintWriter.write(personOutput + "\n");
		}
		
		jsonPrintWriter.close();
	}
public void jsonConverterCustomer(List<Customer> customers) {
		
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File jsonOutput = new File("data/Customers.json");
		
		PrintWriter jsonPrintWriter = null;
		
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		for(Customer aCustomer : customers) {
			// Use toJson method to convert Person object into a String
			String customerOutput = gson.toJson(aCustomer); 
			jsonPrintWriter.write(customerOutput + "\n");
		}
		
		jsonPrintWriter.close();
	}
public void jsonConverterProducts(List<Product> products) {
	
	//Gson gson = new Gson();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	File jsonOutput = new File("data/Products.json");
	
	PrintWriter jsonPrintWriter = null;
	
	try {
		jsonPrintWriter = new PrintWriter(jsonOutput);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} 
	
	for(Product aProduct : products) {
		// Use toJson method to convert Person object into a String
		String personOutput = gson.toJson(aProduct); 
		jsonPrintWriter.write(personOutput + "\n");
	}
	
	jsonPrintWriter.close();
}

}
