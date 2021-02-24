package Main;

import Interfaces.EventADT;

public class Event implements EventADT {

    private String description, room;

    public Event(String description, String room) {
        this.description = description;
        this.room = room;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getRoom() {
        return room;
    }
}
