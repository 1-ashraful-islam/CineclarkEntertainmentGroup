package org.cineclark.datacontainers;

import java.util.ArrayList;

public class Refreshments extends Product {

	private String refreshmentName;
	private double refreshmentCost;
	public Refreshments(String productCode, char productType, String refreshmentName, double refreshmentCost) {
		super(productCode, productType);
		this.refreshmentName = refreshmentName;
		this.refreshmentCost = refreshmentCost;
	}
	
	public Refreshments(Refreshments copyInstance) {
		super(copyInstance.getProductCode(),copyInstance.getProductType());
		this.refreshmentName = copyInstance.refreshmentName;
		this.refreshmentCost = copyInstance.refreshmentCost;
		setNumberOfProducts(new Integer(copyInstance.getNumberOfProducts()));
	}
	
	public String getRefreshmentName() {
		return refreshmentName;
	}
	public void setRefreshmentName(String refreshmentName) {
		this.refreshmentName = refreshmentName;
	}
	public double getRefreshmentCost() {
		return refreshmentCost;
	}
	public void setRefreshmentCost(double refreshmentCost) {
		this.refreshmentCost = refreshmentCost;
	}
	
	@Override
	public double computeSubTotal() {
		// TODO Auto-generated method stub
		//5% discount if purchased with movie
		if(getOptionalParameter() !=null) {
		if(getOptionalParameter().equalsIgnoreCase("Discount") ) {
			return getRefreshmentCost()*95*getNumberOfProducts()/100;
		}
		}

		return getRefreshmentCost()*getNumberOfProducts();
	}

	@Override
	public double computeTaxes() {
		// TODO Auto-generated method stub
		if(getTaxExempt() == 'Y') {
			return 0;
		}
		return computeSubTotal()*.04;
	}

	@Override
	public double computeTotal() {
		// TODO Auto-generated method stub
		return computeSubTotal()+computeTaxes();
	}

	@Override
	public ArrayList<String> productDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
