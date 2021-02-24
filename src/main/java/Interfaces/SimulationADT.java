package Interfaces;

import Exceptions.EmptyCollectionException;
import Exceptions.NotComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public interface SimulationADT {

    /**
     * Simulates the path with the lowest weight
     *
     * @return an iterator with the path
     * @throws EmptyCollectionException
     */
    public Iterator getAutomaticSimulation() throws EmptyCollectionException;

    /**
     * Performs a manual simulation through the chosen map
     *
     * @throws NotComparableException
     * @throws ParseException
     * @throws IOException
     * @throws EmptyCollectionException
     */
    public void getManualSimulation() throws NotComparableException, ParseException, IOException, EmptyCollectionException;

    /**
     * Gets the locations and their connections
     * @return a string with the vertices and their edges
     */
    public String getMap();
}
