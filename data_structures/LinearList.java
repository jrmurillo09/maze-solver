/*  Jose Murillo
	cssc0067
*/
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinearList<E> implements LinearListADT<E>{
	private Node head, tail;
	private int currentSize;
	private long modificationCounter;
	public class IteratorHelper implements Iterator<E>{
		Node iterIndex; 
		private long modCheck;
		
		public IteratorHelper(){
			iterIndex = head;
			modCheck = modificationCounter;
		}
		@Override
		public boolean hasNext() {
			if(modCheck != modificationCounter)
				throw new ConcurrentModificationException();
			return iterIndex != null;
		}

		@Override
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			E temp = iterIndex.data;
			iterIndex = iterIndex.next;
			return temp;
		}
		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	public class Node{ //or private class Node<E>???
		E data;
		Node prev;
		Node next;  //Node<E>next;???
		
		public Node(E value){
			data = value;
			prev = null;
			next = null;
		}
	}
	
	public LinearList(){
		head=tail=null;
		currentSize = 0;
		modificationCounter = 0;
	}
	
	@Override
	public boolean addFirst(E obj) {
		Node newNode = new Node(obj);
        if (isEmpty())
            head = tail = newNode;
        else{
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        modificationCounter++;
        currentSize++;
		return true;
	}

	@Override
	public boolean addLast(E obj) {
		 Node newNode = new Node(obj);
	     if (isEmpty())
	         head = tail = newNode;
	     else {
	    	 tail.next = newNode;
	    	 newNode.prev = tail; //tail is pointing to previous Node
	    	 tail = newNode;
	     }
	     modificationCounter++;
	     currentSize++;
	     return true;
	}

	@Override
	public E removeFirst() {
		if (isEmpty())
			return null;
		E temp = head.data;
        if (head == tail) 
            head = tail = null;
        else{
        	head = head.next;
        	head.prev = null;
        }
        modificationCounter++;
        currentSize--;
		return temp;
}	

	@Override
	public E removeLast() {
		if(isEmpty())
			return null;
		E temp = tail.data;
		if(head == tail)
			head = tail = null;
		else{
			tail = tail.prev;
			tail.next = null;
		}
	    modificationCounter++;
        currentSize--;
		return temp;	
	}

	@Override
	public E remove(E obj) {
		Node curr = head;
		while(curr != null && ((Comparable<E>) obj).compareTo(curr.data) != 0){ 
			curr = curr.next;
		}
		if(head == tail)
			head = tail = null;
		if (curr==null)
			return null;
		E temp = curr.data;
		if(curr == head){
			head = head.next;
        	head.prev = null;
		}
		if(curr == tail){
			tail = tail.prev;
			tail.next = null;
		}
		else if(curr.prev != null){
			curr.prev.next = curr.next;
			curr.next.prev = curr.prev;
		}
		modificationCounter++;
		currentSize--;
		return temp;
	}
		
			
//		while(index.hasNext()){
//			temp = index.next();
//			if(((Comparable<E>) obj).compareTo(temp) == 0){
//				while(index.hasNext()){
//					else{
//			    		Node current=head;
//			        	while(current.next.next!=null){
//			        		current=current.next;
//			   			}
//			   		current.next=null;
//			    	temp = current.data;
//			        }
//				}
//				return temp;
//			}
//		}
		
		//Use find method, then 3 conditionals, if curr=head, curr=middle and curr=tail

	@Override
	public E peekFirst() {
		if(isEmpty())
			return null;
		return head.data;
	}

	@Override
	public E peekLast() {
		if(isEmpty())
			return null;
		return tail.data;
	}

	@Override
	public boolean contains(E obj) {
		return(find(obj) != null);
	}
	@Override
	public E find(E obj) {
		Node curr = head;
		while(curr != null && ((Comparable<E>) obj).compareTo(curr.data) != 0){ 
			curr = curr.next;
		}
		if (curr==null)
			return null;
		return curr.data;
	}
	
	@Override
	public void clear() {
		head = tail = null;
		currentSize = 0;
	}

	@Override
	public boolean isEmpty() {
        return (head == null);
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
}
