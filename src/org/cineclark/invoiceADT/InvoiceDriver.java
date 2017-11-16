package org.cineclark.invoiceADT;
import java.util.List;

import org.cineclark.datacontainers.Invoice;
import org.cineclark.fileReader.FlatFileReader;


public class InvoiceDriver {

	public static void main (String[] args){
		FlatFileReader flReader = new FlatFileReader(); //new instance of flatFileReader
		List<Invoice> invoices = flReader.readInvoices();
		
		InvoiceList IL = new InvoiceList();		
		
		for(Invoice aInvoice: invoices) {
			IL.addToStart(aInvoice);
		}
		
		IL.printSummary();
		IL.printDetailed();
	}
}
