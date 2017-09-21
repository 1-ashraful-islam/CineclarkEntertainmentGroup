package org.cineclark.datacontainers;

public class Refreshments extends Product {

	private String refreshmentName;
	private double refreshmentCost;
	public Refreshments(String productCode, char productType, String refreshmentName, double refreshmentCost) {
		super(productCode, productType);
		this.refreshmentName = refreshmentName;
		this.refreshmentCost = refreshmentCost;
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
	
	
}
