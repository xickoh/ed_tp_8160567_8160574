import Exceptions.EmptyCollectionException;
import Structs.ArrayUnorderedList;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class Simulation<T> {

    private Mission mission;
    private Agent agent;

    public Simulation(String pathJson, Agent agent) throws IOException, ParseException {

        this.mission = IO.importMission(pathJson);
        this.agent = agent;

    }

    public void getManualSimulation(){
        
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
}
