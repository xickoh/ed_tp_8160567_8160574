package Main;

import Interfaces.RoomADT;
import Structs.LinkedList;

import java.util.Iterator;
import java.util.Objects;

public class Room implements RoomADT {

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

    @Override
    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void setEnemies(LinkedList<Enemy> enemies) {
        this.enemies = enemies;
    }

    @Override
    public Target getTarget() {
        return target;
    }

    @Override
    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
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

    @Override
    public double getEnemiesPower(){
        double power = 0;

        Iterator<Enemy> enemiesItr = this.enemies.iterator();

        while (enemiesItr.hasNext()){
            power+= enemiesItr.next().getPower();
        }

        return power;
    }

    @Override
    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    @Override
    public void setPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }
}
