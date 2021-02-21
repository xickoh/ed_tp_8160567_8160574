package Main;

import Exceptions.NotComparableException;


import Exceptions.EmptyCollectionException;
import Structs.ArrayUnorderedList;
import Structs.LinkedQueue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

import java.util.Scanner;


public class Simulation<T> {

    private Mission mission;
    private Agent agent;

    /**
     * Instantiates a new simulation with an agent and the mission map
     * @param pathJson path to the json file
     * @param agent the agent that will perform theh simulations
     * @throws IOException
     * @throws ParseException
     */
    public Simulation(String pathJson, Agent agent) throws IOException, ParseException {

        this.mission = IO.importMission(pathJson);
        this.agent = agent;

    }

    /**
     * Checks if the mission is valid
     * @return true if valid, or false if invalid
     */
    public boolean missionValid(){
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
        Iterator<String> neighbors, neighbors2;
        Iterator<String> entry = mission.getEntry().iterator();
        Iterator<String> exit = mission.getExit().iterator();
        String currentNeighbor;
        this.agent.setHealth(100);
        int number = 1;
        boolean hasTarget = false;

        System.out.println("New manual simulation\n");
        System.out.println("Entries:");

        while (entry.hasNext()){
            System.out.println(number++ + "- " + entry.next());
        }

        System.out.println("\nInsert the initial position: ");
        String position = sc.nextLine();

        while (Integer.parseInt(position) <1 || Integer.parseInt(position) > number-1){
            System.out.println("\u001B[32mOption " + position + " is not an option, I can't teleport there\u001B[0m");
            position = sc.nextLine();
        }

        //Gets the respective zone for the chosen option
        number = 1;
        while (exit.hasNext()){
            if (Integer.parseInt(position) == number){
                position = exit.next();
                break;
            }
            exit.next();
            number++;
        }

        do {
            //Clears the screen
            Main.clearScreen();

            this.agent.setZone(position);
            path.enqueue(position);
            neighbors = mission.getGraph().getNeighbor(position);
            neighbors2 = mission.getGraph().getNeighbor(position);

            if(mission.getTarget().getZone().equals(this.agent.getZone())){
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
                System.out.println(number++ + "- " + currentNeighbor);
            }
            System.out.println("\nAgent " + this.agent.getName() + " Health: "+ this.agent.getHealth());

            if (hasTarget){
                System.out.print("\u001B[33mYou carry the target with you\u001B[0m");
            }
            System.out.println("\nInsert the next room: ");
            position = sc.nextLine();

            while (Integer.parseInt(position) <1 || Integer.parseInt(position) > number-1){
                System.out.println("\u001B[32mOption " + position + " is not an option, I can't teleport there\u001B[0m");
                position = sc.nextLine();
            }

            //Gets the respective zone for the chosen option
            number = 1;
            while (neighbors2.hasNext()){
                if (Integer.parseInt(position) == number){
                    position = neighbors2.next();
                    break;
                }
                neighbors2.next();
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
        Iterator<Enemy> enemies = mission.getEnemies().iterator();
        Enemy currentEnemy;

        while (enemies.hasNext()) {

            currentEnemy = enemies.next();

            if (currentEnemy.getZone().equals(this.agent.getZone())) {

                this.agent.setHealth(this.agent.getHealth() - currentEnemy.getPower());
                System.out.println("\n\u001B[31mAgent suffered damage from "+ currentEnemy.getName() +": -"+ currentEnemy.getPower()+" Health\u001B[0m");

                if(this.agent.getHealth() < 0){
                    this.agent.setHealth(0);
                    System.out.println("\u001B[31mAgent " + this.agent.getName()+ " down, I repeat, agent "
                            + this.agent.getName() + " down!\u001B[0m");
                    return true;
                }
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
            if(exit.next().equals(this.agent.getZone())){
                return true;
            } else {
                exit.next();
            }
        }
        return false;
    }

    /**
     * Simulates the path with the lowest weight
     * @return an iterator with the path
     * @throws EmptyCollectionException
     */
    public Iterator getAutomaticSimulation() throws EmptyCollectionException {
        Iterator entries = mission.getEntry().iterator();
        Iterator bestPath = null;
        double bestPathWeight = Double.MAX_VALUE;

        while (entries.hasNext()){
            Iterator path = mission.getGraph().shortestCostPath(entries.next(),mission.getTarget().getZone());
            Iterator copyIterator = mission.getGraph().shortestCostPath(entries.next(),mission.getTarget().getZone());
            double pathWeight = mission.getGraph().getPathWeight(path);
            if (pathWeight < bestPathWeight){
                bestPath = copyIterator;
                bestPathWeight = pathWeight;
            }
        }

        //Adds the returning path
        ArrayUnorderedList<String> resultPath = new ArrayUnorderedList<>();
        ArrayUnorderedList<String> reversePath = new ArrayUnorderedList<>();
        while (bestPath.hasNext()){
            String nextZone = (String) bestPath.next();
            resultPath.addToRear(nextZone);
            reversePath.addToFront(nextZone);
        }
        Iterator returningPath = reversePath.iterator();
        returningPath.next();
        while (returningPath.hasNext()){
            resultPath.addToRear((String) returningPath.next());
        }

        return resultPath.iterator();
    }

    /**
     * Gets the locations and their connections
     * @return a string with the vertices and their edges
     */
    public String getMap(){

        Iterator<String> neighbors;
        Iterator<String> entry = this.mission.getEntry().iterator();
        Iterator<String> graph = this.mission.getGraph().iteratorBFS(entry.next());

        String str = "";

        while(graph.hasNext()){

            String currentVertex = graph.next();

            neighbors = this.mission.getGraph().getNeighbor(currentVertex);

            str += "\n\nThe room "+ currentVertex +" has this conections: ";

            while (neighbors.hasNext()){

                str += " "+neighbors.next()+"";
            }
        }

        return str;
    }
}