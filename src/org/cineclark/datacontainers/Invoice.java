package org.cineclark.datacontainers;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Invoice {
	
	private String invoiceCode;
	private Customer customer;
	private Person salesPerson;
	private DateTime invoiceDate;
	private ArrayList<Product> productList= new ArrayList<Product>();
	private double invoiceSubTotal;
	private double invoiceTotalTaxes;
	private double invoiceDiscount;
	
	public Invoice(String invoiceCode, Customer customer, Person salesPerson, DateTime invoiceDate,
			ArrayList<Product> productList) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
		this.productList = productList;
	}
	/**
	 * @return the invoiceCode
	 */
	public String getInvoiceCode() {
		return invoiceCode;
	}
	/**
	 * @param invoiceCode the invoiceCode to set
	 */
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/**
	 * @return the salesPerson
	 */
	public Person getSalesPerson() {
		return salesPerson;
	}
	/**
	 * @param salesPerson the salesPerson to set
	 */
	public void setSalesPerson(Person salesPerson) {
		this.salesPerson = salesPerson;
	}
	/**
	 * @return the invoiceDate
	 */
	public DateTime getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(DateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return the productList
	 */
	public ArrayList<Product> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}
	
	
	public double getInvoiceSubTotal() {
		
		invoiceSubTotal=0;
		for(Product aProduct: getProductList()) {
			invoiceSubTotal+=aProduct.computeSubTotal();
		}
		
		return invoiceSubTotal; 
	}
	
	public double getInvoiceTotalTaxes() {
		invoiceTotalTaxes=0;
		for(Product aProduct: getProductList()) {
			invoiceTotalTaxes+=aProduct.computeTaxes();
		}
		return invoiceTotalTaxes;
	}
	
	public double getInvoiceDiscount() {
		invoiceDiscount= 0;
		if(getCustomer().getCustomerType().equalsIgnoreCase("Student")) {
			return getInvoiceSubTotal()*getCustomer().getDiscount();
		}
		return 0;
	}
	
	public double getInvoiceTotal() {
		
		return getInvoiceSubTotal()+getCustomer().getadditionalFee()+getInvoiceTotalTaxes()-getInvoiceDiscount();
	}
	
	
	
	

}
