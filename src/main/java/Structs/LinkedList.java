package Structs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

    private Node<T> front;
    private int count;

    public LinkedList()
    {
        this.front = null;
        this.count = 0;
    }

    public void add(T object)
    {
        Node<T> newNode = new Node(object);

        if(this.count == 0)
        {
            this.front = newNode;
        }
        else
        {
            newNode.setNext(this.front);
            this.front = newNode;
        }

        this.count++;
    }

    public void remove() throws EmptyCollectionException
    {
        if(isEmpty()){
            throw new EmptyCollectionException("List is empty !");
        }

        this.front = this.front.getNext();
        this.count--;

    }

    public boolean isEmpty()
    {
        if(this.count == 0)
            return true;

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new myItr();
    }

    private class myItr implements Iterator{

        private Node<T> current;

        public myItr() {
        current = front;
        }

        @Override
        public boolean hasNext() {

            return current != null;
        }

        @Override
        public Object next() {
            if(!hasNext())
                throw new ArrayIndexOutOfBoundsException("There isn't next element !");

            T element = current.getObject();

            current = current.getNext();

            return element;
        }

        @Override
        public void remove() {

            try {

                LinkedList.this.remove();

            } catch (EmptyCollectionException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }

//    @Override
//    public String toString() {
//        return "LinkedList{" +
//                "front=" + front.toString() +
//                ", count=" + count +
//                '}';
//    }
}
