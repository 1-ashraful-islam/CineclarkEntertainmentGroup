package org.cineclark.reports;

import org.cineclark.fileWriter.*;

import java.util.ArrayList;
import java.util.List;

import org.cineclark.datacontainers.*;


public class DataConverter {
	
	
	public static void main(String[] args) {
		
		Person persons1 = new Person(null, null, null, null, null);
		Person persons2 = new Person(null, null, null, null, null);
		
		List<Person> persons = new ArrayList<Person>();
		
		persons.add(persons1);
		persons.add(persons2);
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
		jsonWriter.jsonConverter(persons);
		
		// Create an XML Writer object
		/***
		Write the ArrayList into an XML file
		*/
		
	}

}
