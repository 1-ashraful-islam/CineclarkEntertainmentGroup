package org.cineclark.fileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import org.cineclark.datacontainers.*;
import org.joda.time.DateTime;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XMLWriter {

	//Writing the person
	public void xmlConverterPerson(List<Person> persons) {
		XStream  xstream = new XStream();
		
        File xmlOutput = new File("data/Persons.xml");
		
		PrintWriter xmlPrintWriter = null;
		try {
			xmlPrintWriter = new PrintWriter(xmlOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		xmlPrintWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		
		xstream.alias("person", Person.class); 
		//xstream.alias("email", Person.getEmail().toString()); TODO find alias to change email name
		for(Person aPerson : persons) {
			// Use toXML method to convert Person object into a String
			String personOutput = xstream.toXML(aPerson);
			xmlPrintWriter.write(personOutput + "\n");
		}
		xmlPrintWriter.close();	
	}
	
	//writing the customers
	public void xmlConverterCustomer(List<Customer> customers) {
		XStream  xstream = new XStream();
		
        File xmlOutput = new File("data/Customers.xml");
		
		PrintWriter xmlPrintWriter = null;
		try {
			xmlPrintWriter = new PrintWriter(xmlOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		xmlPrintWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		
		xstream.alias("generalcustomer", GeneralCustomer.class); 
		xstream.alias("studentcustomer", StudentCustomer.class);
		//xstream.alias("email", Person.getEmail().toString()); TODO find alias to change email name
		for(Customer aCustomer : customers) {
			// Use toXML method to convert Person object into a String
			String customerOutput = xstream.toXML(aCustomer);
			xmlPrintWriter.write(customerOutput + "\n");
		}
		xmlPrintWriter.close();	
	}
	
	//Writing the products
	public void xmlConverterProduct(List<Product> products) {
		XStream  xstream = new XStream();
		
        File xmlOutput = new File("data/Products.xml");
		
		PrintWriter xmlPrintWriter = null;
		try {
			xmlPrintWriter = new PrintWriter(xmlOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		xmlPrintWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		
		xstream.alias("movieticket", MovieTicket.class); 
		xstream.alias("seasonpass", SeasonPass.class); 
		xstream.alias("parkingpass", ParkingPass.class); 
		xstream.alias("refreshments", Refreshments.class); 
		//xstream.alias("email", Person.getEmail().toString()); TODO find alias to change email name
		for(Product aProduct : products) {
			// Use toXML method to convert Person object into a String
			xstream.registerConverter(new DateTimeConverter());
			String productOutput = xstream.toXML(aProduct);
			xmlPrintWriter.write(productOutput + "\n");
		}
		xmlPrintWriter.close();	
	}
	
//	//Date time converters
	class DateTimeConverter implements Converter {

		  public void marshal(Object value, HierarchicalStreamWriter writer,
		            MarshallingContext context) {

		   DateTime dateTime = (DateTime) value;
		  writer.setValue(dateTime.toString("yyyy-MM-dd HH:mm"));
		  
		  }

		@Override
		public boolean canConvert(Class object) {
			// TODO Auto-generated method stub
			return object.equals(DateTime.class);
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			// TODO Auto-generated method stub
			reader.moveDown();
			String date= reader.getValue();
			reader.moveUp();
			return date;
		}

		}
	
}