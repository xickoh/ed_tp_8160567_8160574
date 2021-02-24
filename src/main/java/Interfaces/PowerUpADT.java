package Interfaces;

import Main.PowerUp;

public interface PowerUpADT {

    public enum Type{
        MedKit,
        Shield
    }

    /**
     * Returns boolean result whether the power up is used
     * @return true if used, false if not
     */
    public boolean isUsed();

    /**
     * Set the power up as used;
     */
    public void setUsed();

    /**
     * Returns the powerup type
     * @return type
     */
    public Type getType();

    /**
     * Setter for power up type
     * @param type
     */
    public void setType(Type type);

}
