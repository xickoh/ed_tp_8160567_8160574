public class Enemy {

    private String name;
    private Double power;
    private int zone;

    public Enemy(String name, Double power, int zone) {
        this.name = name;
        this.power = power;
        this.zone = zone;
    }

    public String getName() {
        return name;
    }

    public Double getPower() {
        return power;
    }

    public int getZone() {
        return zone;
    }
}
