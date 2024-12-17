public class Player extends Person {

    // Attributes

    /**
     * number of goals and number of assists of the player
     */
    private int goals;
    private int assists;

    // Constructor
    public Player(String name) {
        super(name); // Appelle le constructeur de Person pour initialiser 'name'
        this.goals = 0;
        this.assists = 0;
    }

    // Getters

    /**
     * Getter of the player name
     * @return player name
     */
    @Override
    public String getName() {
        return super.getName(); // Utilise le getter de la classe parente
    }

    /**
     * Getter of the number of goals scored
     * @return number of goals scored by the player
     */
    public int getGoals() {
        return goals;
    }

    /**
     * Getter of the number of assists
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
     * @return a display
     */
    public String getStats() {
        return super.getName() + " - Goals: " + goals + ", Assists: " + assists ;
    }

    @Override
    public String toString() {
        return super.getName(); // Utilise le getter de la classe parente
    }

}