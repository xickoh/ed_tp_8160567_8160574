package Main;

import Structs.LinkedList;

public class Room {

    private LinkedList<Enemy> enemies;
    private Target target;
    private String room;

    public Room(String room) {
        this.room = room;
        this.enemies = null;
        this.target = null;
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(LinkedList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
