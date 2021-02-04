package Structs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

public class ArrayList<T> implements ListADT<T>, Iterable<T> {

    private final int DEFAULT_CAPACITY = 20;

    protected T[] list;
    protected int count;

    public ArrayList() {
        this.list = (T[]) new Object[DEFAULT_CAPACITY];
        this.count = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {

        if(isEmpty())
            throw new EmptyCollectionException("List is empty !");

        T removed = this.list[0];

        for(int i = 0; i < count-1; i++){
            this.list[i] = this.list[i+1];
        }
        this.count--;
        this.list[this.count] = null;

        return removed;
    }

    @Override
    public T removeLast() throws EmptyCollectionException{

        if(isEmpty())
            throw new EmptyCollectionException("List is empty !");

        T removed = this.list[count-1];

        this.count--;
        this.list[this.count] = null;

        return removed;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {

        if(isEmpty())
            throw new EmptyCollectionException("List is empty !");

        T removed;
        int i = 0;
        boolean found = false;

        while (i < count && !found){

            if (this.list[i] == element){
                   found =true;
            }
            i++;
        }

        if(!found)
            throw new ElementNotFoundException("Element not found !");

        removed = this.list[i];

        for(int j = i; j < count-1; j++){
            this.list[j] = this.list[j+1];
        }
        this.count--;
        this.list[this.count] = null;

        return removed;
    }

    @Override
    public T first() {
        return this.list[0];
    }

    @Override
    public T last() {
        return this.list[count-1];
    }

    @Override
    public boolean contains(T target) {

        int i = 0;
        boolean found = false;

        while (i < count && !found){
            if (this.list[i] == target){
                found = true;
            }
            i++;
        }

        return found;
    }

    @Override
    public boolean isEmpty() {

        if(this.count == 0)
            return true;

        return false;
    }

    @Override
    public int size() {

        return this.count;
    }

    @Override
    public Iterator iterator() {
        return new myItr();
    }

    private class myItr<T> implements Iterator<T> {

        private int current;

        public myItr() {
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            return current != size();
        }

        @Override
        public T next() {

            if(!hasNext())
                throw new ArrayIndexOutOfBoundsException("There is no next element !");

            return (T) list[current++];
        }

    }

    public void expandCapacity(){

        T[] copArray = (T[])new Object[list.length * 2];

        for(int i = 0; i < list.length; i++)
        {
            copArray[i] = list[i];
        }

        list = copArray;

    }

    @Override
    public String toString() {
        String str = "";
        int i = 0;

        while(i < this.count ){

            str += "Position["+ i +"]: "+ list[i] + "\n";
            i++;
        }

        return str;
    }
}
