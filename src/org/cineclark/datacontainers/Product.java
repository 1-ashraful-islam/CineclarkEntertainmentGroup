package org.cineclark.datacontainers;

public class Product {

	private String productCode;
	private char productType;
	
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
	
	
	
}
