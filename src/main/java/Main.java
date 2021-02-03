import Exceptions.EmptyCollectionException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Agent p = new Agent("Kim");
        try {
            Simulation s = new Simulation("mission.json", p);

            //Iterator i = s.getAutomaticSimulation();

            //s.printMatrix();

            s.getManualSimulation();


            //while(i.hasNext()){
              //  System.out.println(i.next());
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}