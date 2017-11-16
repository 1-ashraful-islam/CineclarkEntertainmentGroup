package org.cineclark.invoiceADT;
import org.cineclark.datacontainers.Invoice;

public class InvoiceNode{
		private InvoiceNode next;
	    private Invoice item;

	    public InvoiceNode(Invoice item) {
	        this.item = item;
	        this.next = null;
	    }

	    public Invoice getInvoice() {
	        return item;
	    }

	    public InvoiceNode getNext() {
	        return next;
	    }

	    public void setNext(InvoiceNode next) {
	        this.next = next;
	    }
	}
