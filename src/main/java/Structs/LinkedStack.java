package Structs;

import Exceptions.EmptyCollectionException;


public class LinkedStack<T> implements StackADT<T>{

    private Node<T> top;
    private int count;

    public LinkedStack(T element) {

        Node<T> newNode = new Node<>(element);

        if (top == null) {

            this.top = newNode;
        } else {

            newNode.setNext(top);
            this.top = newNode;
        }

        this.count++;
    }

    public LinkedStack() {
        this.top = null;
        this.count = 0;
    }


    @Override
    public void push(T element) {

        Node<T> newNode = new Node<>(element);

        if (top == null) {
            this.top = newNode;

        } else {
            newNode.setNext(top);
            this.top = newNode;
        }

        this.count++;
    }

    @Override
    public T pop() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Stack is empty !!");

        T removed = this.top.getObject();

        this.top = this.top.getNext();
        this.count--;

        return removed;
    }

    @Override
    public T peek() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Stack is empty !!");

        return this.top.getObject();
    }

    @Override
    public boolean isEmpty() {

        if(this.count == 0)
            return true;

        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {

        String str = "";

        Node<T> current = this.top;

        while (current != null)
        {
            str += current.toString() + "\n";
            current = current.getNext();
        }

        return str;
    }
}
