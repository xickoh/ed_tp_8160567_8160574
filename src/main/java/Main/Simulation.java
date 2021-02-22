package Main;

import Exceptions.NotComparableException;


import Exceptions.EmptyCollectionException;
import Structs.ArrayUnorderedList;
import Structs.Graph;
import Structs.LinkedList;
import Structs.LinkedQueue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;

import java.util.Scanner;


public class Simulation {

    private Mission mission;
    private Agent agent;

    /**
     * Instantiates a new simulation with an agent and the mission map
     *
     * @param pathJson path to the json file
     * @param agent    the agent that will perform theh simulations
     * @throws IOException
     * @throws ParseException
     */
    public Simulation(String pathJson, Agent agent) throws IOException, ParseException {

        this.mission = IO.importMission(pathJson);
        this.agent = agent;

    }

    /**
     * Checks if the mission is valid
     *
     * @return true if valid, or false if invalid
     */
    public boolean missionValid() {
        return this.mission != null;
    }

    /**
     * Performs a manual simulation through the chosen map
     *
     * @throws NotComparableException
     * @throws ParseException
     * @throws IOException
     * @throws EmptyCollectionException
     */
    public void getManualSimulation() throws NotComparableException, ParseException, IOException, EmptyCollectionException {

        Scanner sc = new Scanner(System.in);
        LinkedQueue<String> path = new LinkedQueue<>();
        Iterator<Room> neighbors;
        Iterator<Room> entry = mission.getEntry().iterator();
        Iterator<Room> exit = mission.getExit().iterator();
        Room currentNeighbor;
        this.agent.setHealth(100);
        int number = 1;
        boolean hasTarget = false;

        System.out.println("New manual simulation\n");
        System.out.println("Entries:");

        while (entry.hasNext()){
            System.out.println(number++ + "- " + entry.next().getRoom());
        }

        System.out.println("\nInsert the initial position: ");
        int position = sc.nextInt();

        while (position < 1 || position > number-1){
            System.out.println("\u001B[32mOption " + position + " is not an option, I can't teleport there\u001B[0m");
            position = sc.nextInt();
        }

        //Gets the respective zone for the chosen option
        number = 1;
        entry = mission.getEntry().iterator();
        while (entry.hasNext()){
            if (position == number){
                position = mission.getMap().getIndex(entry.next());
                break;
            }
            exit.next();
            number++;
        }

        do {
            //Clears the screen
            Main.clearScreen();

            this.agent.setCurrentLocation(mission.getMap().getVertex(position));
            path.enqueue(mission.getMap().getVertex(position).getRoom());
            neighbors = mission.getMap().getNeighbor(mission.getMap().getVertex(position));

            if(mission.getTargetRoom().equals(this.agent.getCurrentLocation())){
                hasTarget = true;
                System.out.println("\u001B[33mTarget acquired. Leaving now.\u001B[0m");
            }

            if(isAgentDown() || isReadyToLeave(hasTarget)){
                break;
            }

            System.out.println("\nOptions: ");
            number = 1;
            while (neighbors.hasNext()){
                currentNeighbor = neighbors.next();
                System.out.println(number++ + "- " + currentNeighbor.getRoom());
            }
            System.out.println("\nAgent " + this.agent.getName() + " Health: "+ this.agent.getHealth());

            if (hasTarget){
                System.out.print("\u001B[33mYou carry the target with you\u001B[0m");
            }
            System.out.println("\nInsert the next room: ");
            position = sc.nextInt();

            while (position <1 || position > number-1){
                System.out.println("\u001B[32mOption " + position + " is not an option, I can't teleport there\u001B[0m");
                position = sc.nextInt();
            }

            //Gets the respective zone for the chosen option
            number = 1;
            neighbors = this.mission.getMap().getNeighbor(this.mission.getMap().getVertex(this.agent.getCurrentLocation()));
            while (neighbors.hasNext()){
                if (position == number){
                    position = mission.getMap().getIndex(neighbors.next());
                    break;
                }
                neighbors.next();
                number++;
            }


        } while(this.agent.getHealth() > 0);

        IO.exportMission(path, this.agent.getHealth(),
                this.mission.getMissionCode(), this.mission.getVersion());

        if (this.agent.getHealth()>0){
            System.out.println("\n\u001B[32mMission Accomplished !!!\u001B[32m\n");
            System.out.println("Health: "+this.agent.getHealth());
        }

    }

    /**
     * Decrements health points if the agent has been hit
     * @return false if the agent is still alive, or true otherwise
     */
    public boolean isAgentDown(){
        Room agentCurrentLocation = this.agent.getCurrentLocation();

        if (agentCurrentLocation.getEnemiesPower() > 0){
            this.agent.setHealth(this.agent.getHealth() - agentCurrentLocation.getEnemiesPower());

            Iterator<Enemy> enemies = agentCurrentLocation.getEnemies().iterator();

            while(enemies.hasNext()){
                Enemy e = enemies.next();
                System.out.println("\n\u001B[31mAgent suffered damage from "+ e.getName() +": -"+ e.getPower()+" Health\u001B[0m");
            }

            if(!(this.agent.getHealth() > 0)){
                this.agent.setHealth(0);
                System.out.println("\u001B[31mAgent " + this.agent.getName()+ " down, I repeat, agent "
                        + this.agent.getName() + " down!\u001B[0m");
                return true;
            }

        }

        return false;
    }

