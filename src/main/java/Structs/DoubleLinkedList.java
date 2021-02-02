package Structs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;


import java.util.Iterator;

public class DoubleLinkedList<T> implements ListADT<T>, Iterable<T> {

    protected DoubleNode<T> head, tail;
    protected int count;

    public DoubleLinkedList() {

        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("List is Empty");

        T removed = this.head.getElement();

        this.head = this.head.getNext();
        this.head.setPrevius(null);

        this.count--;

        return removed;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("List is Empty");

        T removed = this.tail.getElement();

        this.tail = this.tail.getPrevius();
        this.tail.setNext(null);

        this.count--;

        return removed;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {

        if(isEmpty())
            throw new EmptyCollectionException("List is Empty");

        T removed;
        boolean found = false;
        DoubleNode<T> current = this.head;

        while(current != null && !found){

            if(element.equals(current.getElement())) {

                found = true;

            } else {

                current = current.getNext();
            }

        }

        if(!found)
            throw new ElementNotFoundException("Element does not exist ");

        if(current == this.head){

            removed = removeFirst();

        } else if(current == this.tail){

            removed = removeLast();

        } else {

            removed = current.getElement();

            current.getNext().setPrevius(current.getPrevius());
            current.getPrevius().setNext(current.getNext());

        }

        this.count--;

        return removed;
    }

    @Override
    public T first() {
        return this.head.getElement();
    }

    @Override
    public T last() {
        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {

        boolean found = false;
        DoubleNode<T> current = this.head;

        while(current != null && !found){

            if(target.equals(current.getElement())) {

                found = true;
            } else {

                current = current.getNext();
            }
        }

        return found;
    }

    @Override
    public boolean isEmpty() {

        if(count == 0)
            return true;

        return false;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public Iterator<T> iterator() {
        return new myItr();
    }

    private class myItr implements Iterator{

        private DoubleNode<T> current;

        public myItr() {

        }

        @Override
        public boolean hasNext() {

            return current != null;
        }

        @Override
        public Object next() {
            if(!hasNext())
                throw new ArrayIndexOutOfBoundsException("The isn't next element !");

            T element = current.getElement();

            current = current.getNext();

            return element;
        }

        @Override
        public void remove() {

            try {

                DoubleLinkedList.this.remove((this.current.getElement()));

            } catch (ElementNotFoundException | EmptyCollectionException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public String toString() {

        String str = "";

        DoubleNode<T> current = this.head;

        while (current != null)
        {
            str += current.toString() + "\n";
            current = current.getNext();
        }

        return str;
    }
}
