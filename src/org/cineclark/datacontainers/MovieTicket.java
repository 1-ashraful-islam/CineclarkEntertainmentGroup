package org.cineclark.datacontainers;

import org.joda.time.DateTime;

public class MovieTicket extends Product{

	private DateTime dateTime;
	private String movieName;
	private Address address;
	private String screenNo;
	private double pricePerUnit;
	
	public MovieTicket(String productCode, char productType, DateTime dateTime, String movieName, Address address,
			String screenNo, double pricePerUnit) {
		super(productCode, productType);
		this.dateTime = dateTime;
		this.movieName = movieName;
		this.address = address;
		this.screenNo = screenNo;
		this.pricePerUnit = pricePerUnit;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getScreenNo() {
		return screenNo;
	}

	public void setScreenNo(String screenNo) {
		this.screenNo = screenNo;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	
	
}
