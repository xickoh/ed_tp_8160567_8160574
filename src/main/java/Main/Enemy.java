package Main;

public class Enemy {

    private String name;
    private Double power;
    private String zone;

    /**
     * Contructor of Enemy
     *
     * @param name represents the name of the enemy
     * @param power represents the power of the enemy
     * @param zone represents the zone of the enemy
     */
    public Enemy(String name, Double power, String zone) {
        this.name = name;
        this.power = power;
        this.zone = zone;
    }

    /**
     * Return the name of the enemy
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the power of the enemy
     *
     * @return power
     */
    public Double getPower() {
        return power;
    }

    /**
     * Return the zone of the enemy
     *
     * @return zone
     */
    public String getZone() {
        return zone;
    }
}
