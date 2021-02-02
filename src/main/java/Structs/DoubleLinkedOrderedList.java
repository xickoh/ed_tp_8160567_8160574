package Structs;

import Exceptions.NotComparableException;

public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    public DoubleLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NotComparableException {

        if (!(element instanceof Comparable))
            throw new NotComparableException("Element not comparable!");


        Comparable<T> comp = (Comparable<T>) element;

        DoubleNode<T> newNode = new DoubleNode<>(element);
        DoubleNode<T> current = this.head;

        if(isEmpty()){

            this.head = newNode;
            this.tail = newNode;

        } else if(comp.compareTo(current.getElement()) >= 0){

            current.setPrevius(newNode);
            newNode.setNext(current);
            this.head = newNode;

        } else if(comp.compareTo(this.tail.getElement()) < 0){

            this.tail.setNext(newNode);
            newNode.setPrevius(this.tail);
            this.tail = newNode;

        } else {

            while(comp.compareTo(current.getElement()) < 0 && current != null){

                current = current.getNext();
            }

            current.getPrevius().setNext(newNode);
            newNode.setPrevius(current.getPrevius());
            current.setPrevius(newNode);
            newNode.setNext(current);

        }

        this.count++;

    }
}
