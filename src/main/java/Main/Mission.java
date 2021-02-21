package Main;

import Structs.ArrayUnorderedList;
import Structs.LinkedList;
import Structs.Graph;

public class Mission<T> {

    private Graph<T> map;
    private LinkedList<T> entry;
    private LinkedList<T> exit;
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
    public Mission(Graph<T> map,
                   LinkedList<T> entries,
                   LinkedList<T> exits,
                   String missionCode,
                   int version) {

        this.map = map;
        this.entry = entries;
        this.exit = exits;
        this.missionCode = missionCode;
        this.version = version;

    }

    public Graph<T> getMap() {
        return map;
    }

    public LinkedList<T> getEntry() {
        return entry;
    }

    public LinkedList<T> getExit() {
        return exit;
    }

    public String getMissionCode() {
        return missionCode;
    }

    public int getVersion() {
        return version;
    }
}
