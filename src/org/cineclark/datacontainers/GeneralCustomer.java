package org.cineclark.datacontainers;

public class GeneralCustomer extends Customer{
	
	private char type;

	public GeneralCustomer(String customerCode, Person contact, String name, Address address, char type) {
		super(customerCode, contact, name, address);
		this.type = type;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}



	/* (non-Javadoc)
	 * @see org.cineclark.datacontainers.Customer#getDiscount()
	 */
	@Override
	public double getDiscount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.cineclark.datacontainers.Customer#getadditionalFee()
	 */
	@Override
	public double getadditionalFee() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCustomerType() {
		// TODO Auto-generated method stub
		return "General";
	}
	

}
