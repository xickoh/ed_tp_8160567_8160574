package Ficha2;

public class DoubleNode<T> {

    private DoubleNode<T> previus, next;
    private T element;

    public DoubleNode(T element) {
        this.previus = null;
        this.next = null;
        this.element = element;
    }

    public DoubleNode<T> getPrevius() {
        return previus;
    }

    public void setPrevius(DoubleNode<T> previus) {
        this.previus = previus;
    }

    public DoubleNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "{" +
                "element = " + element.toString() +
                '}';
    }
}
