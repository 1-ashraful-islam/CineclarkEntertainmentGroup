package org.cineclark.reports;

import java.util.List;

import org.cineclark.datacontainers.Invoice;
import org.cineclark.fileReader.FlatFileReader;
import org.cineclark.invoiceADT.InvoiceList;
import org.cineclark.invoiceADT.TotalComparator;

public class InvoiceReport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlatFileReader flReader = new FlatFileReader(); //new instance of flatFileReader
		List<Invoice> invoices = flReader.readInvoices();
		
		//from Discussion
				InvoiceList invoiceOrderedList=new InvoiceList(new TotalComparator());	
				
				for(Invoice aInvoice: invoices) {
					invoiceOrderedList.add(aInvoice);
				}
				
				invoiceOrderedList.printSummary();
				invoiceOrderedList.printDetailed();
	}

}
