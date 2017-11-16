package org.cineclark.datacontainers;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import org.cineclark.fileReader.FlatFileReader;
import org.joda.time.DateTime;

public class Invoice {
	
	private String invoiceCode;
	private Customer customer;
	private Person salesPerson;
	private DateTime invoiceDate;
	private ArrayList<Product> productList= new ArrayList<Product>();
	private double invoiceSubTotal;
	private double invoiceTotalTaxes;
	
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
		
		if(getCustomer().getCustomerType().equalsIgnoreCase("Student")) {
			return getInvoiceSubTotal()*getCustomer().getDiscount();
		}
		return 0;
	}
	
	public double getInvoiceTotal() {
		
		return getInvoiceSubTotal()+getCustomer().getadditionalFee()+getInvoiceTotalTaxes()-getInvoiceDiscount();
	}
	
	public String toSummary(){
		StringBuilder sb = new StringBuilder();
		double subInvoiceSubtotal=0;
		double subInvoiceTotalFees=0;
		double subInvoiceTotalTaxes=0;
		double subInvoiceTotalDiscount=0;
		double subInvoiceGrandTotal=0;
		//System.out.printf("%-10s %-40s %-20s %-10s %-6s %-7s %-8s %-9s\n\n", "Invoice", "Customer", "Salesperson","Subtotal","Fees","Taxes","Discount","Total");
			sb.append(String.format("%-10s %-40s %-20s $%-9.2f $%-5.2f $%-6.2f $%-7.2f $%-7.2f \n", getInvoiceCode(),(getCustomer().getName()+" ["+ getCustomer().getCustomerType()+"]"), getSalesPerson().getName(),getInvoiceSubTotal(),getCustomer().getadditionalFee(), getInvoiceTotalTaxes(), getInvoiceDiscount(), getInvoiceTotal()));
			/*subInvoiceSubtotal+=getInvoiceSubTotal();
					
			subInvoiceTotalFees +=getCustomer().getadditionalFee();
			subInvoiceTotalTaxes +=getInvoiceTotalTaxes();
			subInvoiceTotalDiscount +=getInvoiceDiscount();
			subInvoiceGrandTotal +=getInvoiceTotal();
		sb.append("====================================================================================================================");
		sb.append(String.format("TOTALS %-65s $%-9.2f $%-5.2f $%-6.2f $%-7.2f $%-7.2f\n", "",subInvoiceSubtotal,subInvoiceTotalFees,subInvoiceTotalTaxes, subInvoiceTotalDiscount, subInvoiceGrandTotal));
		
		*/
		return sb.toString();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Invoice %s \n",getInvoiceCode()));
		sb.append("========================\n");
		sb.append(String.format("Salesperson: %s\n",getSalesPerson().getName() ));
		sb.append("Customer Info:\n");
		//get the customer object
		Customer currentCustomer= getCustomer();
		sb.append(String.format("\t %s (%s)\n", currentCustomer.getName(), currentCustomer.getCustomerCode()));
		sb.append(String.format("\t [%5s]\n", currentCustomer.getCustomerType()));
		sb.append(String.format("\t %5s\n",currentCustomer.getContact().getName()));
		//get the address of customer
		Address currentAddress = currentCustomer.getAddress();
		sb.append(String.format("\t %s\n\t %s %s %s %s\n",currentAddress.getStreet(), currentAddress.getCity(), currentAddress.getState(),currentAddress.getZip(),currentAddress.getCountry()));
		sb.append("------------------------------------------\n");
		
		//Print out the individual product details
		sb.append(String.format("%-5s %-70s %-10s %-6s %-7s\n", "Code", "Item", "SubTotal", "Tax", "Total"));
		
		//variables for subtotal of product prices
		double productSubSubTotal=0;
		double productSubSubTaxes=0;
		double productSubTotalTotal=0;
		for (Product aProduct: getProductList()) {
			if(aProduct.productDetails().size() ==2) {
				sb.append(String.format("%-5s %-70s $%-9.2f $%-5.2f $%-6.2f\n%-5s %-60s\n",aProduct.getProductCode(),aProduct.productDetails().get(0),aProduct.computeSubTotal(),aProduct.computeTaxes(),aProduct.computeTotal(),"",aProduct.productDetails().get(1) ));
				
			}
			else 
				sb.append(String.format("%-5s %-70s $%-9.2f $%-5.2f $%-6.2f\n",aProduct.getProductCode(),aProduct.productDetails().get(0),aProduct.computeSubTotal(),aProduct.computeTaxes(),aProduct.computeTotal() ));
		productSubSubTotal+=aProduct.computeSubTotal();
		productSubSubTaxes+=aProduct.computeTaxes();
		productSubTotalTotal+=aProduct.computeTotal();
		}
		sb.append(String.format("%76s ==========================\n", ""));
		sb.append(String.format("%-76s $%-9.2f $%-5.2f $%-6.2f\n", "SUB-TOTALS",productSubSubTotal,productSubSubTaxes,productSubTotalTotal));
		if(getInvoiceDiscount() >0) {
			sb.append(String.format("%-94s $-%-6.2f\n", "DISCOUNT (8% STUDENT & NO TAX)", getInvoiceDiscount()));
		}
		if(currentCustomer.getadditionalFee() >0) {
			sb.append(String.format("%-93s  $%-6.2f\n","ADDITIONAL FEE (STUDENT)",currentCustomer.getadditionalFee()));
		}
		sb.append(String.format("%-94s $%-6.2f", "\nTOTAL", getInvoiceTotal()));
		
		//Thanking for purchase
		sb.append("\n\n\t\t\tThank you for your purchase!\n\n\n");
		return sb.toString();
	}
	
	public int compareTo(Invoice inv) {
		if(getInvoiceTotal()== inv.getInvoiceTotal()){
			return 0;
		}
		else if(getInvoiceTotal()> inv.getInvoiceTotal()){
			return 1;
		}
		else
			return -1;
	}
	
	

}
