package Structs;

import Exceptions.EmptyCollectionException;
import Main.Room;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {


    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    /**
     * Creates an empty graph.
     */
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void addEdge (T vertex1, T vertex2) {
        try {
            addEdge(getIndex(vertex1), getIndex(vertex2));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void addEdge (int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2))
        {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph
     * if necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    public void addVertex (T vertex)
    {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        for (int i = 0; i <= numVertices; i++)
        {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        vertices[numVertices++] = vertex;
    }

    @Override
    public void removeVertex(T vertex) {

        try {

            if (getIndex(vertex) == -1) {
                return;
            }
            int index = getIndex(vertex);

            for (int i = 0; i < numVertices; i++) {
                removeEdge(vertices[i], vertices[index]);
                removeEdge(vertices[index], vertices[i]);
            }

            for (int i = index; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
            }

            numVertices--;

        }catch (Exception ex){

            ex.printStackTrace();
        }

    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {

        try {
            adjMatrix[getIndex(vertex1)][getIndex(vertex2)] = false;
            adjMatrix[getIndex(vertex2)][getIndex(vertex1)] = false;

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {

        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        try {

            int startIndex = getIndex(startVertex);

            if (!indexIsValid(startIndex)) {

                return resultList.iterator();

            }

            boolean[] visited = new boolean[numVertices];

            for (int i = 0; i < numVertices; i++) {

                visited[i] = false;

            }

            traversalQueue.enqueue(startIndex);
            visited[startIndex] = true;

            while (!traversalQueue.isEmpty()) {
                x = traversalQueue.dequeue();
                resultList.addToRear(vertices[x.intValue()]);
                /**
                 * Find all vertices adjacent to x that have not been visited and
                 * queue them up
                 */
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[x.intValue()][i] && !visited[i]) {
                        traversalQueue.enqueue(new Integer(i));
                        visited[i] = true;
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {

        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        try {

            int startIndex = getIndex(startVertex);
            Integer x;
            boolean found;
            LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();

            boolean[] visited = new boolean[numVertices];
            if (!indexIsValid(startIndex)) {
                return resultList.iterator();
            }
            for (int i = 0; i < numVertices; i++) {
                visited[i] = false;
            }

            traversalStack.push(startIndex);
            resultList.addToRear(vertices[startIndex]);
            visited[startIndex] = true;
            while (!traversalStack.isEmpty()) {
                x = traversalStack.peek();
                found = false;
                /**
                 * Find a vertex adjacent to x that has not been visited and push it
                 * on the stack
                 */
                for (int i = 0; (i < numVertices) && !found; i++) {
                    if (adjMatrix[x.intValue()][i] && !visited[i]) {
                        traversalStack.push(i);
                        resultList.addToRear(vertices[i]);
                        visited[i] = true;
                        found = true;
                    }
                }
                if (!found && !traversalStack.isEmpty()) {
                    traversalStack.pop();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return resultList.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (indexIsValid(startIndex) == false || indexIsValid(targetIndex) == false) {
            return resultList.iterator();
        }

        int x = startIndex;
        int[] size = new int[this.numVertices];
        int[] previous = new int[this.numVertices];
        boolean[] visited = new boolean[this.numVertices];

        for (int i = 0; i < this.numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        size[startIndex] = 0;
        previous[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (x != targetIndex)) {
            try {
                x = traversalQueue.dequeue();
            } catch (EmptyCollectionException ex) {
            }
            for (int i = 0; i < this.numVertices; i++) {
                if (this.adjMatrix[x][i] && !visited[i]) {
                    size[i] = size[x] + 1;
                    previous[i] = x;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        if (x != targetIndex) {
            return resultList.iterator();
        }

        do{
            resultList.addToFront(vertices[x]);
            x = previous[x];
        }while(x >= startIndex);

        return resultList.iterator();
    }

    @Override
    public boolean isEmpty() {
        return numVertices == 0 ? true : false;
    }

    @Override
    public boolean isConnected() throws EmptyCollectionException {

        Iterator itr = iteratorDFS((T) vertices[0]);
        int count = 0;

        while (itr.hasNext()) {
            count++;
            itr.next();
        }

        return count == numVertices;

    }

    @Override
    public int size() {
        return numVertices;
    }

    public void expandCapacity(){

        T[] copArray = (T[])new Object[vertices.length * 2];
        boolean[][] copAdjMatrix = new boolean[adjMatrix.length * 2][adjMatrix.length * 2];


        for(int i = 0; i < vertices.length; i++)
        {
            copArray[i] = vertices[i];
        }

        for (int i = 0; i<vertices.length; i++){
            for (int j = 0; j<vertices.length; j++){
                copAdjMatrix[i][j] = adjMatrix[i][j];
            }
        }

        vertices = copArray;
        adjMatrix = copAdjMatrix;

    }

    public boolean indexIsValid(int index) {
        return (index == -1 || vertices[index] == null) ? false : true;
    }

    public int getIndex(T vertex){

        for (int i = 0; i < vertices.length; i++){
            if(vertex.equals(vertices[i])){
                return i;
            }
        }
        return -1;
    }


    public T getVertex(T vertex){

        return this.vertices[getIndex(vertex)];
    }

    public T getVertex(int indexVertex){

        return this.vertices[indexVertex];
    }

    public String toString(Iterator itr) {

        String str = "";

        do{
            str += " " + itr.next();
        }while(itr.hasNext());

        return str;
    }

    /** @Override
   public String toString() {

        String s = "";
        String result = "";

        for (int i = 0; i < this.size(); i++) {
            s += vertices[i].toString() + "\n";
        }

        result += "Adjacency Matrix\n";
        result += "----------------\n";

        result += "    ";
        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < 30)
                result += "     ";
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + " \t";

            for (int j = 0; j < numVertices; j++) {
                result += this.adjMatrix[i][j] + " ";
            }
            result += "\n";
        }


        return result;
    }**/

    public T[] getVertices(){
        return this.vertices;
    }

    public boolean[][] getAdjMatrix(){
        return adjMatrix;
    }
}
