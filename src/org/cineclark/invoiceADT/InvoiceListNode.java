package org.cineclark.invoiceADT;
import org.cineclark.datacontainers.Invoice;

public class InvoiceListNode {
	 private InvoiceListNode next;
	    private InvoiceDriver item;

	    public InvoiceListNode(InvoiceDriver item) {
	        this.item = item;
	        this.next = null;
	    }

	    public InvoiceDriver getInvoice() {
	        return item;
	    }

	    public InvoiceListNode getNext() {
	        return next;
	    }

	    public void setNext(InvoiceListNode next) {
	        this.next = next;
	    }
	}
