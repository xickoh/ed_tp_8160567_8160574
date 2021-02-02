package Ficha10;

import Exceptions.ElementNotFoundException;
import Ficha9.ArrayBinaryTree;

public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    protected int height;
    protected int maxIndex;

    public ArrayBinarySearchTree() {

        super();
        this.height = 0;
        this.maxIndex = -1;
    }

    public ArrayBinarySearchTree (T element)
    {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    @Override
    public void addElement(T element) {

        if (tree.length < maxIndex * 2 + 3 )
            expandCapacity();

        Comparable<T> tempelement = (Comparable<T>)element;

        if (isEmpty())
        {
            tree[0] = element;
            maxIndex = 0;
        }
        else
        {
            boolean added = false;
            int currentIndex = 0;
            while (!added)
            {
                if (tempelement.compareTo((tree[currentIndex]) ) < 0)
                {
                    /** go left */
                    if (tree[currentIndex*2+1] == null)
                    {
                        tree[currentIndex*2+1] = element;
                        added = true;
                        if (currentIndex*2+1 > maxIndex)
                            maxIndex = currentIndex*2+1;
                    }
                    else
                        currentIndex = currentIndex*2+1;
                }
                else
                {
                    /** go right */
                    if (tree[currentIndex*2+2] == null)
                    {
                        tree[currentIndex*2+2] = element;
                        added = true;
                        if (currentIndex*2+2 > maxIndex)
                            maxIndex = currentIndex*2+2;
                    }
                    else
                        currentIndex = currentIndex*2+2;
                }
            }
        }
        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void removeAllOccurrences(T targetElement) {

    }

    @Override
    public T removeMin() {
        return null;
    }

    @Override
    public T removeMax() {
        return null;
    }

    @Override
    public T findMin() {
        return null;
    }

    @Override
    public T findMax() {
        return null;
    }

    public void expandCapacity(){

        T[] copArray = (T[])new Object[tree.length * 2];

        for(int i = 0; i < tree.length; i++)
        {
            copArray[i] = tree[i];
        }

        tree = copArray;

        System.out.println("Capacity extended");

    }
}
