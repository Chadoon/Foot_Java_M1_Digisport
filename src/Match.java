/**
 * This class was created to manage a match between two teams and to get the match details
 */
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Match {

    // Attributes
    /*
     * date and time of the match
     */
    private LocalDateTime matchDateTime;
    /*
     * teams involved in the match
     */
    private Team homeTeam;
    private Team awayTeam;
    /*
     * score of the match
     */
    private int homeScore;
    private int awayScore;
    /*
     * players involved in the match
     */
    private Map<Player, Integer> playerGoals;
    private Map<Player, Integer> playerAssists;


    //Constructor
    public Match( LocalDateTime dateTime, Team homeTeam, Team awayTeam) {
        this.matchDateTime = dateTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.playerGoals = new HashMap<>();
        this.playerAssists = new HashMap<>();
    }
    
    public Match( LocalDateTime dateTime, Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        this.matchDateTime = dateTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.playerGoals = new HashMap<>();
        this.playerAssists = new HashMap<>();
    }

    // Getters and Setters

    /**
     * Get the date and time of the match
     * @return the date and time of the match
     */
    public LocalDateTime getDateTime() {
        return matchDateTime;
    }
    
    /**
    * Set the date and time of the match.
    * @param date the new date and time for the match
    */
    public void setDateTime(LocalDateTime date) {
        this.matchDateTime = date;
    }

    /**
     * Get the home team of the match
     * @return the home team
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * Set the home team of the match
     * @param homeTeam the new home team
     */
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * Get the away team of the match
     * @return the away team
     */
    public Team getAwayTeam() {
        return awayTeam;
    }

    /**
     * Set the away team of the match
     * @param awayTeam the new away team
     */
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

/**
 * Get the current home team score.
 * @return the score of the home team
 */

    public int getHomeScore() {
        return homeScore;
    }

    /**
     * Set the score of the home team
     * @param homeScore the new score of the home team
     */
    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

/**
 * Get the current away team score.
 * @return the score of the away team
 */

    public int getAwayScore() {
        return awayScore;
    }

    /**
     * Set the score of the away team
     * @param awayScore the new score of the away team
     */
    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    /**
     * Update the home and away scores of the match based on the goals and assists recorded so far.
     * <p>
     * The home score is the sum of all goals by players in the home team, and
     * the away score is the sum of all goals by players in the away team.
     */
    private void updateTeamScore() {
        int homeGoals = playerGoals.entrySet().stream()
                .filter(e -> homeTeam.getPlayers().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue).sum();
        int awayGoals = playerGoals.entrySet().stream()
                .filter(e -> awayTeam.getPlayers().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue).sum();

        this.homeScore = homeGoals;
        this.awayScore = awayGoals;

        // 2. Synchronisation avec les statistiques des joueurs
        for (Map.Entry<Player, Integer> entry : playerGoals.entrySet()) {
            Player player = entry.getKey();
            int matchGoals = entry.getValue();

        }
    }


    /**
     * Add a goal to the match for the given player.
     * <p>
     * If the player is not part of either team, an error message is printed and the goal is not counted.
     * <p>
     * The home team's score is incremented if the player is part of the home team, and the away team's score is incremented if the player is part of the away team.
     * @param player the player who scored the goal
     */

    public void addGoal(Player player) {
        if (!homeTeam.getPlayers().contains(player) && !awayTeam.getPlayers().contains(player)) {
            System.out.println("Error: Player " + player.getName() + " is not part of either team.");
            return;
        }

        playerGoals.put(player, playerGoals.getOrDefault(player, 0) + 1);
        updateTeamScore();

        if (homeTeam.getPlayers().contains(player)) {
            homeScore++;
        } else if (awayTeam.getPlayers().contains(player)) {
            awayScore++;
        }
    }


    /**
     * Add an assist to the match for the given player.
     * <p>
     * If the player is not part of either team, an error message is printed and the assist is not counted.
     * <p>
     * The number of assists of the player is incremented.
     * @param player the player who assisted the goal
     */
    public void addAssist(Player player) {
        if (!homeTeam.getPlayers().contains(player) && !awayTeam.getPlayers().contains(player)) {
            System.out.println("Error: Player " + player.getName() + " is not part of either team.");
            return;
        }

        playerAssists.put(player, playerAssists.getOrDefault(player, 0) + 1);
        player.addAssist();
    }

//    // Finalize match and update team statistics
//    public void finalizeMatch() {
//        homeTeam.recordMatch(homeScore, awayScore);
//        awayTeam.recordMatch(awayScore, homeScore);
//    }


    /**
     * Record the score of a match and update the team statistics.
     * @param homeScore the score of the home team
     * @param awayScore the score of the away team
     */
    public void recordScore(int homeScore, int awayScore) {
        if (!homeTeam.isValidForMatch() || !awayTeam.isValidForMatch()) {
            throw new IllegalStateException("Both teams must have at least 11 players to record a score.");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        updateTeamScore();
        //homeTeam.recordMatch(homeScore, awayScore);
        //awayTeam.recordMatch(awayScore, homeScore);
    }


    @Override
    public String toString() {
        return "Match{" +
                "matchDateTime=" + matchDateTime +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", playerGoals=" + playerGoals +
                ", playerAssists=" + playerAssists +
                '}';
    }

    /**
    * Displays the details of the match including date, time, team names, scores, goals, and assists.
    *
    */
    public void displayMatchDetails() {
        System.out.println("Match Details:");
        System.out.println("Date & Time: " + matchDateTime);
        System.out.println(homeTeam.getName() + " (" + homeScore + ") vs " + awayTeam.getName() + " (" + awayScore + ")");
        System.out.println("Goals:");
        for (Map.Entry<Player, Integer> entry : playerGoals.entrySet()) {
            System.out.println(" - " + entry.getKey().getName() + ": " + entry.getValue());
        }
        System.out.println("Assists:");
        for (Map.Entry<Player, Integer> entry : playerAssists.entrySet()) {
            System.out.println(" - " + entry.getKey().getName() + ": " + entry.getValue());
        }
    }



}