package org.cineclark.invoiceADT;
import java.util.Comparator;
import java.util.List;

import org.cineclark.datacontainers.Invoice;
import org.cineclark.fileReader.FlatFileReader;

public class TotalComparator implements Comparator<Invoice>{
	FlatFileReader flReader = new FlatFileReader(); //new instance of flatFileReader
	List<Invoice> invoices = flReader.readInvoices();
	
	@Override
	public int compare(Invoice inv1, Invoice inv2) {
		int intV = 0;
		for(Invoice aInvoice: invoices){
			if(inv1.compareTo(inv2)<0){
				//inv1 is smaller than inv2
				intV= -1;
			}
			else if(inv1.compareTo(inv2)==0){
				//equal to other object
				intV= 0;
			}
			else
				//inv1 is larger than inv2
				intV= 1;
		}
		return intV;
	}
}
