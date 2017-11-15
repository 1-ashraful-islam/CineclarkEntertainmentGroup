package org.cineclark.invoiceADT;
import org.cineclark.datacontainers.Invoice;

public class InvoiceList {
	
		private InvoiceListNode start;
		private InvoiceListNode end;
		private int size;

		public void clear() { 
			InvoiceListNode previousNode=start;
			if(previousNode.getNext() !=null){
				previousNode=previousNode.getNext();
				InvoiceListNode nodeToBeRemoved =previousNode.getNext();
				InvoiceListNode nextNode=nodeToBeRemoved.getNext();
				previousNode.setNext(nextNode);
				size--;
			}
			
		}
		
	   public void addToStart(InvoiceDriver t) {
	    	InvoiceListNode aNode= new InvoiceListNode(t);
	    	size++;
	    	if(start==null){
	    		start=aNode;
	    		end=start;
	    	}
	    	else {
	    		aNode.setNext(start);
	    		start=aNode;
	    	}
	     }
	   
	 
	    public void addToEnd(InvoiceDriver t) {
	    	InvoiceListNode aNode= new InvoiceListNode(t);
	    	size++;
	    	if(start==null){
	    		start=aNode;
	    		end=start;
	    	}
	    	else {
	    		end.setNext(aNode);
	    		end = aNode;
	    	}
	      }

	    
	    public void remove(int position) {
	    	InvoiceListNode previousNode=start;
	    	try{
	    		for(int i=0; i<position-1;i++){
	    			if(previousNode.getNext() !=null){
	    				if(position==1){
	    					start=start.getNext();
	    					size--;
	    				}
	    				else{
	    				previousNode=previousNode.getNext();
	    				InvoiceListNode nodeToBeRemoved =previousNode.getNext();
	    				InvoiceListNode nextNode=nodeToBeRemoved.getNext();
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
	    	
	    private InvoiceListNode getInvoiceListNode(int position) {
			InvoiceListNode headNode=start;
			for(int i=0; i< position; i++){
	    		if(headNode.getNext()==null)
	    			return null;
	    		headNode=headNode.getNext();
	    	}
	    	return headNode;
	    	
	    }
	    

	    public InvoiceDriver getInvoice(int position) {
	    	InvoiceListNode headNode=start;
	    	
	    	for(int i=0; i< position; i++){
	    		if(headNode.getNext()==null)
	    			return null;
	    		headNode=headNode.getNext();
	    	}
	    	return headNode.getInvoice();
	      }
	    
	    
	    
	    //TODO: Print the list to the standard output in a readable format
	    //Invoice.toString()
	    public void print() {
		InvoiceListNode headNode=start;
		
		for(int i=0; i<size; i++){
			//InvoiceListNode nodeToPrint =headNode.getNext();
			//InvoiceListNode nextNode=nodeToPrint.getNext();
			System.out.println(getInvoice(i).toString());
			//headNode=headNode.getNext();
		}	
	    }

	}

