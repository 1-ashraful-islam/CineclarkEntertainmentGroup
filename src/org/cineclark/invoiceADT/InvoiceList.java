package org.cineclark.invoiceADT;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.lang.*;

import org.cineclark.datacontainers.Invoice;
import org.cineclark.fileReader.FlatFileReader;


public class InvoiceList implements Iterable<Invoice> {
		private InvoiceNode start;
		private InvoiceNode end;
		private int size;
		private Comparator<Invoice> comp;
		
		/*
		 * The comparator will be used for comparing two
		 * Invoice objects before adding it into the list
		 * for maintaining order
		 */

		public InvoiceList(Comparator<Invoice> comp) {
			this.start =null;
			this.size=0;
			this.comp=comp;
		}
		
	
		// The getter method for comparator
		public Comparator<Invoice> getComp(){
			return comp;
		}
		
		public void add(Invoice item) {
			InvoiceNode newInvoiceNode = new InvoiceNode(item);
			if(start == null){
				start=newInvoiceNode;
				end=start;
			}
			else if(size==1){
				//New node is smaller than start node, and 
				//the new node should be set as the start node.
				//Set the pointers as follows.
				//new node-->start node
				//start=new
				if(item.compare(start.getInvoice())== 1){
					//TODO
					newInvoiceNode.setNext(start);
					start=newInvoiceNode;
					size++;
				}
				/*Case 2
				 * New node > start node
				 * New node should be the neighbor of the start node
				 * set pointer as follows.
				 * start node --> new node
				 */
				else{
					//TODO
					end.setNext(newInvoiceNode);
					end = newInvoiceNode;
					size++;
				}
			}
			else{
				if(item.compare(inv2)== 1){
					/*
					 * Case 1: new node is smaller than start node
					 * Start node should be set as the next node of the new node,
					 * and the new node should be set as the start node
					 * new node --> start node
					 * start=new
					 */
					//TODO
					newInvoiceNode.setNext(start);
					start=newInvoiceNode;
					size++;
				}
				else if(inv1.compare(inv2)== -1){
					/*
					 * Case 2: New node is smaller than neighbor of the start node
					 * The neighbor of the start node should be set as the next node
					 * of the new node, and the new node should be set as the next
					 * node of the start node.
					 * new node --> neighbor of start node
					 * start node -->new node
					 */
					//TODO
					InvoiceNode previousNode = new InvoiceNode(item);
					previousNode= start;
					int index=0;
					for(int i=1; i<index -1; i++){
						previousNode=previousNode.getNext();
					}
					InvoiceNode nextNode=previousNode.getNext();
					newInvoiceNode.setNext(previousNode);
					previousNode.setNext(nextNode);
					size++;
				}
				else{
					/*
					 * Case 3: new node is greater than or equal to the neighbor of
					 * the start node.(current node>=current node)
					 * current node --> new node
					 * new node --> next node
					 */
					//TODO
					InvoiceNode previousNode = new InvoiceNode(item);
					previousNode=start;
					int index=0;
					for(int i=1; i<index -1; i++){
						previousNode=previousNode.getNext();
					}
					InvoiceNode nextNode=previousNode.getNext();
					previousNode.setNext(newInvoiceNode);
					newInvoiceNode.setNext(nextNode);
					size++;
				}
			}
		}
		
		private InvoiceNode getInvoiceNodeAtIndex(int position) {
			// get the truck that at index position
			InvoiceNode invoiceAtIndex = start;
					//if the index is out of range throw an error and exit
					if (position > size || position < 0) {
						throw new IndexOutOfBoundsException();
					}
					
					//handling corner cases
					if(position==0) {
						return start;
						
					} else if(position==size) {
						return end;
					} else if (position<size || position >0){
						
						for (int i = 0; i < position; i++) {
							invoiceAtIndex = invoiceAtIndex.getNext();
								
							}
						return invoiceAtIndex;

						}

						return invoiceAtIndex;
					
					
			//throw new UnsupportedOperationException("Not yet implemented.");
		}
		public Invoice getInvoice(int position) {
			// get the truck that is before the index position
			InvoiceNode invoiceAtIndex = null;
			// if the index is out of range throw an error and exit
			if (position > size || position < 0) {
				throw new IndexOutOfBoundsException();
			}
			// handling corner cases
			if (position == 0) {
				invoiceAtIndex = start;

			} else {

				// get truck at index
				invoiceAtIndex = getInvoiceNodeAtIndex(position);

			}
			return invoiceAtIndex.getInvoice();
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
				return false;
			}
			@Override
			public Invoice next(){
				//return getNext();
				return null;
			}
			
			@Override
			public void remove(){
				//TODO
			}
		}
		
		
		
		
		
		
		
		
		public void clear() { 
			InvoiceNode previousNode=start;
			if(previousNode.getNext() !=null){
				previousNode=previousNode.getNext();
				InvoiceNode nodeToBeRemoved =previousNode.getNext();
				InvoiceNode nextNode=nodeToBeRemoved.getNext();
				previousNode.setNext(nextNode);
				size--;
			}
			
		}	   

	    
	    public void remove(int position) {
	    	InvoiceNode previousNode=start;
	    	try{
	    		for(int i=0; i<position-1;i++){
	    			if(previousNode.getNext() !=null){
	    				if(position==1){
	    					start=start.getNext();
	    					size--;
	    				}
	    				else{
	    				previousNode=previousNode.getNext();
	    				InvoiceNode nodeToBeRemoved =previousNode.getNext();
	    				InvoiceNode nextNode=nodeToBeRemoved.getNext();
	    				previousNode.setNext(nextNode);
	    				end=nextNode;
	    				size--;
	    				}
	    			}
	    		}
	    	}catch(IndexOutOfBoundsException e){
	    		System.out.println("\nException thrown :" + e);
	    	}
	    }
	    	
	    
	    
	    
	    //TODO: Print the list to the standard output in a readable format
	    //Invoice.toString()
	    public void printDetailed() {
		//InvoiceListNode headNode=start;
			for(int i=0; i<size; i++){
				System.out.println(getInvoice(i).toString());
			}	
	    }

	    public void printSummary(){
	    	double subInvoiceSubtotal=0;
			double subInvoiceTotalFees=0;
			double subInvoiceTotalTaxes=0;
			double subInvoiceTotalDiscount=0;
			double subInvoiceGrandTotal=0;
			
			FlatFileReader flReader = new FlatFileReader(); //new instance of flatFileReader
			List<Invoice> invoices = flReader.readInvoices();
			
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
			System.out.println(String.format("TOTALS %-65s $%-9.2f $%-5.2f $%-6.2f $%-7.2f $%-7.2f\n\n", "",subInvoiceSubtotal,subInvoiceTotalFees,subInvoiceTotalTaxes, subInvoiceTotalDiscount, subInvoiceGrandTotal));
			
			
	    }
	}

