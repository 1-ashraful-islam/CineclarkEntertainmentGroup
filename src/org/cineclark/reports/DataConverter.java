package org.cineclark.reports;

import org.cineclark.fileWriter.*;
import org.cineclark.fileReader.*;

//import java.util.ArrayList;
import java.util.List;

import org.cineclark.datacontainers.*;


public class DataConverter {
	
	
	public static void main(String[] args) {
		
		

		// Create a FlatFileReader object
		/*** 
		 * This object should Read data from the flat file;
		Create objects; and
		Store objects in object ArrayList
		It returns ArrayList of objects
		*/
		FlatFileReader flReader = new FlatFileReader(); //new instance of flatFileReader
		List<Person> persons = flReader.readPersons(); //get the list of persons
		List<Customer> customers = flReader.readCustomers(); //get the list of customers
		List<Product> products = flReader.readProducts(); //get the list of products
		
		// Create a Json Writer object
		/***
		 *Write the ArrayList into a Json file
		 **/
		JsonWriter jsonWriter = new JsonWriter();
		jsonWriter.jsonConverterPerson(persons); //Write the person objects in JSON format
		jsonWriter.jsonConverterCustomer(customers); //Write the customer objects in JSON format
		jsonWriter.jsonConverterProducts(products); //Write the product objects in JSON format
		// Create an XML Writer object
		/***
		Write the ArrayList into an XML file
		*/
		
	}

}
