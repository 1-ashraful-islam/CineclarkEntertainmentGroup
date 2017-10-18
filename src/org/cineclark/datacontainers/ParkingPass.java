package org.cineclark.datacontainers;

import java.util.ArrayList;

public class ParkingPass extends Product{

	private double parkingFee;

	public ParkingPass(String productCode, char productType, double parkingFee) {
		super(productCode, productType);
		this.parkingFee = parkingFee;
	}
	public ParkingPass(ParkingPass copyInstance) {
		super(copyInstance.getProductCode(),copyInstance.getProductType());
		setNumberOfProducts(new Integer(copyInstance.getNumberOfProducts()));
		this.parkingFee=copyInstance.parkingFee;
	}

	public double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}

	@Override
	public double computeSubTotal() {
		// TODO Auto-generated method stub
		//check to see if a movie ticket is associated

		if(getOptionalParameter() !=null) 
		{
			int freeParking= Integer.parseInt(getOptionalParameter());
			if(freeParking >getNumberOfProducts()) return 0; 
			return getParkingFee()*(getNumberOfProducts()-freeParking);
		}

		return getParkingFee()*getNumberOfProducts();
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
