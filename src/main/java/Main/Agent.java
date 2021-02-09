package Main;

public class Agent {

    private String name, zone;
    private double health;

    /**
     * Class that defines the agent that will perform the mission
     *
     * @param name name of the agent
     */

    public Agent(String name) {
        this.name = name;
        this.health = 100;
    }

    /**
     * Get the name of the agent
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the zone where the agent is located
     * @return zone
     */
    public String getZone() {
        return zone;
    }

    /**
     * Updates the zone where the player is at
     * @param zone zone where the player has moved into
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
