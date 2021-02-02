package Ficha2;

public class Node<T> {

    private T object;
    private Node<T> next, before;
    private SentinelNode<T> sentinelObject;

    public Node(T object) {

        /* Implementação do node normal
        this.sentinelObject = new SentinelNode<>(object);
        */
        this.object = object;

        this.next = null;
    }

    public Node<T> getBefore() {
        return before;
    }

    public void setBefore(Node<T> before) {
        this.before = before;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getObject() {
        return object;
    }

    public Node<T> getNext() {
        return next;
    }

    public SentinelNode<T> getSentinelObject() {
        return sentinelObject;
    }

    @Override
    public String toString() {
        return "Node{" +
                "object=" + object.toString() +
                '}';
    }
}
