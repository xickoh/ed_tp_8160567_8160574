package Structs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        count = 0;
        tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root of the new tree
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[CAPACITY];
        tree[0] = element;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the element is in the tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    public T find(T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }
        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }
        return temp;
    }

    @Override
    public T getRoot() {
        return tree[0];
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
       return this.count;
    }

    @Override
    public boolean contains(T targetElement) throws ElementNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder(0, templist);
        return templist.iterator();
    }

    protected void inorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length) {
            if (tree[node] != null) {
                inorder(node * 2 + 1, templist);
                templist.addToRear(tree[node]);
                inorder((node + 1) * 2, templist);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder(0, templist);
        return templist.iterator();
    }

    protected void preorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length) {
            if (tree[node] != null) {
                templist.addToRear(tree[node]);
                preorder(node * 2 + 1, templist);
                preorder((node + 1) * 2, templist);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postorder(0, templist);
        return templist.iterator();
    }

    protected void postorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length) {
            if (tree[node] != null) {
                postorder(node * 2 + 1, templist);
                postorder((node + 1) * 2, templist);
                templist.addToRear(tree[node]);
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException {
        LinkedQueue<Integer> values = new LinkedQueue<>();
        ArrayUnorderedList<Integer> results = new ArrayUnorderedList<>();
        int index = 0;

        values.enqueue(0);

        while (!(values.isEmpty())) {

            try {
                index = values.dequeue();

                if (tree[index] != null) {
                    results.addToRear((Integer) tree[index]);

                    if (this.tree[(index * 2) + 1] != null) {
                        values.enqueue(index * 2 + 1);
                    }

                    if (this.tree[(index + 1) * 2] != null) {
                        values.enqueue((index + 1) * 2);
                    }
                }
            } catch (EmptyCollectionException ex) {
                System.out.println("Vazio!!!");
            }

        }

        return results.iterator();
    }

    public String toString(Iterator itr) {

        String str = "";

        while (itr.hasNext()){
            str += " " + itr.next();
        }

        return str;
    }
}