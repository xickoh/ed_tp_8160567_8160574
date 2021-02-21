package Main;

public class Enemy {

    private String name;
    private Double power;

    /**
     * Contructor of Enemy
     *
     * @param name represents the name of the enemy
     * @param power represents the power of the enemy
     */
    public Enemy(String name, Double power) {
        this.name = name;
        this.power = power;
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

}
