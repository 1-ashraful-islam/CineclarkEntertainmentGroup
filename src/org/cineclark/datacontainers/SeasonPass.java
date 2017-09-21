package org.cineclark.datacontainers;

import org.joda.time.DateTime;

public class SeasonPass extends Product{

	private String seasonPassName;
	private DateTime startDate;
	private DateTime endDate;
	private double cost;
	
	public SeasonPass(String productCode, char productType, String seasonPassName, DateTime startDate, DateTime endDate,
			double cost) {
		super(productCode, productType);
		this.seasonPassName = seasonPassName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cost = cost;
	}
	public String getSeasonPassName() {
		return seasonPassName;
	}
	public void setSeasonPassName(String seasonPassName) {
		this.seasonPassName = seasonPassName;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
	
}
