package org.cineclark.reports;

import java.util.List;

import org.cineclark.datacontainers.Address;
import org.cineclark.datacontainers.Customer;
import org.cineclark.datacontainers.Invoice;
import org.cineclark.datacontainers.Product;
import org.cineclark.fileReader.FlatFileReader;
import org.cineclark.invoiceADT.InvoiceList;
import org.cineclark.invoiceADT.TotalComparator;

import com.ceg.ext.DBClass;

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
		
		//from Discussion
		InvoiceList<Invoice> invoiceOrderedList=new InvoiceList<Invoice>(new TotalComparator());
		
		
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
		
		
		//Individual detail reports
		int invoiceNo=1;
		
		for(Invoice aInvoice: invoices) {
			System.out.println(String.format("Invoice %s",aInvoice.getInvoiceCode()));
			System.out.println("========================");
			System.out.println(String.format("Salesperson: %s",aInvoice.getSalesPerson().getName() ));
			System.out.println("Customer Info:");
			//get the customer object
			Customer currentCustomer= aInvoice.getCustomer();
			System.out.println(String.format("\t %s (%s)", currentCustomer.getName(), currentCustomer.getCustomerCode()));
			System.out.println(String.format("\t [%5s]", currentCustomer.getCustomerType()));
			System.out.println(String.format("\t %5s",currentCustomer.getContact().getName()));
			//get the address of customer
			Address currentAddress = currentCustomer.getAddress();
			System.out.println(String.format("\t %s\n\t %s %s %s %s",currentAddress.getStreet(), currentAddress.getCity(), currentAddress.getState(),currentAddress.getZip(),currentAddress.getCountry()));
			System.out.println("------------------------------------------");
			
			//Print out the individual product details
			System.out.println(String.format("%-5s %-70s %-10s %-6s %-7s", "Code", "Item", "SubTotal", "Tax", "Total"));
			
			//variables for subtotal of product prices
			double productSubSubTotal=0;
			double productSubSubTaxes=0;
			double productSubTotalTotal=0;
			for (Product aProduct: aInvoice.getProductList()) {
				if(aProduct.productDetails().size() ==2) {
					System.out.println(String.format("%-5s %-70s $%-9.2f $%-5.2f $%-6.2f\n%-5s %-60s",aProduct.getProductCode(),aProduct.productDetails().get(0),aProduct.computeSubTotal(),aProduct.computeTaxes(),aProduct.computeTotal(),"",aProduct.productDetails().get(1) ));
					
				}
				else 
					System.out.println(String.format("%-5s %-70s $%-9.2f $%-5.2f $%-6.2f",aProduct.getProductCode(),aProduct.productDetails().get(0),aProduct.computeSubTotal(),aProduct.computeTaxes(),aProduct.computeTotal() ));
			productSubSubTotal+=aProduct.computeSubTotal();
			productSubSubTaxes+=aProduct.computeTaxes();
			productSubTotalTotal+=aProduct.computeTotal();
			}
			System.out.println(String.format("%76s ==========================", ""));
			System.out.println(String.format("%-76s $%-9.2f $%-5.2f $%-6.2f", "SUB-TOTALS",productSubSubTotal,productSubSubTaxes,productSubTotalTotal));
			if(aInvoice.getInvoiceDiscount() >0) {
				System.out.println(String.format("%-94s $-%-6.2f", "DISCOUNT (8% STUDENT & NO TAX)",aInvoice.getInvoiceDiscount()));
			}
			if(currentCustomer.getadditionalFee() >0) {
				System.out.println(String.format("%-93s  $%-6.2f","ADDITIONAL FEE (STUDENT)",currentCustomer.getadditionalFee()));
			}
			System.out.println(String.format("%-94s $%-6.2f", "TOTAL", aInvoice.getInvoiceTotal()));
			
			//Thanking for purchase
			System.out.println("\n\n\t\t\tThank you for your purchase!\n\n");
			invoiceNo++;
		}
	}
}
