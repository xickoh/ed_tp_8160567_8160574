package Main;

public class PowerUp {

    enum Type{
        MedKit,
        Shield
    }

    boolean used;
    Type type;

    public PowerUp(Type type) {
        this.used = false;
        this.type = type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed() {
        this.used = true;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
