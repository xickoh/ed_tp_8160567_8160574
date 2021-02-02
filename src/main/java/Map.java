import Structs.ArrayUnorderedList;
import Structs.NetworkGraph;

public class Map<T> {

    private NetworkGraph<T> graph;
    private ArrayUnorderedList<T> entryExit;
    private Target target;
    private ArrayUnorderedList<Enemy> enemies;
    private String missionCode;
    private int version;

    /**
     * Class constructor of Map
     *
     * @param graph
     * @param entryExit
     * @param target
     * @param enemies
     * @param missionCode
     * @param version
     */
    public Map(NetworkGraph<T> graph,
               ArrayUnorderedList<T> entryExit,
               Target target,
               ArrayUnorderedList<Enemy> enemies,
               String missionCode,
               int version) {

        this.graph = graph;
        this.entryExit = entryExit;
        this.target = target;
        this.enemies = enemies;
        this.missionCode = missionCode;
        this.version = version;

        this.
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
     * Returns a ArrayUnor
     *
     * @return entryExit
     */
    public ArrayUnorderedList<T> getEntryExit() {
        return this.entryExit;
    }

    public Target getTarget() {
        return this.target;
    }

    public ArrayUnorderedList<Enemy> getEnemies() {
        return this.enemies;
    }
}
