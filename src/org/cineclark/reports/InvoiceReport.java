package org.cineclark.reports;

import org.cineclark.datacontainers.Invoice;
import org.cineclark.invoiceADT.InvoiceList;
import org.cineclark.invoiceADT.TotalComparator;

public class InvoiceReport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//from Discussion
				InvoiceList<Invoice> invoiceOrderedList=new InvoiceList<Invoice>(new TotalComparator());
				
	}

}
