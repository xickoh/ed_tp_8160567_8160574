package Main;

import Interfaces.TargetADT;

public class Target implements TargetADT {

    private String type;

    /**
     * Target constructor
     *
     * @param type represents the essence of target
     */
    public Target(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

}
