package Ficha5;

import Exceptions.ElementNotFoundException;

public interface UnorderedListADT <T> extends ListADT<T> {

    public void addToFront(T element);

    public void addToRear(T element);

    public void addAfter(T element, T target) throws ElementNotFoundException;

}
