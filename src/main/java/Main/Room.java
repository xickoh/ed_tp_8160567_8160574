package Main;

import Structs.LinkedList;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room1 = (Room) o;
        return Objects.equals(room, room1.room);
    }

}
