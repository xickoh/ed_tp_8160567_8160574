package Main;

public class Event {

    private String description, room;

    public Event(String description, String room) {
        this.description = description;
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public String getRoom() {
        return room;
    }
}
