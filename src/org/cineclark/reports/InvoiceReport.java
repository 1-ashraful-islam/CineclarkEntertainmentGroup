package org.cineclark.reports;

import java.util.List;

import org.cineclark.datacontainers.Invoice;
import org.cineclark.fileReader.FlatFileReader;

public class InvoiceReport {

	
	public static void main(String[] args) {
		
		FlatFileReader flReader = new FlatFileReader(); //new instance of flatFileReader
		List<Invoice> invoices = flReader.readInvoices(); //get the list of invoices
		
		//variables for sub-sub totals
		double subInvoiceSubtotal=0;
		double subInvoiceTotalFees=0;
		double subInvoiceTotalTaxes=0;
		double subInvoiceTotalDiscount=0;
		double subInvoiceGrandTotal=0;
		
		//Executive Summary reports
		System.out.println("========================\n Executive Summary Report\n========================");
		System.out.printf("%-10s %-40s %-20s %-10s %-6s %-7s %-8s %-9s\n", "Invoice", "Customer", "Salesperson","Subtotal","Fees","Taxes","Discount","Total");
		for(Invoice aInvoice: invoices) {
			System.out.println(String.format("%-10s %-40s %-20s $%-9.2f $%-5.2f $%-6.2f $%-7.2f $%-7.2f ", aInvoice.getInvoiceCode(),(aInvoice.getCustomer().getName()+" ["+ aInvoice.getCustomer().getCustomerType()+"]"), aInvoice.getSalesPerson().getName(),aInvoice.getInvoiceSubTotal(),aInvoice.getCustomer().getadditionalFee(), aInvoice.getInvoiceTotalTaxes(),aInvoice.getInvoiceDiscount(), aInvoice.getInvoiceTotal()));
			subInvoiceSubtotal+=aInvoice.getInvoiceSubTotal();
					
			subInvoiceTotalFees +=aInvoice.getCustomer().getadditionalFee();
			subInvoiceTotalTaxes +=aInvoice.getInvoiceTotalTaxes();
			subInvoiceTotalDiscount +=aInvoice.getInvoiceDiscount();
			subInvoiceGrandTotal +=aInvoice.getInvoiceTotal();
		}
		System.out.println("====================================================================================================================");
		System.out.println(String.format("TOTALS %-65s $%-9.2f $%-5.2f $%-6.2f $%-7.2f $%-7.2f", "",subInvoiceSubtotal,subInvoiceTotalFees,subInvoiceTotalTaxes, subInvoiceTotalDiscount, subInvoiceGrandTotal));
		
	}
}