    /**
     * Checks if the agent has the target and is on an exit floor
     * @param hasTarget
     * @return true if the agent is ready to leave, false if not in an exit floor/ doesn't have the target
     */
    public boolean isReadyToLeave(boolean hasTarget){
        if (!hasTarget){
            return false;
        }

        Iterator exit = mission.getExit().iterator();
        while(exit.hasNext()) {
            if(exit.next().equals(this.agent.getCurrentLocation())){
                return true;
            }
        }
        return false;
    }

    /**
     * Simulates the path with the lowest weight
     *
     * @return an iterator with the path
     * @throws EmptyCollectionException
     */
    public Iterator getAutomaticSimulation() throws EmptyCollectionException {

        ArrayUnorderedList<Room> bestPath = new ArrayUnorderedList<>();
        ArrayUnorderedList<Room> bestResult = new ArrayUnorderedList<>();
        double bestPathCost = Double.MAX_VALUE;
        int bestPathLength = Integer.MAX_VALUE;

        Room targetRoom = mission.getTargetRoom();

        Iterator<Room> entriesIt = mission.getEntry().iterator();

        while (entriesIt.hasNext()) {
            ArrayUnorderedList<Room> path = getBestPath(mission.getMap().getVertex(entriesIt.next()), targetRoom);
            Iterator<Room> pathItr = path.iterator();
            double pathCost = 0;
            int pathLength = 0;
            while (pathItr.hasNext()) {
                pathCost += pathItr.next().getEnemiesPower();
                pathLength++;
            }
            if (pathCost <= bestPathCost && pathLength < bestPathLength) {
                bestPath = path;
            }
        }

        //Populates bestResult with the best path to target
        Iterator<Room> bestPathItr = bestPath.iterator();
        while (bestPathItr.hasNext()) {
            bestResult.addToRear(bestPathItr.next());
        }

        bestPathCost = Double.MAX_VALUE;
        bestPathLength = Integer.MAX_VALUE;
        Iterator<Room> exitsIt = mission.getExit().iterator();
        while (exitsIt.hasNext()) {
            ArrayUnorderedList<Room> path = getBestPath(targetRoom, mission.getMap().getVertex(exitsIt.next()));
            Iterator<Room> pathItr = path.iterator();
            double pathCost = 0;
            int pathLength = 0;
            while (pathItr.hasNext()) {
                pathCost += pathItr.next().getEnemiesPower();
                pathLength++;
            }
            if (pathCost <= bestPathCost && pathLength < bestPathLength) {
                bestPath = path;
            }
        }

        bestPathItr = bestPath.iterator();
        while (bestPathItr.hasNext()) {
            bestResult.addToRear(bestPathItr.next());
        }

        return bestResult.iterator();

    }

    public ArrayUnorderedList<Room> getBestPath(Room vertex1, Room vertex2) throws EmptyCollectionException {
        ArrayUnorderedList<Room> resultPath = new ArrayUnorderedList<>();
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();

        Graph<Room> map = mission.getMap();

        int startIndex = map.getIndex(vertex1);
        int endIndex = map.getIndex(vertex2);

        if (!map.indexIsValid(startIndex) || !map.indexIsValid(endIndex))
            return resultPath;

        boolean visited[] = new boolean[map.size()];
        double shortestDistance[] = new double[map.size()];
        int previusVertex[] = new int[map.size()];
        int vertex[] = new int[map.size()];

        for (int i = 0; i < map.size(); i++) {
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

            for (int i = 0; i < map.size(); i++) {
                if (map.getAdjMatrix()[currentVertex][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    if (shortestDistance[currentVertex] + map.getVertex(i).getEnemiesPower() < shortestDistance[i]) {
                        shortestDistance[i] = shortestDistance[currentVertex] + map.getVertex(i).getEnemiesPower();
                        previusVertex[i] = currentVertex;
                    }
                }
            }

            visited[currentVertex] = true;
        }
        currentVertex = endIndex;

        do {
            resultPath.addToFront(map.getVertex(currentVertex));
            currentVertex = previusVertex[currentVertex];

        } while (currentVertex != startIndex);

        resultPath.addToFront(map.getVertex(startIndex));

        return resultPath;
    }


    /**
     * Gets the locations and their connections
     * @return a string with the vertices and their edges
     */
//    public String getMap(){
//
//        Iterator<String> neighbors;
//        Iterator<String> entry = this.mission.getEntry().iterator();
//        Iterator<String> graph = this.mission.getGraph().iteratorBFS(entry.next());
//
//        String str = "";
//
//        while(graph.hasNext()){
//
//            String currentVertex = graph.next();
//
//            neighbors = this.mission.getGraph().getNeighbor(currentVertex);
//
//            str += "\n\nThe room "+ currentVertex +" has this conections: ";
//
//            while (neighbors.hasNext()){
//
//                str += " "+neighbors.next()+"";
//            }
//        }
//
//        return str;
//    }
}
