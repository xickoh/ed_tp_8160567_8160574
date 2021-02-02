package Structs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    public int count;
    public BinaryTreeNode<T> root;

    public LinkedBinaryTree() {
        this.count = 0;
        this.root = null;
    }

    public LinkedBinaryTree (T element)
    {
        count = 1;
        root = new BinaryTreeNode<T> (element);
    }

    @Override
    public T getRoot() throws EmptyCollectionException{
        if(isEmpty())
            throw new EmptyCollectionException("Tree is empty !");

        return this.root.element;
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
    public boolean contains(T targetElement) {

        BinaryTreeNode<T> current = findAgain( targetElement, root );

        if( current == null )
            return false;

        return true;
    }

    @Override
    public T find(T targetElement) throws ElementNotFoundException {

        BinaryTreeNode<T> current = findAgain( targetElement, root );

        if( current == null )
            throw new ElementNotFoundException("binary tree");

        return (current.element);
    }

    private BinaryTreeNode<T> findAgain(T targetElement,
                                        BinaryTreeNode<T> next) {
        if (next == null)
            return null;

        if (next.element.equals(targetElement))
            return next;

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null)
            temp = findAgain(targetElement, next.right);

        return temp;
    }

    @Override
    public Iterator<T> iteratorInOrder() {

        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        inorder (root, tempList);

        return tempList.iterator();
    }

    protected void inorder (BinaryTreeNode<T> node,
                            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder (node.left, tempList);
            tempList.addToRear(node.element);
            inorder (node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {

        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        preorder (root, tempList);

        return tempList.iterator();
    }

    protected void preorder (BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList){

        if(node != null){
            tempList.addToRear(node.element);
            preorder(node.left, tempList);
            preorder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {

        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        postorder (root, tempList);

        return tempList.iterator();
    }

    protected void postorder (BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList){

        if(node != null){
            postorder(node.left, tempList);
            postorder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        LinkedQueue<BinaryTreeNode> nodes = new LinkedQueue<>();
        ArrayUnorderedList<T> result = new ArrayUnorderedList<>();
        BinaryTreeNode<T> node;

        nodes.enqueue(this.root);

        while (!(nodes.isEmpty())){
            try{
                node = nodes.dequeue();

                if(node.element != null){

                    result.addToRear(node.element);

                    if(node.left != null) {

                        nodes.enqueue(node.left);
                    }
                    if(node.right != null) {

                        nodes.enqueue(node.right);
                    }
                } else {

                    result.addToRear(null);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

        return result.iterator();
    }


    public String toString(Iterator itr) {

        String str = "";

        while (itr.hasNext()){
            str += " " + itr.next();
        }

        return str;
    }
}
