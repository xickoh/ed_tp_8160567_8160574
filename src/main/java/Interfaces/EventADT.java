package Interfaces;

public interface EventADT {

    /**
     * Getter to return event description
     * @return description
     */
    public String getDescription();

    /**
     * Get the room where said event happened
     * @return room name
     */
    public String getRoom();
}
