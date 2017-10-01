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
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;

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
		//to change the email field
		xstream.aliasField("emails", Person.class,"email");
		ClassAliasingMapper emailsMapper = new ClassAliasingMapper(xstream.getMapper());
		emailsMapper.addClassAlias("email", String.class);
		xstream.registerLocalConverter(Person.class,"email", new CollectionConverter(emailsMapper));
		
		xmlPrintWriter.write("<persons>");
		for(Person aPerson : persons) {
			// Use toXML method to convert Person object into a String
			java.lang.String personOutput = xstream.toXML(aPerson);
			xmlPrintWriter.write(personOutput + "\n");
		}
		xmlPrintWriter.write("</persons>");
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
		//to change the email field
				xstream.aliasField("emails", Person.class,"email");
				ClassAliasingMapper emailsMapper = new ClassAliasingMapper(xstream.getMapper());
				emailsMapper.addClassAlias("email", String.class);
				xstream.registerLocalConverter(Person.class,"email", new CollectionConverter(emailsMapper));

		xmlPrintWriter.write("<customers>");
		for(Customer aCustomer : customers) {
			// Use toXML method to convert Person object into a String
			String customerOutput = xstream.toXML(aCustomer);
			xmlPrintWriter.write(customerOutput + "\n");
		}
		xmlPrintWriter.write("</customers>");
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
 
		xmlPrintWriter.write("<products>");
		for(Product aProduct : products) {
			// Use toXML method to convert Person object into a String
			xstream.registerConverter(new DateTimeConverter());
			String productOutput = xstream.toXML(aProduct);
			xmlPrintWriter.write(productOutput + "\n");
		}
		xmlPrintWriter.write("</products>");
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