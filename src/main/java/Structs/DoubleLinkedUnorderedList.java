package Structs;

import Exceptions.ElementNotFoundException;


public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T>{

    private DoubleNode<T> current;


    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {

        this.current = this.head;
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if(this.current == null){

            this.head = newNode;
            this.tail = newNode;
        } else {

            this.current.setPrevius(newNode);
            newNode.setNext(current);
            this.head = newNode;
        }

        this.count++;
    }

    @Override
    public void addToRear(T element) {

        current = this.tail;
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if(this.current == null){

            this.head = newNode;
            this.tail = newNode;
        } else {

            current.setNext(newNode);
            newNode.setPrevius(current);
            tail = newNode;
        }

        this.count++;

    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException{

        this.current = this.head;
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if(!contains(target))
            throw new ElementNotFoundException("tar not found");

        while(!this.current.getElement().equals(target) && current != null){

            current = current.getNext();
        }

        this.current.getNext().setPrevius(newNode);
        newNode.setNext(current.getNext());

        newNode.setPrevius(current);
        this.current.setNext(newNode);

        this.count++;
    }
}
