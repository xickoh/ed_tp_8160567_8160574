package Main;

import Structs.ArrayUnorderedList;
import Structs.LinkedList;
import Structs.Graph;

public class Mission {

    private Graph<Room> map;
    private LinkedList<Room> entry;
    private LinkedList<Room> exit;
    private String missionCode;
    private int version;

    /**
     * Class constructor of Map
     *
     * @param map represents the structure of the building
     * @param entries represents the entries points in the building
     * @param exits represents the exits points in the building
     * @param missionCode represents the code of the mission
     * @param version represents the version
     */
    public Mission(Graph<Room> map,
                   LinkedList<Room> entries,
                   LinkedList<Room> exits,
                   String missionCode,
                   int version) {

        this.map = map;
        this.entry = entries;
        this.exit = exits;
        this.missionCode = missionCode;
        this.version = version;

    }

    public Graph<Room> getMap() {
        return map;
    }

    public LinkedList<Room> getEntry() {
        return entry;
    }

    public LinkedList<Room> getExit() {
        return exit;
    }

    public String getMissionCode() {
        return missionCode;
    }

    public int getVersion() {
        return version;
    }
}
