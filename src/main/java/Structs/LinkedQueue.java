package Structs;

import Exceptions.EmptyCollectionException;

import java.util.Iterator;


public class LinkedQueue<T> implements QueueADT<T>, Iterable<T> {

    private Node<T> head, tail;
    private int count;

    public LinkedQueue() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    @Override
    public void enqueue(T element) {

        Node<T> newNode = new Node<>(element);

        if(this.count == 0){

            this.head = newNode;
            this.tail = newNode;

        } else {

            this.tail.setNext(newNode);
            this.tail = newNode;

        }

        count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Queue is empty !!");

        T removed = this.head.getObject();

        if(this.count == 1){

            this.head = null;
            this.tail = null;

        } else {

            this.head = this.head.getNext();

        }

        count--;

        return removed;
    }

    @Override
    public T first() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Queue is empty !!");

        return this.head.getObject();
    }

    @Override
    public boolean isEmpty() {

        if(count == 0)
            return true;

        return false;
    }

    @Override
    public int size() {

        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new myItr();
    }

    private class myItr implements Iterator {

        private Node<T> current;

        public myItr() {
            current = head;
        }

        @Override
        public boolean hasNext() {

            return current != null;
        }

        @Override
        public Object next() {
            if (!hasNext())
                throw new ArrayIndexOutOfBoundsException("There isn't next element !");

            T element = current.getObject();

            current = current.getNext();

            return element;
        }
    }

    @Override
    public String toString() {

        String str = "";

        Node<T> current = this.head;

        while(current != null){
            str += current.getObject() + "\n";
            current = current.getNext();
        }

        return str;
    }
}
