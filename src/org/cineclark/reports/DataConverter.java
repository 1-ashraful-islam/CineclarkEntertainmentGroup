package org.cineclark.reports;

import org.cineclark.fileWriter.*;

import java.util.ArrayList;
import java.util.List;

import org.cineclark.datacontainers.*;


public class DataConverter {
	
	
	public static void main(String[] args) {
		
		
		List<Person> persons = new ArrayList<Person>(); // TODO add the the persons from the file reader
		

		// Create a FlatFileReader object
		/*** 
		 * This object should Read data from the flat file;
		Create objects; and
		Store objects in object ArrayList
		It returns ArrayList of objects
		*/
		
		// Create a Json Writer object
		/***
		 *Write the ArrayList into a Json file
		 **/
		JsonWriter jsonWriter = new JsonWriter();
		jsonWriter.jsonConverterPerson(persons);
		
		// Create an XML Writer object
		/***
		Write the ArrayList into an XML file
		*/
		
	}

}
