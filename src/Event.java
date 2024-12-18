import java.util.ArrayList;

public abstract class Event {
    public enum Type {YELLOW_CARD,RED_CARD,GOAL,SUBSTITUTION};
    private Type eventType;
    private int minute;
    private ArrayList<Player> players;

    public Event() {
        this.eventType = eventType;
        this.minute = minute;
        this.players = players;
    }

    public Event(Type eventType, int minute, ArrayList<Player> players) {
        if (minute < 0 || minute > 90) {
            throw new IllegalArgumentException("Minute must be between 0 and 120");
        }
        this.eventType = eventType;
        this.minute = minute;
        this.players = players;
    }

    /**
     * Gets the list of players involved in the event
     * @return an ArrayList of the players involved
     */
    public ArrayList<Player> getPlayersE() {
        return players;
    }

    /**
     * Gets the minute of the event
     * @return the minute of the event
     */
    public int getMinute() {
        return minute ;
    }

/**
 * Provides a string representation of the event based on its type.
 *
 * @return A string describing the event type in French. Possible values:
 *         "Carton jaune" for a yellow card, "Carton rouge" for a red card,
 *         "But" for a goal, and "Remplacement" for a substitution.
 *         Exits the program with an error message if the event type is incorrect.
 */

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

    /**
     * Provides a string representation of the event, giving the event type
     * and the minute at which it occurred.
     *
     * @return A string describing the event type and minute, for example
     *         "Event: GOAL at minute 42".
     */
    @Override
    public String toString() {
        return "Event: " + eventType + " at minute " + minute;
    }
}
