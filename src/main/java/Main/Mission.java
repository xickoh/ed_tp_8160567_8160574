package Main;

import Interfaces.MissionADT;
import Structs.ArrayUnorderedList;
import Structs.LinkedList;
import Structs.Graph;

import java.util.Iterator;

public class Mission implements MissionADT {

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

    @Override
    public Graph<Room> getMap() {
        return map;
    }

    @Override
    public LinkedList<Room> getEntry() {
        return entry;
    }

    @Override
    public LinkedList<Room> getExit() {
        return exit;
    }

    @Override
    public String getMissionCode() {
        return missionCode;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Room getTargetRoom(){
        Room targetRoom = null;

        for (int i = 0; i<this.getMap().size(); i++){
            if (this.getMap().getVertex(i).getTarget()!=null){
                targetRoom = this.getMap().getVertex(i);
            }
        }
        return targetRoom;
    }

    @Override
    public Iterator getEnemyRooms(){

        LinkedList<Room> roomsWithEnemies = new LinkedList<>();

        for (int i = 0; i<map.size(); i++){
            if (map.getVertex(i).getEnemiesPower()>0){
                roomsWithEnemies.add(map.getVertex(i));
            }
        }

        return roomsWithEnemies.iterator();
    }

}
