package Structs;

import Exceptions.EmptyCollectionException;



import java.util.Iterator;

public class NetworkGraph<T> extends Graph<T> implements NetworkADT<T> {

    double weightMatrix[][];

    public NetworkGraph() {
        super();
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        super.addEdge(vertex1, vertex2);
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        this.weightMatrix[index1][index2] = weight;
        this.weightMatrix[index2][index1] = weight;
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        int startIndex = getIndex(vertex1);
        int targetIndex = getIndex(vertex2);
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        double weight = 0;

        if (indexIsValid(startIndex) == false || indexIsValid(targetIndex) == false) {
            return weight;
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
            return weight;
        }

        int curent;

        do{
            resultList.addToFront(x);
            curent = x;
            x = previous[x];
            weight += weightMatrix[curent][x];

        }while(previous[x] != -1);


        return weight;
    }

    public Iterator shortestCostPath(T vertex1, T vertex2) throws EmptyCollectionException{

        ArrayUnorderedList<T> resultPath = new ArrayUnorderedList<>();
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();

        int startIndex = super.getIndex(vertex1);
        int endIndex = super.getIndex(vertex2);

        if(!indexIsValid(startIndex) || !indexIsValid(endIndex))
            return resultPath.iterator();

        boolean visited[] = new boolean[size()];
        double shortestDistance[] = new double[size()];
        int previusVertex[] = new int[size()];
        int vertex[] = new int[size()];

        for(int i = 0; i < size(); i++){
            visited[i] = false;
            shortestDistance[i] = Double.MAX_VALUE;
            previusVertex[i] = -1;
            vertex[i] = i;
        }

        int currentVertex = vertex[startIndex];
        traversalQueue.enqueue(currentVertex);
        shortestDistance[currentVertex] = 0;

        while (!traversalQueue.isEmpty()) {

            currentVertex = traversalQueue.dequeue();

            for (int i = 0; i < this.numVertices; i++) {
                if (this.adjMatrix[currentVertex][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    if(shortestDistance[currentVertex] + weightMatrix[currentVertex][i] < shortestDistance[i]){

                        shortestDistance[i] = shortestDistance[currentVertex] + weightMatrix[currentVertex][i];
                        previusVertex[i] = currentVertex;
                    }
                }
            }

            visited[currentVertex] = true;
        }

        currentVertex = endIndex;

        do {
            resultPath.addToFront(vertices[currentVertex]);
            currentVertex = previusVertex[currentVertex];

        }while (currentVertex != startIndex);

        resultPath.addToFront(vertices[startIndex]);

        return resultPath.iterator();
    }

    public Iterator getNeighbor(T vertex){

        int index = getIndex(vertex);
        ArrayUnorderedList<T> neighborList = new ArrayUnorderedList<>();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.adjMatrix[index][i]) {
                neighborList.addToRear(vertices[i]);
            }
        }

        return neighborList.iterator();
    }

    @Override
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
            if (i < 10)
                result += " ";
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                result += this.weightMatrix[i][j] + " ";
            }
            result += "\n";
        }


        return result;
    }
}
