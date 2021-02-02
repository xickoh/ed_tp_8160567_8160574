public class Objective<T> {

    private int zone;
    private String type;

    public Objective(int zone, String type) {
        this.zone = zone;
        this.type = type;
    }

    public int getZone() {
        return zone;
    }

    public String getType() {
        return type;
    }
}
