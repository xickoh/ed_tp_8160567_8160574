import Structs.ArrayUnorderedList;
import Structs.NetworkGraph;

public class Mission<T> {

    private NetworkGraph<T> graph;
    private ArrayUnorderedList<T> entryExit;
    private Target target;
    private ArrayUnorderedList<Enemy> enemies;
    private String missionCode;
    private int version;

    /**
     * Class constructor of Map
     *
     * @param graph represents the structure of the building
     * @param entryExit represents the entry and exit points in the building
     * @param target represents the objective of the mission
     * @param enemies represents a list of the enemies in the building
     * @param missionCode represents the code of the mission
     * @param version represents the version
     */
    public Mission(NetworkGraph<T> graph,
               ArrayUnorderedList<T> entryExit,
               Target target,
               ArrayUnorderedList<Enemy> enemies,
               String missionCode,
               int version) {

        this.enemies = new ArrayUnorderedList<>();

        this.graph = graph;
        this.entryExit = entryExit;
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
     * Returns an ArrayUnorderedList containing the entry and exit
     * point from the building
     *
     * @return entryExit
     */
    public ArrayUnorderedList<T> getEntryExit() {
        return this.entryExit;
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
}
