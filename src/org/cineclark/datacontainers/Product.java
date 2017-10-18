package org.cineclark.datacontainers;

import java.util.ArrayList;

abstract public class Product {

	private String productCode;
	private char productType;
	private int numberOfProducts=0;
	private String optionalParameter=null;
	private char taxExempt= 'N';
	
	
	public Product(String productCode, char productType) {
		super();
		this.productCode = productCode;
		this.productType = productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public char getProductType() {
		return productType;
	}

	public void setProductType(char productType) {
		this.productType = productType;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	/**
	 * @return the optionalParameter
	 */
	public String getOptionalParameter() {
		return optionalParameter;
	}

	/**
	 * @param optionalParameter the optionalParameter to set
	 */
	public void setOptionalParameter(String optionalParameter) {
		this.optionalParameter = optionalParameter;
	}
	
	
	
	
	/**
	 * @return the taxExempt
	 */
	public char getTaxExempt() {
		return taxExempt;
	}

	/**
	 * @param taxExempt the taxExempt to set
	 */
	public void setTaxExempt(char taxExempt) {
		this.taxExempt = taxExempt;
	}

	abstract public double computeSubTotal();
	abstract public double computeTaxes();
	abstract public double computeTotal();
	abstract public ArrayList<String> productDetails();
	
}
