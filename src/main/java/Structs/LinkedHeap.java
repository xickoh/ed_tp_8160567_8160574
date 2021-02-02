package Structs;

import Exceptions.EmptyCollectionException;


public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {

    public HeapNode<T> lastNode;

    public LinkedHeap() {
        super();
    }

    @Override
    public void addElement (T obj) {

        HeapNode<T> node = new HeapNode<>(obj);

        if (root == null)

            root = node;

        else {

            HeapNode<T> next_parent = getNextParentAdd();

            if (next_parent.getLeft() == null)
                next_parent.setLeft(node);

            else
                next_parent.setRight(node);

            node.parent = next_parent;
        }
        lastNode = node;
        count++;
        if (count>1)
            heapifyAdd();
    }

    private HeapNode<T> getNextParentAdd() {

        HeapNode<T> result = lastNode;

        while ((result != root) && (result.parent.getLeft() != result))
            result = result.parent;

        if (result != root)

            if (result.parent.getRight() == null)

                result = result.parent;
            else {

                result = (HeapNode<T>)result.parent.getRight();

                while (result.getLeft() != null)

                    result = (HeapNode<T>)result.getLeft();
            }
        else

            while (result.getLeft() != null)

                result = (HeapNode<T>)result.getLeft();

        return result;
    }

    private void heapifyAdd() {

        T temp;

        HeapNode<T> next = lastNode;

        temp = next.getElement();

        while ((next != root) && (((Comparable)temp).compareTo
                (next.parent.getElement()) < 0))
        {
            next.setElement(next.parent.getElement());
            next = next.parent;
        }
        next.setElement(temp);
    }

    @Override
    public T removeMin() throws EmptyCollectionException {

        if (isEmpty())
            throw new EmptyCollectionException ("Empty Heap");

        T minElement = root.getElement();

        if (count == 1)
        {
            root = null;
            lastNode = null;
        }
        else
        {
            HeapNode<T> next_last = getNewLastNode();
            if (lastNode.parent.getLeft() == lastNode)

                lastNode.parent.setLeft(null);

            else

                lastNode.parent.setRight(null);

            root.setElement(lastNode.getElement());
            lastNode = next_last;

            heapifyRemove();
        }

        count--;

        return minElement;
    }

    private HeapNode<T> getNewLastNode(){

        HeapNode<T> result = lastNode;

        while ((result != root) && (result.parent.getLeft() == result))
            result = result.parent;

        if (result != root)
            result = (HeapNode<T>)result.parent.getLeft();

        while (result.getRight() != null)
            result = (HeapNode<T>)result.getRight();

        return result;
    }

    private void heapifyRemove(){
        T temp;
        HeapNode<T> node = (HeapNode<T>)root;
        HeapNode<T> left = (HeapNode<T>)node.getLeft();
        HeapNode<T> right = (HeapNode<T>)node.getRight();
        HeapNode<T> next;

        if ((left == null) && (right == null))
            next = null;
        else if (left == null)
            next = right;
        else if (right == null)
            next = left;
        else if (((Comparable)left.getElement()).compareTo(right.getElement()) < 0)
            next = left;
        else
            next = right;

        temp = node.getElement();
        while ((next != null) && (((Comparable)next.getElement()).compareTo(temp) < 0))
        {
            node.setElement(next.getElement());
            node = next;
            left = (HeapNode<T>)node.getLeft();
            right = (HeapNode<T>)node.getRight();

            if ((left == null) && (right == null))
                next = null;
            else if (left == null)
                next = right;
            else if (right == null)
                next = left;
            else if (((Comparable)left.getElement()).compareTo(right.getElement()) < 0)
                next = left;
            else
                next = right;
        }
        node.setElement(temp);
    }

    @Override
    public T findMin() {

        return this.root.getElement();

    }
}
