public class Target<T> {

    private String zone;
    private String type;

    public Target(String type, String zone) {
        this.zone = zone;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getZone(){
        return zone;
    }
}
