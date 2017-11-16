package com.ceg.ext;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DBClass {	
	Connection conn = null;
	Statement stmt = null;
	
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://cse.unl.edu:3306/mislam";
    
    //  Database credentials
    static final String USER = "mislam";
    String PASS ="KH8Ahb";
    
//    Scanner Readpass= new Scanner(System.in);
//    String PASS = Readpass.next();


	public Connection connectMeIn() {
		try{
			Class.forName("com.mysql.jdbc.Driver");			
		}catch(ClassNotFoundException e){
			System.err.println(e);
			System.exit (-1);
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	

	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public void resetDatabase() { //reset Database with the initial values
//		try {
//			conn = connectMeIn(); // connect to database
//			stmt = conn.createStatement(); //create the statement
//			String[] sql;
//			//open the sql file and keep updating until the end of line
//			try {
//				
//				Scanner sp = new Scanner(new File("data/database/invoiceDB.sql"));
//				StringBuilder sb = new StringBuilder();
//				while(sp.hasNext()) {
//					String line = sp.nextLine();
//					if(!(line.isEmpty())) {sb.append(line +"\n");}
//				}
//				//System.out.println(sb.toString());
//				sql = sb.toString().split(";");
//				for(String aQuery: sql) {
//					//split the aQuery with newlines
//					if(aQuery.trim().equalsIgnoreCase("-- duplicate entry for test case\n" + 
//							"COMMIT") || aQuery.trim().equalsIgnoreCase("COMMIT") || aQuery.trim().equalsIgnoreCase("START TRANSACTION") ) {
//						stmt.addBatch(aQuery.trim());
//						//System.out.println(aQuery.trim());
//						//System.out.println(">.<");
//					}
////					System.out.println(aQuery);
////					System.out.println(">.<");
//					
//				}
//				sp.close();
//				stmt.executeLargeBatch();
//				
//				
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			// done resetting the database, closing connection
//			closeConnection();
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
}
