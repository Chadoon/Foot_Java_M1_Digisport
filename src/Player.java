import java.io.Serializable;

public class Player extends Person implements Serializable {

    // Attributes

    /**
     * number of goals and number of assists of the player
     */
    private int goals;
    private int assists;
    private Position position;
    private Team team;
    private int minutesPlayed;

    private static final long serialVersionUID = 1L;

    public Player() {
        super(""); // Appelle un constructeur par défaut de la classe Person (assurez-vous qu'il existe)
        this.goals = 0;
        this.assists = 0;
        this.position = null; // Vous pouvez définir une position par défaut si nécessaire
        this.team = null;
        this.minutesPlayed = 0;
    }
    // Constructor
    public Player(String name, Position position)  {
        super(name); // Appelle le constructeur de Person pour initialiser 'name'
        this.goals = 0;
        this.assists = 0;
        this.position = position;
        this.team = team;
    }

    public Team getTeam() {
        return team ;
    }
    // Getters
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addMinutesPlayed(int minutes) {
        this.minutesPlayed += minutes;
    }


    /**
     * Getter of the number of goals scored
     *
     * @return number of goals scored by the player
     */
    public int getGoals() {
        return goals;
    }

    /**
     * Getter of the number of assists
     *
     * @return number of assists delivered by the player
     */
    public int getAssists() {
        return assists;
    }

    /**
     * Increment the number of goal scored
     */
    public void addGoal() {

        goals++;
    }

    /**
     * Increment the number of assist
     */
    public void addAssist() {
        assists++;
    }

    /**
     * Display the stats of a player : their goals scored and assists
     *
     * @return a display
     */
    public String getStats() {
        if (minutesPlayed != 0) {
            return super.getName() + " - Goals: " + goals + ", Assists: " + assists + ", Minutes Played : " + minutesPlayed;
        } else {
            return super.getName() + " - Goals: " + goals + ", Assists: " + assists;
        }
    }

    public int compareTo(Player other) {
        return Integer.compare(other.goals, this.goals); // Tri par nombre de buts (ordre décroissant)
    }
    @Override
    public String toString() {
        return super.getName() + " (" + position + ")";
    }
}
