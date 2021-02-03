import Exceptions.EmptyCollectionException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Agent p = new Agent("Kim");
        try {
            Simulation s = new Simulation("mission.json", p);

            Iterator i = s.getAutomaticSimulation();
            while(i.hasNext()){
                System.out.println(i.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (EmptyCollectionException e) {
            e.printStackTrace();
        }
    }
}