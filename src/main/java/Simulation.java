
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

    public Simulation(String pathJson, Agent agent) throws IOException, ParseException {

        this.mission = IO.importMission(pathJson);
        this.agent = agent;

    }

    public void getManualSimulation() throws NotComparableException, ParseException, IOException, EmptyCollectionException {

        Scanner sc = new Scanner(System.in);
        LinkedQueue<String> path = new LinkedQueue<>();
        Iterator<String> neighbors;
        Iterator<String> entry = mission.getEntryExit().iterator();
        Enemy currentEnemy;
        String currentNeighbor;
        boolean hasTarget = false;

        System.out.println("New simulation manual\n");
        System.out.println("Entries:");

        while (entry.hasNext()){

            System.out.println(entry.next());
        }

        System.out.println("\nInsert the inicial position: ");
        String position = sc.nextLine();

        search:
        do {

            Iterator<Enemy> enemies = mission.getEnemies().iterator();
            this.agent.setZone(position);
            path.enqueue(position);
            neighbors = mission.getGraph().getNeighbor(position);
            Iterator exit = mission.getEntryExit().iterator();

            while (enemies.hasNext()) {

                currentEnemy = enemies.next();

                if (currentEnemy.getZone().equals(this.agent.getZone())) {

                    this.agent.setHealth(this.agent.getHealth() - currentEnemy.getPower());
                    System.out.println("\nAgent suffered damage from "+ currentEnemy.getName() +": -"+ currentEnemy.getPower()+" Health");

                    if(this.agent.getHealth() < 0){
                        this.agent.setHealth(0);
                        System.out.println(this.agent.getName()+ "is dead !!");
                        break search;
                    }
                }
            }

            if(mission.getTarget().getZone().equals(this.agent.getZone())){

                hasTarget = true;
            }

            while(exit.hasNext()) {

                if(hasTarget && exit.next().equals(this.agent.getZone())){

                    break search;
                } else {

                    exit.next();
                }
            }

            System.out.println("\nOptions: ");
            while (neighbors.hasNext()){

                currentNeighbor = neighbors.next();
                System.out.println(currentNeighbor);
            }
            System.out.println("\nAgent Health: "+ this.agent.getHealth());

            System.out.println("\nInsert the next room: ");

            position = sc.nextLine();

        } while(this.agent.getHealth() >= 0);

        IO.exportMission(path, this.agent.getHealth(),
                this.mission.getMissionCode(), this.mission.getVersion());

        System.out.println("\nMission Accomplished !!!\n");
        System.out.println("Health: "+this.agent.getHealth());
    }

    public Iterator getAutomaticSimulation() throws EmptyCollectionException {
        Iterator entries = mission.getEntryExit().iterator();
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

    public void printMatrix(){
        System.out.println(mission.getGraph().toString());
    }
}
