package Main;

import Structs.LinkedList;

import java.util.Iterator;
import java.util.Objects;

public class Room {

    private LinkedList<Enemy> enemies;
    private Target target;
    private String room;
    private LinkedList<PowerUp> powerUps;

    public Room(String room) {
        this.room = room;
        this.enemies = new LinkedList<>();
        this.target = null;
        this.powerUps = new LinkedList<>();
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

    public double getEnemiesPower(){
        double power = 0;

        Iterator<Enemy> enemiesItr = this.enemies.iterator();

        while (enemiesItr.hasNext()){
            power+= enemiesItr.next().getPower();
        }

        return power;
    }

    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }
}
