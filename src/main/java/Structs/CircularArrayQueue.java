package Ficha4;

import Exceptions.EmptyCollectionException;
import Ficha2.Node;

public class CircularArrayQueue<T> implements QueueADT<T>{

    private final int DEFAULT_CAPACITY = 50;
    private int head, rear, count;
    private T[] arrayCircular;

    public CircularArrayQueue() {

        arrayCircular = (T[]) new Object[DEFAULT_CAPACITY];
        this.head = 0;
        this.rear = 0;
        this.count = 0;
    }

    @Override
    public void enqueue(T element) {

        if(this.count == this.arrayCircular.length)
            expandCapacity();

        this.arrayCircular[this.rear] = element;
        this.rear = (this.rear + 1) % this.arrayCircular.length;
        count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("CircularArray is empty !!!");

        T removed = this.arrayCircular[this.head];

        this.arrayCircular[this.head] = null;
        this.head = (this.head + 1) % this.arrayCircular.length;

        return removed;
    }

    @Override
    public T first() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("CircularArray is empty !!!");

        return this.arrayCircular[this.head];
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

    public void expandCapacity(){

        T[] copArray = (T[])new Object[arrayCircular.length * 2];

        for(int i = 0; i < arrayCircular.length; i++)
        {
            copArray[i] = arrayCircular[i];
        }

        arrayCircular = copArray;

        System.out.println("Capacity extended");

    }

    @Override
    public String toString() {
        String str = "";

        for(int i = this.head; i < this.rear; i++ ){
            str += "Queue position["+i+"]: "+this.arrayCircular[i]+"\n";
        }

        return str;
    }
}
