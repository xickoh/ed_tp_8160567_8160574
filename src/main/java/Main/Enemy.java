package Main;

import Interfaces.EnemyADT;

public class Enemy implements EnemyADT {

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPower() {
        return power;
    }

}
