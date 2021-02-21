package Main;

import Structs.ArrayUnorderedList;
import Structs.NetworkGraph;

public class Mission<T> {

    private NetworkGraph<T> graph;
    private ArrayUnorderedList<T> entry;
    private ArrayUnorderedList<T> exit;
    private Target target;
    private ArrayUnorderedList<Enemy> enemies;
    private String missionCode;
    private int version;

    /**
     * Class constructor of Map
     *
     * @param graph represents the structure of the building
     * @param entries represents the entries points in the building
     * @param exits represents the exits points in the building
     * @param target represents the objective of the mission
     * @param enemies represents a list of the enemies in the building
     * @param missionCode represents the code of the mission
     * @param version represents the version
     */
    public Mission(NetworkGraph<T> graph,
                   ArrayUnorderedList<T> entries,
                   ArrayUnorderedList<T> exits,
                   Target target,
                   ArrayUnorderedList<Enemy> enemies,
                   String missionCode,
                   int version) {

        this.enemies = new ArrayUnorderedList<>();
        this.graph = graph;
        this.entry = entries;
        this.exit = exits;
        this.target = target;
        this.enemies = enemies;
        this.missionCode = missionCode;
        this.version = version;

    }

    /**
     * Returns the graph
     *
     * @return graph
     */
    public NetworkGraph<T> getGraph() {
        return this.graph;
    }

    /**
     * Returns an ArrayUnorderedList containing the entry
     * point from the building
     *
     * @return entry
     */
    public ArrayUnorderedList<T> getEntry() {
        return this.entry;
    }

    /**
     * Returns an ArrayUnorderedList containing the exit
     * point from the building
     *
     * @return exit
     */
    public ArrayUnorderedList<T> getExit() {
        return this.exit;
    }

    /**
     * Return an object Target
     *
     * @return target
     */
    public Target getTarget() {
        return this.target;
    }

    /**
     * Returns an ArrayUnorderedList containing the enemies
     *
     * @return enemies
     */
    public ArrayUnorderedList<Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * Returns the mission code
     * @return missionCode
     */
    public String getMissionCode() {
        return missionCode;
    }

    /**
     * Returns the mission's version
     * @return version
     */
    public int getVersion() {
        return version;
    }
}
