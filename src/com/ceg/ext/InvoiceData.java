package com.ceg.ext;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cineclark.sql.DatabaseInfo;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;
import com.mysql.jdbc.Statement;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Do not change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	//Database Connectivity::
	static Connection conn;
	static PreparedStatement ps;
	

	
	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		conn = DatabaseInfo.getConnection(); //connect to database
		String sql = "DELETE FROM Person";
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.executeUpdate();
			sql = null; //releasing resource
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseInfo.closeConnection(); //disconnect from database

	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		conn = DatabaseInfo.getConnection(); //connect to database
		
		//first insert the address into address table and get the generated addressID
		String sql1 = "INSERT INTO `Address` ( `Street`, `City`, `State`, `Zip`, `Country`) VALUES (?, ?, ?, ?, ?)";
		int AddressID=0;
		try {
			ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				//System.out.println(rs.next());
			    AddressID = rs.getInt(1);
			}
			//System.out.println(AddressID+" returned generated keys");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//if it goes into catch, unique constant probably failed, try to see if the address alredy exists in that case update the addressID or show error
			sql1= "SELECT AddressID FROM Address WHERE Street= ? AND City= ? AND State= ? AND Zip = ? AND Country= ?";
			try {
				ps = conn.prepareStatement(sql1);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					AddressID= rs.getInt("AddressID");
					//System.out.println(AddressID );
				}
				if(AddressID ==0) {//did not find address and could not insert the address either
					System.out.println("Couldn't insert address or find address in the list");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		String sql2 = "INSERT INTO `Person` (`PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES ( ?, ?, ?,  ?)";
		try {
			ps = conn.prepareStatement(sql2);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setInt(4, AddressID);
			ps.executeUpdate();
			sql2 = null; //releasing resource
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Couldn't insert Person. Probably duplicate entry!");
			DatabaseInfo.closeConnection(); //close database connection
			return;
		}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		conn= DatabaseInfo.getConnection(); //connect to database
		//INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (1, '10points2Gryffindor@gmail.com',  1)
		String sql= "SELECT PersonID FROM `Person` WHERE PersonCode= ?";
		int personID=0;
		try {
			ps = conn.prepareStatement(sql);
			//System.out.println("I'm here");
			ps.setString(1, personCode);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				personID= rs.getInt("PersonID");
				//System.out.println(personID );
			}
			if(personID ==0) {//did not find address and could not insert the address either
				System.out.println("Person does not exist in the database! Couldn't add email address!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//after finding the personID we can add it to the database now
		sql = "INSERT INTO `Email` (`EmailAddress`, `PersonID`) VALUES (?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setInt(2, personID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Couldn't add email address. Probably duplicate email!");
			DatabaseInfo.closeConnection(); //close database connection
			return;
		}
		
		
		DatabaseInfo.closeConnection(); //close database connection
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
		conn = DatabaseInfo.getConnection(); //connect to database
		String sql = "DELETE FROM Customer";
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.executeUpdate();
			sql = null; //releasing resource
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseInfo.closeConnection(); //disconnect from database
	}
	/**
	 * add a Customer
	 * @param customerCode
	 * @param customerType
	 * @param primaryContactPersonCode
	 * @param name
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//first insert the address into address table and get the generated addressID
				String sql = "INSERT INTO `Address` ( `Street`, `City`, `State`, `Zip`, `Country`) VALUES (?, ?, ?, ?, ?)";
				int AddressID=0;
				try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, street);
					ps.setString(2, city);
					ps.setString(3, state);
					ps.setString(4, zip);
					ps.setString(5, country);
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
					    AddressID = rs.getInt(1);
					}
					//System.out.println(AddressID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the address alredy exists in that case update the addressID or show error
					sql= "SELECT AddressID FROM Address WHERE Street= ? AND City= ? AND State= ? AND Zip = ? AND Country= ?";
					try {
						ps = conn.prepareStatement(sql);
						ps.setString(1, street);
						ps.setString(2, city);
						ps.setString(3, state);
						ps.setString(4, zip);
						ps.setString(5, country);
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							AddressID= rs.getInt("AddressID");
							//System.out.println(AddressID );
						}
						if(AddressID ==0) {//did not find address and could not insert the address either
							System.out.println("Couldn't insert address or find address in the list");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
				
				//get customer primary contact iD
				
				sql= "SELECT PersonID FROM `Person` WHERE PersonCode= ?";
				int contactPersonID=0;
				try {
					ps = conn.prepareStatement(sql);
					//System.out.println("I'm here");
					ps.setString(1, primaryContactPersonCode);
					ResultSet rs= ps.executeQuery();
					
					while(rs.next()) {
						contactPersonID= rs.getInt("PersonID");
						//System.out.println(personID );
					}
					if(contactPersonID ==0) {//did not find address and could not insert the address either
						System.out.println("Contact Person does not exist in the database! Couldn't add customer!");
						DatabaseInfo.closeConnection(); //close database connection
						return;
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//now we try to add the customer 
				
				sql = "INSERT INTO `Customer` (`CustomerCode`, `CustomerName`,`CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (?,?,?,?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, customerCode);
					ps.setString(2, name);
					ps.setString(3, customerType);
					ps.setInt(4, contactPersonID);
					ps.setInt(5, AddressID);
					ps.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("Couldn't add Customer. Probably duplicate customer!");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		conn = DatabaseInfo.getConnection(); //connect to database
		String sql = "DELETE FROM Product";
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.executeUpdate();
			sql = null; //releasing resource
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//first insert the address into address table and get the generated addressID
		String sql = "INSERT INTO `Address` ( `Street`, `City`, `State`, `Zip`, `Country`) VALUES (?, ?, ?, ?, ?)";
		int AddressID=0;
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				//System.out.println(rs.next());
			    AddressID = rs.getInt(1);
			}
			//System.out.println(AddressID+" returned generated keys");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//if it goes into catch, unique constant probably failed, try to see if the address alredy exists in that case update the addressID or show error
			sql= "SELECT AddressID FROM Address WHERE Street= ? AND City= ? AND State= ? AND Zip = ? AND Country= ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					AddressID= rs.getInt("AddressID");
					//System.out.println(AddressID );
				}
				if(AddressID ==0) {//did not find address and could not insert the address either
					System.out.println("Couldn't insert address or find address in the list");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		//Insert into product table and get the productID
		sql = "INSERT INTO `Product` ( `ProductCode`, `ProductType`) VALUES (?, ?)";
		int ProductID=0;
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, productCode);
			ps.setString(2, "M");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				//System.out.println(rs.next());
				ProductID = rs.getInt(1);

			}
			//System.out.println(ProductID+" returned generated keys");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//if it goes into catch, unique constant probably failed, try to see if the Product alredy exists in that case update the productID or show error
			sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, productCode);
				ps.setString(2, "M");
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					ProductID= rs.getInt("ProductID");
					//System.out.println(AddressID );
				}
				if(ProductID ==0) {//did not find Product and could not insert the Product either
					System.out.println("Couldn't insert Product or find Product in the list. Try different ProductCode!");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		//insert the movie in the MovieTicket
				 sql = "INSERT INTO `MovieTicket` ( `MovieDateTime`, `MovieName`, `ScreenNo`, `MovieTicketPrice`, `AddressID`,`ProductID`) VALUES (?, ?, ?, ?, ?,?)";
				int MovieTicketID=0;
				 try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, dateTime);
					ps.setString(2, movieName);
					ps.setString(3, screenNo);
					ps.setDouble(4, pricePerUnit);
					ps.setInt(5, AddressID);
					ps.setInt(6, ProductID);
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
						MovieTicketID = rs.getInt(1);
					}
					//System.out.println(MovieTicketID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
					sql= "SELECT MovieTicketID FROM MovieTicket WHERE MovieDateTime= ? AND MovieName= ? AND ScreenNo= ? AND AddressID = ? AND ProductID= ?";
					
					try {
						ps = conn.prepareStatement(sql);
						ps.setString(1, dateTime);
						ps.setString(2, movieName);
						ps.setString(3, screenNo);
						ps.setInt(4, AddressID);
						ps.setInt(5, ProductID);
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							MovieTicketID= rs.getInt("MovieTicketID");
							//System.out.println(MovieTicketID );
						}
						if(MovieTicketID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
							System.out.println("Couldn't insert MovieTicket or find MovieTicket in the list");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						} else {
							System.out.println("Movie already exists in the database!");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
		
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//Insert into product table and get the productID
				String sql = "INSERT INTO `Product` ( `ProductCode`, `ProductType`) VALUES (?, ?)";
				int ProductID=0;
				try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, productCode);
					ps.setString(2, "S");
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
						ProductID = rs.getInt(1);

					}
					//System.out.println(ProductID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the Product alredy exists in that case update the productID or show error
					sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
					try {
						ps = conn.prepareStatement(sql);
						ps.setString(1, productCode);
						ps.setString(2, "S");
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							ProductID= rs.getInt("ProductID");
							//System.out.println(AddressID );
						}
						if(ProductID ==0) {//did not find Product and could not insert the Product either
							System.out.println("Couldn't insert Product or find Product in the list. Try different ProductCode!");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
				
				//insert the item into SeasonPass
						 sql = "INSERT INTO `SeasonPass` (`SeasonPassName`, `StartDate`, `EndDate`, `SeasonPassPrice`, `ProductID`) VALUES (?, ?, ?, ?, ?)";
						int SeasonPassID=0;
						 try {
							ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, name);
							ps.setString(2, seasonStartDate);
							ps.setString(3, seasonEndDate);
							ps.setDouble(4, cost);
							ps.setInt(5, ProductID);
							ps.executeUpdate();
							ResultSet rs = ps.getGeneratedKeys();
							if (rs.next()) {
								//System.out.println(rs.next());
								SeasonPassID = rs.getInt(1);
							}
							//System.out.println(MovieTicketID+" returned generated keys");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
							sql= "SELECT SeasonpassID FROM SeasonPass WHERE SeasonPassName= ? AND StartDate= ? AND EndDate= ?";
							
							try {
								ps = conn.prepareStatement(sql);
								ps.setString(1, name);
								ps.setString(2, seasonStartDate);
								ps.setString(3, seasonEndDate);
								ResultSet rs= ps.executeQuery();
								
								while(rs.next()) {
									SeasonPassID= rs.getInt("SeasonPassID");
									//System.out.println(MovieTicketID );
								}
								if(SeasonPassID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
									System.out.println("Couldn't insert SeasonPass or find SeasonPass in the list");
									DatabaseInfo.closeConnection(); //close database connection
									return;
								} else {
									System.out.println("SeasonPass already exists in the database!");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			
						}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//Insert into product table and get the productID
		String sql = "INSERT INTO `Product` ( `ProductCode`, `ProductType`) VALUES (?, ?)";
		int ProductID=0;
		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, productCode);
			ps.setString(2, "P");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				//System.out.println(rs.next());
				ProductID = rs.getInt(1);

			}
			//System.out.println(ProductID+" returned generated keys");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//if it goes into catch, unique constant probably failed, try to see if the Product alredy exists in that case update the productID or show error
			sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, productCode);
				ps.setString(2, "P");
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					ProductID= rs.getInt("ProductID");
					//System.out.println(AddressID );
				}
				if(ProductID ==0) {//did not find Product and could not insert the Product either
					System.out.println("Couldn't insert Product or find Product in the list. Try different ProductCode!");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		//insert the item in the ParkingPass
				 sql = "INSERT INTO `ParkingPass` (`ParkingPassPrice`, `ProductID`) VALUES (?, ?)";
				int ParkingPassID=0;
				 try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setDouble(1, parkingFee);
					ps.setInt(2, ProductID);
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
						ParkingPassID = rs.getInt(1);
					}
					//System.out.println(MovieTicketID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
					sql= "SELECT ParkingPassID FROM ParkingPass WHERE ParkingPassPrice= ? AND ProductID= ?";
					
					try {
						ps = conn.prepareStatement(sql);
						ps.setDouble(1, parkingFee);
						ps.setInt(2, ProductID);
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							ParkingPassID= rs.getInt("ParkingPassID");
							//System.out.println(MovieTicketID );
						}
						if(ParkingPassID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
							System.out.println("Couldn't insert ParkingPass or find ParkingPass in the list. Check your product code!");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						} else {
							System.out.println("ParkingPass already exists in the database!");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//Insert into product table and get the productID
				String sql = "INSERT INTO `Product` ( `ProductCode`, `ProductType`) VALUES (?, ?)";
				int ProductID=0;
				try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, productCode);
					ps.setString(2, "R");
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
						ProductID = rs.getInt(1);

					}
					//System.out.println(ProductID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the Product alredy exists in that case update the productID or show error
					sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
					try {
						ps = conn.prepareStatement(sql);
						ps.setString(1, productCode);
						ps.setString(2, "R");
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							ProductID= rs.getInt("ProductID");
							//System.out.println(AddressID );
						}
						if(ProductID ==0) {//did not find Product and could not insert the Product either
							System.out.println("Couldn't insert Product or find Product in the list. Try different ProductCode!");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
				
				//insert the item in the ParkingPass
						 sql = "INSERT INTO `Refreshment` (`RefreshmentName`, `RefreshmentPrice`,`ProductID`) VALUES (?, ?, ?)";
						int RefreshmentID=0;
						 try {
							ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, name);
							ps.setDouble(2, cost);
							ps.setInt(3, ProductID);
							ps.executeUpdate();
							ResultSet rs = ps.getGeneratedKeys();
							if (rs.next()) {
								//System.out.println(rs.next());
								RefreshmentID = rs.getInt(1);
							}
							//System.out.println(MovieTicketID+" returned generated keys");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
							sql= "SELECT RefreshmentID FROM Refreshment WHERE RefreshmentName= ? AND ProductID= ?";
							
							try {
								ps = conn.prepareStatement(sql);
								ps.setString(1, name);
								ps.setInt(2, ProductID);
								ResultSet rs= ps.executeQuery();
								
								while(rs.next()) {
									RefreshmentID= rs.getInt("RefreshmentID");
									//System.out.println(MovieTicketID );
								}
								if(RefreshmentID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
									System.out.println("Couldn't insert Refreshment or find Refreshment in the list. Check your product code!");
									DatabaseInfo.closeConnection(); //close database connection
									return;
								} else {
									System.out.println("Refreshment already exists in the database!");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			
						}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 10. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		conn = DatabaseInfo.getConnection(); //connect to database
		String sql = "DELETE FROM Invoice";
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.executeUpdate();
			sql = null; //releasing resource
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//find the customer from database
		String sql= "SELECT CustomerID FROM `Customer` WHERE CustomerCode= ?";
		int CustomerID=0;
		try {
			ps = conn.prepareStatement(sql);
			//System.out.println("I'm here");
			ps.setString(1, customerCode);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				CustomerID= rs.getInt("CustomerID");
				//System.out.println(personID );
			}
			if(CustomerID ==0) {//did not find address and could not insert the address either
				System.out.println("Customer does not exist in the database! Couldn't add Invoice!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//find the salesPerson from database
		sql= "SELECT PersonID FROM `Person` WHERE PersonCode= ?";
		int personID=0;
		try {
			ps = conn.prepareStatement(sql);
			//System.out.println("I'm here");
			ps.setString(1, salesPersonCode);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				personID= rs.getInt("PersonID");
				//System.out.println(personID );
			}
			if(personID ==0) {//did not find address and could not insert the address either
				System.out.println("Sales Person does not exist in the database! Couldn't add Invoice!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add the invoice 
		 sql = "INSERT INTO `Invoice` (`InvoiceCode`, `InvoiceDate`,`CustomerID`, `SalesPersonID`) VALUES (?, ?, ?, ?)";
			int InvoiceID=0;
			 try {
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, invoiceCode);
				ps.setString(2, invoiceDate);
				ps.setInt(3, CustomerID);
				ps.setInt(4, personID);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					//System.out.println(rs.next());
					InvoiceID = rs.getInt(1);
				}
				//System.out.println(InvoiceID+" returned generated keys");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
				sql= "SELECT InvoiceID FROM Invoice WHERE InvoiceCode= ? AND InvoiceDate= ? AND CustomerID=?";
				
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, invoiceCode);
					ps.setString(2, invoiceDate);
					ps.setInt(3, CustomerID);
					ResultSet rs= ps.executeQuery();
					
					while(rs.next()) {
						InvoiceID= rs.getInt("InvoiceID");
						//System.out.println(MovieTicketID );
					}
					if(InvoiceID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
						System.out.println("Couldn't insert Invoice or find Invoice in the list. Check your Invoice code!");
						DatabaseInfo.closeConnection(); //close database connection
						return;
					} else {
						System.out.println("Invoice already exists in the database!");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//find the productID
		String sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
		int ProductID=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productCode);
			ps.setString(2, "M");
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				ProductID= rs.getInt("ProductID");
				//System.out.println(AddressID );
			}
			if(ProductID ==0) {//did not find Product and could not insert the Product either
				System.out.println("Couldn't find Product in the list. Try different ProductCode!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//find invoiceID
		sql= "SELECT InvoiceID FROM Invoice WHERE InvoiceCode= ? ";
		int InvoiceID=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, invoiceCode);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				InvoiceID= rs.getInt("InvoiceID");
				//System.out.println(MovieTicketID );
			}
			if(InvoiceID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
				System.out.println("Couldn't  find Invoice in the list. Check your Invoice code!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add the product to invoiceProducts
		sql = "INSERT INTO `InvoiceProducts` (`NumberOfProducts`, `InvoiceID`,`ProductID`) VALUES (?, ?, ?)";
		int InvoiceProductsID=0;
		 try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, quantity);
			ps.setInt(2, InvoiceID);
			ps.setInt(3, ProductID);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				//System.out.println(rs.next());
				InvoiceProductsID = rs.getInt(1);
			}
			//System.out.println(InvoiceID+" returned generated keys");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
			sql= "SELECT InvoiceProductsID FROM InvoiceProducts WHERE InvoiceID= ? AND ProductID= ?";
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, InvoiceID);
				ps.setInt(2, ProductID);
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					InvoiceProductsID= rs.getInt("InvoiceProductsID");
					//System.out.println(MovieTicketID );
				}
				if(InvoiceProductsID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
					System.out.println("Couldn't insert Movie into  InvoiceProduct or find it in the list. Check your Product codes!");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				} else {
					System.out.println("You already added the same entry of MovieTicket product into invoice!");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//find the productID
				String sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
				int ProductID=0;
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, productCode);
					ps.setString(2, "S");
					ResultSet rs= ps.executeQuery();
					
					while(rs.next()) {
						ProductID= rs.getInt("ProductID");
						//System.out.println(AddressID );
					}
					if(ProductID ==0) {//did not find Product and could not insert the Product either
						System.out.println("Couldn't find Product in the list. Try different ProductCode!");
						DatabaseInfo.closeConnection(); //close database connection
						return;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//find invoiceID
				sql= "SELECT InvoiceID FROM Invoice WHERE InvoiceCode= ? ";
				int InvoiceID=0;
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, invoiceCode);
					ResultSet rs= ps.executeQuery();
					
					while(rs.next()) {
						InvoiceID= rs.getInt("InvoiceID");
						//System.out.println(MovieTicketID );
					}
					if(InvoiceID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
						System.out.println("Couldn't  find Invoice in the list. Check your Invoice code!");
						DatabaseInfo.closeConnection(); //close database connection
						return;
					} 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//add the product to invoiceProducts
				sql = "INSERT INTO `InvoiceProducts` (`NumberOfProducts`, `InvoiceID`,`ProductID`) VALUES (?, ?, ?)";
				int InvoiceProductsID=0;
				 try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, quantity);
					ps.setInt(2, InvoiceID);
					ps.setInt(3, ProductID);
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
						InvoiceProductsID = rs.getInt(1);
					}
					//System.out.println(InvoiceID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
					sql= "SELECT InvoiceProductsID FROM InvoiceProducts WHERE InvoiceID= ? AND ProductID= ?";
					
					try {
						ps = conn.prepareStatement(sql);
						ps.setInt(1, InvoiceID);
						ps.setInt(2, ProductID);
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							InvoiceProductsID= rs.getInt("InvoiceProductsID");
							//System.out.println(MovieTicketID );
						}
						if(InvoiceProductsID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
							System.out.println("Couldn't insert SeasonPass into  InvoiceProduct or find it in the list. Check your Product codes!");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						} else {
							System.out.println("You already added the same entry of Seasonpass product into invoice!");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 * NOTE: ticketCode may be null
	 */
	public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//find the productID
				String sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
				int ProductID=0;
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, productCode);
					ps.setString(2, "P");
					ResultSet rs= ps.executeQuery();
					
					while(rs.next()) {
						ProductID= rs.getInt("ProductID");
						//System.out.println(AddressID );
					}
					if(ProductID ==0) {//did not find Product and could not insert the Product either
						System.out.println("Couldn't find Product in the list. Try different ProductCode!");
						DatabaseInfo.closeConnection(); //close database connection
						return;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//find invoiceID
				sql= "SELECT InvoiceID FROM Invoice WHERE InvoiceCode= ? ";
				int InvoiceID=0;
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, invoiceCode);
					ResultSet rs= ps.executeQuery();
					
					while(rs.next()) {
						InvoiceID= rs.getInt("InvoiceID");
						//System.out.println(MovieTicketID );
					}
					if(InvoiceID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
						System.out.println("Couldn't  find Invoice in the list. Check your Invoice code!");
						DatabaseInfo.closeConnection(); //close database connection
						return;
					} 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//add the product to invoiceProducts
				sql = "INSERT INTO `InvoiceProducts` (`NumberOfProducts`, `ProductReference`, `InvoiceID`,`ProductID`) VALUES (?, ?, ?, ?)";
				int InvoiceProductsID=0;
				 try {
					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, quantity);
					ps.setString(2, ticketCode);
					ps.setInt(3, InvoiceID);
					ps.setInt(4, ProductID);
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						//System.out.println(rs.next());
						InvoiceProductsID = rs.getInt(1);
					}
					//System.out.println(InvoiceID+" returned generated keys");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
					sql= "SELECT InvoiceProductsID FROM InvoiceProducts WHERE InvoiceID= ? AND ProductID= ?";
					
					try {
						ps = conn.prepareStatement(sql);
						ps.setInt(1, InvoiceID);
						ps.setInt(2, ProductID);
						ResultSet rs= ps.executeQuery();
						
						while(rs.next()) {
							InvoiceProductsID= rs.getInt("InvoiceProductsID");
							//System.out.println(MovieTicketID );
						}
						if(InvoiceProductsID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
							System.out.println("Couldn't insert ParkingPass into  InvoiceProduct or find it in the list. Check your Product codes!");
							DatabaseInfo.closeConnection(); //close database connection
							return;
						} else {
							System.out.println("You already added the same entry of ParkingPass product into invoice!");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	/**
	 * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity. 
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
		conn = DatabaseInfo.getConnection(); //connect to database
		//find the productID
		String sql= "SELECT ProductID FROM Product WHERE ProductCode= ? AND ProductType= ?";
		int ProductID=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productCode);
			ps.setString(2, "R");
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				ProductID= rs.getInt("ProductID");
				//System.out.println(AddressID );
			}
			if(ProductID ==0) {//did not find Product and could not insert the Product either
				System.out.println("Couldn't find Product in the list. Try different ProductCode!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//find invoiceID
		sql= "SELECT InvoiceID FROM Invoice WHERE InvoiceCode= ? ";
		int InvoiceID=0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, invoiceCode);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				InvoiceID= rs.getInt("InvoiceID");
				//System.out.println(MovieTicketID );
			}
			if(InvoiceID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
				System.out.println("Couldn't  find Invoice in the list. Check your Invoice code!");
				DatabaseInfo.closeConnection(); //close database connection
				return;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add the product to invoiceProducts
		sql = "INSERT INTO `InvoiceProducts` (`NumberOfProducts`, `InvoiceID`,`ProductID`) VALUES (?, ?, ?)";
		int InvoiceProductsID=0;
		 try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, quantity);
			ps.setInt(2, InvoiceID);
			ps.setInt(3, ProductID);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				//System.out.println(rs.next());
				InvoiceProductsID = rs.getInt(1);
			}
			//System.out.println(InvoiceID+" returned generated keys");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//if it goes into catch, unique constant probably failed, try to see if the MovieTicket alredy exists in that case update the addressID or show error
			sql= "SELECT InvoiceProductsID FROM InvoiceProducts WHERE InvoiceID= ? AND ProductID= ?";
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, InvoiceID);
				ps.setInt(2, ProductID);
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					InvoiceProductsID= rs.getInt("InvoiceProductsID");
					//System.out.println(MovieTicketID );
				}
				if(InvoiceProductsID ==0) {//did not find MovieTicket and could not insert the MovieTicket either
					System.out.println("Couldn't insert Refreshment into  InvoiceProduct or find it in the list. Check your Product codes!");
					DatabaseInfo.closeConnection(); //close database connection
					return;
				} else {
					System.out.println("You already added the same entry of Refreshment product into invoice!");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		DatabaseInfo.closeConnection(); //disconnect from database
	}

	
}
