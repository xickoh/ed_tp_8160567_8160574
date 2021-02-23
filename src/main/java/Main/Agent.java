package Main;

import Structs.LinkedList;

public class Agent {

    private String name;
    private double health;
    private Room currentLocation;
    private LinkedList<PowerUp> powerUps;

    /**
     * Class that defines the agent that will perform the mission
     *
     * @param name name of the agent
     */

    public Agent(String name) {
        this.name = name;
        this.health = 100;
        this.currentLocation = null;
        this.powerUps = new LinkedList<>();
    }

    /**
     * Get the name of the agent
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the room where the agent is located
     * @return zone
     */
    public Room getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Updates the room where the player is at
     * @param currentLocation room where the player has moved into
     */
    public void setCurrentLocation(Room currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(LinkedList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }
}
