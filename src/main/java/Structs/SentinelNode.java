package Structs;

public class SentinelNode<T> {

    private T element;

    public SentinelNode(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "SentinelNode{" +
                "element=" + element +
                '}';
    }
}
