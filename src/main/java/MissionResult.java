import Structs.ArrayUnorderedList;

import java.util.Iterator;

public class MissionResult implements Comparable{

    private double health;
    private ArrayUnorderedList<String> path;
    private int version;
    private String codMission;
    private String date;

    /**
     *
     * @param health represents the agent health
     * @param path represents the path followed by the agent
     * @param version represents the verion of mission
     * @param codMission represents the codMission
     */
    public MissionResult(double health, ArrayUnorderedList<String> path, int version, String codMission, String date) {
        this.health = health;
        this.path = path;
        this.version = version;
        this.codMission = codMission;
        this.date = date;
    }

    /**
     * Returns the agent health
     *
     * @return health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Returns the path followed by the agent
     *
     * @return path
     */
    public ArrayUnorderedList<String> getPath() {
        return path;
    }

    /**
     * Returns the version code
     *
     * @return version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Returns the mission code
     *
     * @return codMission
     */
    public String getCodMission() {
        return codMission;
    }

    /**
     * Returns the date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Retuns a int value
     *
     * @param o Object recived
     * @return -1 if is less
     * @return 1 if is more
     * @return 0 if is the same
     */
    @Override
    public int compareTo(Object o) {

        if(((MissionResult) o).health > this.health )
            return 1;

        if(((MissionResult) o).health < this.health )
            return -1;

        return 0;
    }

    @Override
    public String toString() {
        String str = "\n";
        Iterator itr = this.path.iterator();

        str +=  "\nMission code: "+ this.codMission
                +"\nVersion: "+ this.version
                +"\nDate: "+ this.date
                +"\nHealth: "+ this.health
                +"\nPath: ";

        while(itr.hasNext()){
            str += "\n"+ itr.next();

            if(itr.hasNext())
                str +=  "\n     |     "+"\n     V     ";
        }

        return str;
    }
}
