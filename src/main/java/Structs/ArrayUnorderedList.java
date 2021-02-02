package Ficha5;

import Exceptions.ElementNotFoundException;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {

        if(this.count == this.list.length)
            expandCapacity();

        for(int i = this.count-1; i >= 0; i--){

            this.list[i+1] = this.list[i];
        }

        this.list[0] = element;

        this.count++;

    }

    @Override
    public void addToRear(T element) {

        if(this.count == this.list.length)
            expandCapacity();

        this.list[this.count] = element;

        this.count++;

    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {

        int position = 0;
        boolean found = false;

        if(this.count == this.list.length)
            expandCapacity();

        while(!found && position < size()){

            if(target.equals(this.list[position])) {

                found = true;
            } else {

                position++;
            }

        }

        for(int i = size()-1; i > position; i--){

            this.list[i+1] = this.list[i];
        }

        this.list[position+1] = element;
        this.count++;


    }
}
