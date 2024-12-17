import java.util.ArrayList;

public class Event {
    private enum Type {YELLOW_CARD,RED_CARD,GOAL,SUBSTITUTION};

    private Type eventType;
    private int minute;
    private ArrayList<Player> players;

    public Event(Type eventType, int minute, ArrayList<Player> players) {
        this.eventType = eventType;
        this.minute = minute;
        this.players = players;
    }

    public String getEvent() {
        String newEvent = "";
        switch (eventType){
            case Type.YELLOW_CARD:
                newEvent = "Carton jaune";
                break;
            case Type.RED_CARD:
                newEvent = "Carton rouge";
                break;
            case Type.GOAL:
                newEvent = "But";
                break;
            case Type.SUBSTITUTION:
                newEvent = "Remplacement";
                break;
            default:
                System.err.println("Event.getEvent(): incorrect event");
                System.exit(-1);
        };
        return newEvent;
    }
}