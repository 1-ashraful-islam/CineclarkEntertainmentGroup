package org.cineclark.datacontainers;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;

public class SeasonPass extends Product{

	private String seasonPassName;
	private DateTime startDate;
	private DateTime endDate;
	private double seasonPassCost;
	private DateTime invoiceDate;
	
	public SeasonPass(String productCode, char productType, String seasonPassName, DateTime startDate, DateTime endDate,
			double seasonPassCost) {
		super(productCode, productType);
		this.seasonPassName = seasonPassName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.seasonPassCost = seasonPassCost;
	}
	
	public SeasonPass(SeasonPass copyInstance) {
		super(copyInstance.getProductCode(),copyInstance.getProductType());
		this.seasonPassName = copyInstance.seasonPassName;
		this.startDate = copyInstance.startDate;
		this.endDate = copyInstance.endDate;
		this.seasonPassCost = copyInstance.seasonPassCost;
		setNumberOfProducts(new Integer(copyInstance.getNumberOfProducts()));
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
	public double getSeasonPassCost() {
		return seasonPassCost;
	}
	public void setSeasonPassCost(double seasonPassCost) {
		this.seasonPassCost = seasonPassCost;
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

	@Override
	public double computeSubTotal() {
		// TODO Auto-generated method stub
		if(getInvoiceDate().withTimeAtStartOfDay().isAfter(getStartDate().withTimeAtStartOfDay())) {
			int effectivePeriod= Days.daysBetween(getInvoiceDate().withTimeAtStartOfDay() , getEndDate().withTimeAtStartOfDay() ).getDays();
			int seasonPeriod= Days.daysBetween(getStartDate().withTimeAtStartOfDay() , getEndDate().withTimeAtStartOfDay() ).getDays();
			double proRatedSeasonPassCost= ((double) effectivePeriod/ (double) seasonPeriod*getSeasonPassCost())+8.0;
			return proRatedSeasonPassCost*(double) getNumberOfProducts();
		}
		

		return (getSeasonPassCost()+8.0)* (double) getNumberOfProducts();
	}

	@Override
	public double computeTaxes() {
		// TODO Auto-generated method stub
		if(getTaxExempt() == 'Y') {
			return 0;
		}
		return computeSubTotal()*.06;
	}

	@Override
	public double computeTotal() {
		// TODO Auto-generated method stub
		return computeSubTotal()+computeTaxes();
	}

	@Override
	public ArrayList<String> productDetails() {
		
		ArrayList<String> seasonPassDetails= new ArrayList<String>();
		seasonPassDetails.add("Season Pass - "+ getSeasonPassName() );
		
		if(getInvoiceDate().isAfter(getStartDate()) && getInvoiceDate().isBefore(getEndDate())) {
			int effectivePeriod= Days.daysBetween(getInvoiceDate().withTimeAtStartOfDay() , getEndDate().withTimeAtStartOfDay() ).getDays();
			int seasonPeriod= Days.daysBetween(getStartDate().withTimeAtStartOfDay() , getEndDate().withTimeAtStartOfDay() ).getDays();
			seasonPassDetails.add(String.format("(%d units @ $%-4.2f/unit prorated %d/%d days + $8 fee/unit)", getNumberOfProducts(),getSeasonPassCost(),effectivePeriod,seasonPeriod ));
			
			return seasonPassDetails;
		}
		
		
		
		seasonPassDetails.add(String.format("(%d units @ $%-4.2f/unit + $8 fee/unit)", getNumberOfProducts(),getSeasonPassCost()));
		
		return seasonPassDetails;
		

	}
	
	
	
}
