package Ficha2;

import Exceptions.EmptyCollectionException;

public class LinkedList<T> {

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
    public String toString() {
        return "LinkedList{" +
                "front=" + front.toString() +
                ", count=" + count +
                '}';
    }
}
