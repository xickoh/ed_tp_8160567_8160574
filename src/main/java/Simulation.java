import Exceptions.NotComparableException;
import Structs.ArrayOrderedList;
import Structs.ArrayUnorderedList;
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

    public void getManualSimulation() throws NotComparableException {

        Scanner sc = new Scanner(System.in);
        ArrayOrderedList<String> path = new ArrayOrderedList<>();
        Iterator<Enemy> enemies = mission.getEnemies().iterator();
        Iterator<String> neighbors;
        Enemy currentEnemy;
        String currentNeighbor;
        boolean hasTarget = false;

        System.out.println("New simulation manual");
        System.out.println("Insert the inicial position: ");
        String position = sc.nextLine();

        do {

            this.agent.setZone(position);

            path.add(position);

            neighbors = mission.getGraph().getNeighbor(position);

            while (enemies.hasNext()){

                currentEnemy = enemies.next();

                 if(currentEnemy.getZone() == this.agent.getZone())

                     this.agent.setHealth(this.agent.getHealth() - currentEnemy.getPower());
            }

            if(mission.getTarget().getZone() == this.agent.getZone()){

                hasTarget = true;
            }

            while (neighbors.hasNext()){

                currentNeighbor = neighbors.next();

                System.out.println("Options");
                System.out.println(currentNeighbor);
            }

            System.out.println("Insert the next room: ");

            position = sc.nextLine();

        } while(this.agent.getHealth() > 0 || hasTarget);
    }
}
