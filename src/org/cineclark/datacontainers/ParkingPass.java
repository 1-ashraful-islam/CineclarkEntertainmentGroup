package org.cineclark.datacontainers;

public class ParkingPass extends Product{

	private double parkingFee;

	public ParkingPass(String productCode, char productType, double parkingFee) {
		super(productCode, productType);
		this.parkingFee = parkingFee;
	}

	public double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}
	
	
}
