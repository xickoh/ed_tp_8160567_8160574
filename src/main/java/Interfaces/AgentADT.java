package Interfaces;

import Main.PowerUp;
import Main.Room;
import Structs.LinkedList;

public interface AgentADT {

    public String getName();

    /**
     * Get the room where the agent is located
     *
     * @return zone
     */
    public Room getCurrentLocation();

    /**
     * Updates the room where the player is at
     *
     * @param currentLocation room where the player has moved into
     */
    public void setCurrentLocation(Room currentLocation);

    public double getHealth();

    public void setHealth(double health);

    public LinkedList<PowerUp> getPowerUps();

    public void setPowerUps(LinkedList<PowerUp> powerUps);

}
