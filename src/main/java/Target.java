public class Target {

    private String zone;
    private String type;

    /**
     * Target constructor
     *
     * @param type represents the essence of target
     * @param zone represents the zone of the building were the target is placed
     */
    public Target(String type, String zone) {
        this.zone = zone;
        this.type = type;
    }

    /**
     * Return the essence
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Return the zone of the building were the target is placed
     *
     * @return zone
     */
    public String getZone(){
        return zone;
    }
}
