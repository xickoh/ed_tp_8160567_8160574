package Main;

import Interfaces.PowerUpADT;

public class PowerUp implements  PowerUpADT {

    boolean used;
    Type type;

    public PowerUp(Type type) {
        this.used = false;
        this.type = type;
    }

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public void setUsed() {
        this.used = true;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
