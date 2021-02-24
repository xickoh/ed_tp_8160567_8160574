package Main;

import Interfaces.AgentADT;
import Structs.LinkedList;

import java.util.Iterator;

public class Agent implements AgentADT {

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Room getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void setCurrentLocation(Room currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    @Override
    public void setPowerUps(LinkedList<PowerUp> powerUps) {

        this.powerUps = powerUps;
    }

    /**
     * Checks if agent has said power up
     * @param type
     * @return asked powerup, or null if not found
     */
    public PowerUp hasPowerUp(PowerUp.Type type){

        Iterator<PowerUp> ip = this.powerUps.iterator();

        while (ip.hasNext()){
            PowerUp p = ip.next();
            if (p.getType().equals(type)){
                return p;
            }
        }

        return null;
    }
}
