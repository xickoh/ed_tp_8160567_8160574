package Ficha3;

import Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 2;
    private int top;
    private T[] stack;

    public ArrayStack() {
        this.stack = (T[])(new Object[DEFAULT_CAPACITY]);
        this.top = 0;
    }

    @Override
    public void push(T element) {
        if(size() == stack.length){
            expandCapacity();
        }

        stack[top] = element;
        top++;
    }

    @Override
    public T pop() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Stack is empty !");

        T removed = stack[top-1];
        stack[top-1] = null;
        top--;

        return removed;
    }

    @Override
    public T peek() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("Stack is empty !");

        return stack[top-1];
    }

    @Override
    public boolean isEmpty() {

        if(top == 0)
            return true;

        return false;
    }

    @Override
    public int size() {

        return top;
    }

    public void expandCapacity(){

        T[] copArray = (T[])new Object[stack.length * 2];

        for(int i = 0; i < stack.length; i++)
        {
            copArray[i] = stack[i];
        }

        stack = copArray;

        System.out.println("Capacity extended");

    }

    @Override
    public String toString() {

        String str = "";

        for(int i = 0; i < size(); i++){
            str += "Stack position[" + i + "]: " + stack[i] + "\n";
        }
        return str;
    }
}
