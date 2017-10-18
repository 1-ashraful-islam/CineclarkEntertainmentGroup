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

	@Override
	public String getCustomerType() {
		// TODO Auto-generated method stub
		return "Student";
	}

	@Override
	public double getDiscount() {
		// TODO Auto-generated method stub
		return .08; //8% discount
	}

	@Override
	public double getadditionalFee() {
		// TODO Auto-generated method stub
		return 6.75;
	}
	

}
