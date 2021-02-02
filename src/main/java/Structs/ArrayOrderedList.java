package Structs;

import Exceptions.NotComparableException;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T>{

    public ArrayOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NotComparableException{

        if (!(element instanceof Comparable))
            throw new NotComparableException("Element not comparable !");


        if( this.count == this.list.length )
            expandCapacity();

        Comparable<T> comp = (Comparable<T>) element;
        int i = 0;
        boolean bigger = false;

        while(!bigger && i < this.count){

            if(comp.compareTo(list[i]) > 0){
                bigger = true;
            }else {
                i++;
            }
        }

        for (int j = this.count; j > i; j--){
            this.list[j] = this.list[j-1];
        }

        this.count++;
        this.list[i] = element;
    }
}
