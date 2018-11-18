package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E>{

	private int front, rear, maxCapacity, currentSize;
	private E[] storage;
	
	// two constructors
	public ArrayLinearList(int maxSize){
		currentSize = 0;
		maxCapacity = maxSize;
		storage = (E[])new Object[maxCapacity];
		front = maxCapacity*(1/2);
		rear = maxCapacity*(1/2);
	}

	public ArrayLinearList(){
		currentSize = 0;
		maxCapacity = DEFAULT_MAX_CAPACITY;
		storage = (E[])new Object[DEFAULT_MAX_CAPACITY];
		front = DEFAULT_MAX_CAPACITY*(1/2);
		rear = DEFAULT_MAX_CAPACITY*(1/2);
	}
	
	public boolean addFirst(E obj) {
	//  Adds the Object obj to the beginning of list and returns true if the list is not full.
	//  returns false and aborts the insertion if the list is full.
		if(isFull())
			return false;
		if(--front < 0)
			front = maxCapacity-1;
		storage[front] = obj;
		if(currentSize == 0)
			rear = front;
		currentSize++;
		return true;
	    }
		
	public boolean addLast(E obj) {
	//  Adds the Object obj to the end of list and returns true if the list is not full.
	//  returns false and aborts the insertion if the list is full..
		if(isFull())
			return false;
		if(++rear == maxCapacity)
			rear = 0;
		storage[rear] = obj;
		if(currentSize == 0)
			front = rear;
		currentSize++;
		return true;
	}

	public E removeFirst() {
	//  Removes and returns the parameter object obj in first position in list if the list is not empty,
	//  null if the list is empty.
		E temp;
		if(isEmpty())
			return null;
		if(++front >= maxCapacity)
			front = 0;
		temp = storage[front];
		currentSize--;
		return temp;
	}

	public E removeLast() {
	//  Removes and returns the parameter object obj in last position in list if the list is not empty,
	//  null if the list is empty.
		E temp;
		if(isEmpty())
			return null;
		if(--rear<0)
			rear=maxCapacity-1;
		temp = storage[rear];
		currentSize--;
		return temp;
	}

	public E remove(E obj){
		int index = front;
		int count = 0;
		while(count != currentSize){
			E temp = storage[index];
			if(((Comparable<E>) obj).compareTo(temp) == 0){
				while(index != rear){ //shifting
					if(index==(maxCapacity-1)){
						storage[index]=storage[0];
						index=0;
					}
					storage[index]=storage[++index];		
				}
				rear--;
				currentSize--;
				return temp;
			}
			index++;
			if(index == maxCapacity)
				index=0;
			count++;
		}
		return null;
	}

	public E peekFirst() {
	//  Returns the first element in the list, null if the list is empty.
	//  The list is not modified.
		if(isEmpty())
			return null;
		return storage[front];
	}
	
	public E peekLast() {
	//  Returns the last element in the list, null if the list is empty.
	//  The list is not modified.
		if(isEmpty())
			return null;
		return storage[rear];
	}

	public boolean contains(E obj) {
	//  Returns true if the parameter object obj is in the list, false otherwise.
	//  The list is not modified.
		if(find(obj) != null)
			return true;
		return false;
	}

	public E find(E obj) {
	//  Returns the element matching obj if it is in the list, null otherwise.
	//  In the case of duplicates, this method returns the element closest to front.
	//  The list is not modified.
		int index = front;
		int count = 0;
		while(count != currentSize){
			if(((Comparable<E>) obj).compareTo(storage[index]) == 0)
				return storage[index];
			index++;
			if(index == maxCapacity)
				index=0;
			count++;
		}
		return null;
	}

	public void clear() {
	//  The list is returned to an empty state.
		storage = (E[]) new Object[maxCapacity];
		currentSize = 0;
	}

	public boolean isEmpty() {
	//  Returns true if the list is empty, otherwise false
			return (currentSize == 0);
	}

	public boolean isFull() {
	//  Returns true if the list is full, otherwise false
		return (currentSize == maxCapacity);
	}

	public int size() {
	//  Returns the number of Objects currently in the list.
		return currentSize;
	}

	public Iterator<E> iterator() {
	//  Returns an Iterator of the values in the list, presented in
	//  the same order as the underlying order of the list. (front first, rear last)
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E>{
		private int counter, iterIndex;
		public IteratorHelper(){
			iterIndex = front;
			counter = 0;
		}
		@Override
		public boolean hasNext() {
			return (counter < currentSize);
		}
		@Override
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			counter++;
			E temp = storage[iterIndex];
			if(iterIndex==maxCapacity-1)
				iterIndex=0;
			else
				iterIndex++;
			return temp;
		}
		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}
