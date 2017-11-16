package org.cineclark.invoiceADT;
import java.util.Comparator;
import java.util.Iterator;

import org.cineclark.datacontainers.Invoice;

public class InvoiceListExample implements Iterable<Invoice>{
	private InvoiceNode<Invoice> nodeStart; 
	private int size;
	private Comparator<Invoice> comp;
	
	/*
	 * The comparator will be used for comparing two
	 * Invoice objects before adding it into the list
	 * for maintaining order
	 */

	public Invoice List(Comparator<Invoice> comp) {
		this.head=null;
		this.size=0;
		this.comp=comp;
	}
	
	/*
	 * The getter method for comparator
	 */
	
	public Comparator<Invoice> getComp(){
		return comp;
	}
	
	@Override
	public Iterator<Invoice> iterator(){
		return new IteratorInvoice();
	}
	class IteratorInvoice implements Iterator<Invoice>{
		int index=0;
		private InvoiceNode next;
	    private Invoice item;

		@Override
		public boolean hasNext() {
			//TODO
		}
		@Override
		public Invoice next(){
			return next;
		}
		
		@Override
		public void remove(){
			//TODO
		}
	}
	
}
