	package org.cineclark.fileReader;

	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.ArrayList;
import java.util.Scanner;
	import org.cineclark.datacontainers.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

	public class FlatFileReader {
		
		
		// This Person ArrayList stores the Person objects 
		ArrayList<Person> personList = new ArrayList<Person>(); 
		Scanner sp = null; 
		{ // this code block reads the persons.dat file and stores person objects in a ArrayList
			try {
				sp = new Scanner(new File("data/Persons.dat"));
				
				sp.nextLine(); // reads the number of records from the first line
				
				while(sp.hasNext()) {
					String emailList[] = null;
					String line = sp.nextLine(); // reads each line starting from 2nd line
					String data[] = line.split(";"); // tokenizes the line and stores in a s

					// Stores the 4 array elements of each line into strings
					String personCode = data[0];
					String personName = data[1];
					String commaSeperatedAddress= data[2];
					if (data.length ==4) {
						emailList= data[3].split(",");
					}
					
					//Person Name
					String Name[] = personName.split(",");
					String lastName = Name[0].trim();
					String firstName = Name[1].trim();
					
					//Address Sort
					String addressSort[]= commaSeperatedAddress.split(",");
					String street = addressSort[0];
					String city = addressSort[1];
					String state = addressSort[2];
					String zip = addressSort[3];
					String country = addressSort[4];
					
					
					// Creates an Address object
					Address address = new Address(street, city, state, zip, country);
					
					// Creates a Person object
					Person person = new Person(personCode, lastName, firstName, address);
					//System.out.print("("+n+", "+personCode+", "+firstName+", " + lastName+", "+n+"), ");

					
					if (emailList != null) {
					ArrayList<String> emailArray = new ArrayList<String>();

					for ( String e: emailList) {
						emailArray.add(e);
						
						}
					person.setEmail(emailArray);
					}
					
					
					personList.add(person);
					
					}

				
				sp.close();	


				} catch(FileNotFoundException e) {
					System.err.println(e);
				}
			
		}
		public ArrayList<Person> readPersons() {
			
			return personList;
		}
			
		
		Scanner sc = null; //  scanner for customer
		// This Customer ArrayList stores the Customer objects 
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		{ // This code block reads the Customers.dat file and makes the customer objects array		
			try {
				sc = new Scanner(new File("data/Customers.dat"));
				sc.nextLine(); // reads the number of records from the first line

				
				while(sc.hasNext()) {
					String line = sc.nextLine(); // reads each line starting from 2nd line
					String data[] = line.split(";"); // tokenizes the line and stores in a s

					// Stores the 5 array elements of each line into strings
					String customerCode = data[0];
					char customerType = data[1].trim().charAt(0);
					String contactPerson= data[2];
					String customerName = data[3];
					String commaSeperatedAddress= data[4];
					
					//Address Sort
					String addressSort[]= commaSeperatedAddress.split(",");
					String street = addressSort[0];
					String city = addressSort[1];
					String state = addressSort[2];
					String zip = addressSort[3];
					String country = addressSort[4];
					
					
					// Creates an Address object
					Address address = new Address(street, city, state, zip, country);
					
					// find the person by id from the person array and add that here
					
					
					Person person = null;
					for(Person aPerson: personList) {
						
						if(aPerson.getPersonCode().equalsIgnoreCase(contactPerson)) {
							person= aPerson;
							break;
						}
					}
					

					//Creates a customer object;
					Customer customer = null;
					if (customerType == 'S') {
					customer = new StudentCustomer(customerCode, person, customerName, address, customerType);
					} 
					if (customerType == 'G') {
						customer = new GeneralCustomer(customerCode, person, customerName, address, customerType);
					}
					
					customerList.add(customer);
					
					}
				

				
				sc.close();	


				} catch(FileNotFoundException e) {
					System.err.println(e);
				}
	}
			public ArrayList<Customer> readCustomers() {
			return customerList;
			}
			
			
			Scanner spr = null; //  scanner for product
			// This Product ArrayList stores the product objects 
			ArrayList<Product> productList = new ArrayList<Product>();
			{ // This code block reads the Products.dat file and makes the product objects array		
				try {
					spr = new Scanner(new File("data/Products.dat"));
					spr.nextLine(); // reads the number of records from the first line
					Product product = null; //storage for product
					
					while(spr.hasNext()) {
						String line = spr.nextLine(); // reads each line starting from 2nd line
						String data[] = line.split(";"); // tokenizes the line and stores in a s

						// Stores the  array elements of each line into strings
						String productCode = data[0];
						char productType = data[1].trim().charAt(0);
						
						//System.out.print("("+m+", "+productCode+", "+productType+"), ");
						
						//MovieTicket
						if (productType == 'M') {
							DateTime dateTime= DateTime.parse(data[2], DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));

							String movieName= data[3];
							String commaSeperatedAddress[]= data[4].split(",");
							String screenNo= data[5];
							double pricePerUnit = Double.parseDouble(data[6]);
							
							//Address Sort
							String street = commaSeperatedAddress[0];
							String city = commaSeperatedAddress[1];
							String state = commaSeperatedAddress[2];
							String zip = commaSeperatedAddress[3];
							String country = commaSeperatedAddress[4];
							
							// Creates an Address object
							Address address = new Address(street, city, state, zip, country);
							
							
							//Create the movie Product
							product= new MovieTicket(productCode, productType, dateTime, movieName, address, screenNo, pricePerUnit);
							
							
						}
						
						//SeasonPass
						if(productType == 'S') {
							String seasonPassName = data[2];
							DateTime startDate = DateTime.parse(data[3], DateTimeFormat.forPattern("yyyy-MM-dd")).toDateTimeISO();
							DateTime endDate = DateTime.parse(data[4], DateTimeFormat.forPattern("yyyy-MM-dd"));
							double seasonPassCost= Double.parseDouble(data[5]);
							
							
							//create the Season pass product
							product = new SeasonPass(productCode, productType, seasonPassName, startDate, endDate, seasonPassCost);
						}
						
						//ParkingPass
						if(productType =='P') {
							double parkingFee = Double.parseDouble(data[2]);
							
							
						//create the parking pass product
							product = new ParkingPass(productCode, productType, parkingFee);
						}
						
						//Refreshment
						if(productType == 'R') {
							String refreshmentName = data[2];
							double refreshmentCost = Double.parseDouble(data[3]);
							
							
							//create the Refreshment product
							product = new Refreshments(productCode, productType, refreshmentName, refreshmentCost);
						}
						
						
						productList.add(product);
						

					}					
					spr.close();	


					} catch(FileNotFoundException e) {
						System.err.println(e);
					}
		}
				public ArrayList<Product> readProducts() {
				return productList;
				}
				
