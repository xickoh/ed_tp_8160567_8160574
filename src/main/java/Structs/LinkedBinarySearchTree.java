package Ficha10;


import Exceptions.ElementNotFoundException;
import Ficha9.BinaryTreeNode;
import Ficha9.LinkedBinaryTree;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {


    public LinkedBinarySearchTree() {
        super();
    }

    @Override
    public void addElement(T element) {

        BinaryTreeNode<T> temp = new BinaryTreeNode<>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;

        if(isEmpty()){
            root = temp;
        } else {
            BinaryTreeNode<T> current = root;
            boolean added = false;

            while(!added){
                if(comparableElement.compareTo(current.getElement()) < 0){
                    if(current.getLeft() == null){
                        current.setLeft(temp);
                        added = true;
                    }
                    else {
                        current = current.getLeft();
                    }
                }
                else {
                    if(current.getRight() == null){
                        current.setRight(temp);
                        added = true;
                    }
                    else {
                        current = current.getRight();
                    }
                }
            }
        }

        this.count++;
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;
        if (!isEmpty())
        {
            if ((targetElement).equals(root.getElement()))
            {
                result = root.getElement();
                root = replacement(root);
                count--;
            }
            else
            {
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;
                if (((Comparable)targetElement).compareTo(root.getElement()) < 0)
                    current = root.getLeft();
                else
                    current = root.getRight();
                while (current != null && !found)
                {
                    if (targetElement.equals(current.getElement()))
                    {
                        found = true;
                        count--;
                        result = current.getElement();

                        if (current == parent.getLeft())
                        {
                            parent.setLeft(replacement(current));
                        }
                        else
                        {
                            parent.setRight(replacement(current));
                        }
                    }
                    else
                    {
                        parent = current;

                        if (((Comparable)targetElement).compareTo(current.getElement()) < 0)
                            current = current.getLeft();
                        else
                            current = current.getRight();
                    }
                } //while

                if (!found)
                    throw new ElementNotFoundException("binary search tree");
            }
        } // end outer if
        return result;
    }

    protected BinaryTreeNode<T> replacement (BinaryTreeNode<T> node)
    {
        BinaryTreeNode<T> result = null;

        if ((node.getLeft() == null) && (node.getRight() == null))
            result = null;

        else if ((node.getLeft() != null) && (node.getRight() == null))
            result = node.getLeft();

        else if ((node.getLeft() == null) && (node.getRight() != null))
            result = node.getRight();

        else
        {
            BinaryTreeNode<T> current = node.getRight();
            BinaryTreeNode<T> parent = node;
            while (current.getLeft() != null)
            {
                parent = current;
                current = current.getLeft();
            }
            if (node.getRight() == current)
                current.setLeft(node.getLeft());

            else
            {
                parent.setLeft(current.getRight());
                current.setRight(node.getRight());
                current.setLeft(node.getLeft());
            }
            result = current;
        }
        return result;
    }

    @Override
    public void removeAllOccurrences(T targetElement) {

        try {
            while(contains(targetElement)){
                removeElement(targetElement);
            }
        }catch (ElementNotFoundException ex){
            ex.getMessage();
        }

    }

    @Override
    public T removeMin() {

        T minElement = findMin();

        try {

            T deleted = removeElement(minElement);
            return deleted;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public T removeMax() {

        T maxElement = findMax();

        try {

            T deleted = removeElement(maxElement);
            return deleted;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public T findMin() {

        BinaryTreeNode<T> current = root;

        while (current.getLeft() != null){
            current = current.getLeft();
        }

        return current.getElement();
    }

    @Override
    public T findMax() {

        BinaryTreeNode<T> current = root;

        while (current.getRight() != null){
            current = current.getRight();
        }

        return current.getElement();
    }
}
