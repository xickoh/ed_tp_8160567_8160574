package Interfaces;

import Main.Room;
import Structs.Graph;
import Structs.LinkedList;

import java.util.Iterator;

public interface MissionADT {

    /**
     * Returns the graph of rooms
     * @return map as graph
     */
    public Graph<Room> getMap();

    /**
     * Returns entry rooms
     * @return list with entries
     */
    public LinkedList<Room> getEntry();

    /**
     * Returns exit rooms
     * @return list with exits
     */
    public LinkedList<Room> getExit();

    /**
     * @return mission code
     */
    public String getMissionCode();

    /**
     *
     * @return mission version
     */
    public int getVersion();

    /**
     * Finds and returns the room with the target
     * @return target room
     */
    public Room getTargetRoom();

    /**
     * Gets rooms with enemies
     * @return
     */
    public Iterator getEnemyRooms();

}
