package Interfaces;

import Main.Enemy;
import Main.PowerUp;
import Main.Room;
import Main.Target;
import Structs.LinkedList;

import java.util.Iterator;
import java.util.Objects;

public interface RoomADT {

    /**
     * Returns list of enemies
     * @return list of enemies
     */
    public LinkedList<Enemy> getEnemies();

    /**
     * Sets the enemies list
     * @param enemies
     */
    public void setEnemies(LinkedList<Enemy> enemies);

    /**
     * Gets the target object
     * @return target
     */
    public Target getTarget();

    /**
     * Sets the target
     * @param target
     */
    public void setTarget(Target target);

    /**
     * Returns the name of the room
     * @return name of the room
     */
    public String getRoom();

    /**
     * Get the combined power of the enemies in the room
     * @return enemies power
     */
    public double getEnemiesPower();

    /**
     * Returns a list of power ups in the room
     * @return list of power ups
     */
    public LinkedList<PowerUp> getPowerUps();

    /**
     * Adds a power up to the room
     * @param powerUp
     */
    public void setPowerUp(PowerUp powerUp);

}
