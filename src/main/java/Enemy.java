public class Enemy {

    private String name;
    private Double power;
    private String zone;

    public Enemy(String name, Double power, String zone) {
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

    public String getZone() {
        return zone;
    }
}
