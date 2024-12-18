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

    /**
     * Gets the team of this player.
     *
     * @return the team of this player
     */
    public Team getTeam() {
        return team ;
    }
    // Getters
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the player.
     *
     * @param position the position to be assigned to the player
     */

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Adds the specified number of minutes to the player's total minutes played.
     *
     * @param minutes the number of minutes to add
     */

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

    /**
     * Compare two players based on their goals scored. The player with the most goals comes first.
     *
     * @param other the other player to compare with
     * @return a negative integer, zero, or a positive integer as this player has fewer goals than,
     *         the same number of goals as, or more goals than the other player
     */
    public int compareTo(Player other) {
        return Integer.compare(other.goals, this.goals); // Tri par nombre de buts (ordre décroissant)
    }
    /**
     * Get a string representation of the player, including their name and position.
     *
     * @return a string of the form "Name (Position)"
     */
    @Override
    public String toString() {
        return super.getName() + " (" + position + ")";
    }
}