//				support for invoice
				Scanner sinv = null; //  scanner for product
				// This Product ArrayList stores the product objects 
				ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
				{ // This code block reads the Invoices.dat file and makes the invoice objects array		
					try {
						sinv = new Scanner(new File("data/Invoices.dat"));
						sinv.nextLine(); // reads the number of records from the first line
						
			
						
						while(sinv.hasNext()) {
							Invoice invoice = null; //storage for invoice
							
							Customer invoiceCustomer= null;
							Person salesPerson= null;
							ArrayList<Product> invoiceProducts = new ArrayList<Product>();
							String line = sinv.nextLine(); // reads each line starting from 2nd line
							String data[] = line.split(";"); // tokenizes the line and stores in a s

							// Stores the  array elements of each line into strings
							String invoiceCode = data[0];
							String customerCode = data[1];
							//find the associated customer and add to list
							int k=0;
							for(Customer aCustomer: customerList) {
								k++;
								if(aCustomer.getCustomerCode().equalsIgnoreCase(customerCode)) {invoiceCustomer= aCustomer; break;}
							}
							
							
							
							String salespersonCode= data[2];
							
							//find associated sales person
							for(Person aPerson: personList) {
								
								if(aPerson.getPersonCode().equalsIgnoreCase(salespersonCode)) {salesPerson= aPerson; break;}
							}
							DateTime invoiceDate= DateTime.parse(data[3], DateTimeFormat.forPattern("yyyy-MM-dd"));
							
							String invoiceProductListString[]=data[4].split(","); //seperating the products
							
							// work on each products
							for ( String productString: invoiceProductListString) {
								String productDetails[]= productString.split(":");
								
								//find product type from productList and add to result
								
								Product productSearchResult = null;
								for (Product aProduct: productList) {
									
									if(aProduct.getProductCode().equalsIgnoreCase(productDetails[0]))
										{
										productSearchResult= aProduct;
										break;
										}
								}
								

								
							//MovieTicket, parking pass, refreshment does not have the optional entity
							
								//MovieTicket						
							if ( productSearchResult.getProductType()== 'M') {
								//add the number of products 
								Product addResult= new MovieTicket((MovieTicket) productSearchResult);
								addResult.setNumberOfProducts(Integer.parseInt(productDetails[1]));
								
								invoiceProducts.add(addResult);
							}
							
							//SeasonPass
							if ( productSearchResult.getProductType()== 'S') {
								//add the number of products 
								SeasonPass addResult= new SeasonPass((SeasonPass) productSearchResult);
								addResult.setInvoiceDate(invoiceDate);
								addResult.setNumberOfProducts(Integer.parseInt(productDetails[1]));
								
								invoiceProducts.add((Product) addResult);
							}
							//Refresments
							if ( productSearchResult.getProductType()== 'R') {
								//add the number of products 
								Product addResult= new Refreshments((Refreshments) productSearchResult);
								addResult.setNumberOfProducts(Integer.parseInt(productDetails[1]));
								
								invoiceProducts.add(addResult);
							}
							
							
							//ParkingPass might have a optional entity
							if(productSearchResult.getProductType() =='P') {
								//add the number of products 
								Product addResult= new ParkingPass((ParkingPass) productSearchResult);
								addResult.setNumberOfProducts(Integer.parseInt(productDetails[1]));
								//check the additional optional parameter to calculate number of free parkings
								if(productDetails.length>2) {
									addResult.setOptionalParameter(productDetails[2]);
								}
								
								
								
								invoiceProducts.add(addResult);
							}
							

							}
							
							//find if the refreshment discount applies
							//find if discount is available
							Product discountedRefreshment= null; 
							for(Product aProduct:invoiceProducts) {
								if(aProduct.getProductType() == 'R') discountedRefreshment= aProduct;
							}
							if (discountedRefreshment !=null) { //if discount applies
							for(Product aProduct:invoiceProducts) {
								if (aProduct.getProductType() == 'M') {
									discountedRefreshment.setOptionalParameter("Discount");
								}
							}
							}
							//find if the free parking available
							//find number of free parking
							Product discountedParking= null;
							for(Product aProduct:invoiceProducts) {
								if(aProduct.getProductType() == 'P' && aProduct.getOptionalParameter() !=null) {
									discountedParking= aProduct;
									break;
								}
							}
							if(discountedParking !=null) {
								for(Product aProduct:invoiceProducts) {	
							if (aProduct.getProductCode().equalsIgnoreCase(discountedParking.getOptionalParameter())) {
								discountedParking.setOptionalParameter(discountedParking.getOptionalParameter()+","+Integer.toString(aProduct.getNumberOfProducts()));
								break;
							}
							}
								//if the associated referenced product is not in invoice set the optional parameter to null again
								if(discountedParking.getOptionalParameter().split(",").length !=2) discountedParking.setOptionalParameter(null);
							}
							
							//set the tax exempt
							

							if(invoiceCustomer.getCustomerType().equalsIgnoreCase("Student"))
							{
								for (Product aProduct: invoiceProducts) {
									aProduct.setTaxExempt('Y');
								}
								
							}
							
						
							
							invoice= new Invoice(invoiceCode, invoiceCustomer, salesPerson, invoiceDate,invoiceProducts );
							invoiceList.add(invoice);
							
						

						}					
						sinv.close();	


						} catch(FileNotFoundException e) {
							System.err.println(e);
						}
			}
					public ArrayList<Invoice> readInvoices() {
					return invoiceList;
					}
				

		}