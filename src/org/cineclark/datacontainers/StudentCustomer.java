package org.cineclark.datacontainers;

public class StudentCustomer extends Customer{
	
	private char type;

	public StudentCustomer(String customerCode, Person contact, String name, Address address, char type) {
		super(customerCode, contact, name, address);
		this.type = type;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
	

}
