	package org.cineclark.fileReader;

	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.ArrayList;
	import java.util.Scanner;
	import org.cineclark.datacontainers.Address;
	import org.cineclark.datacontainers.Person;

	public class FlatFileReader {
		
		public ArrayList<Person> readPersons() {
		Scanner sc = null;
			
			try {
				sc = new Scanner(new File("data/Persons.dat"));
				sc.nextLine(); // reads the number of records from the first line
				
				// This Person ArrayList stores the Person objects 
				ArrayList<Person> personList = new ArrayList<Person>();
				
				while(sc.hasNext()) {
					String line = sc.nextLine(); // reads each line starting from 2nd line
					String data[] = line.split(";"); // tokenizes the line and stores in a s
					String array;
					
					// Stores the 4 array elements of each line into strings
					String personCode = data[0];
					String personName = data[1];
					String commaSeperatedAddress= data[2];
					String emailList = data[3];
					
					//Person Name
					String Name[] = personName.split(",");
					String lastName = Name[0];
					String firstName = Name[1];
					
					//Address Sort
					String addressSort[]= commaSeperatedAddress.split(",");
					String street = addressSort[0];
					String city = addressSort[1];
					String state = addressSort[2];
					String zip = addressSort[3];
					String country = addressSort[4];
					
					//Email Sort
					String emailSort[]= commaSeperatedAddress.split(",");
					
					// Creates an Address object
					Address address = new Address(street, city, state, zip, country);
					
					// Creates a Person object
					Person person = new Person(personCode, lastName, firstName, address);
					
					person.setEmail(email);
//					todo

				}
			}
		}