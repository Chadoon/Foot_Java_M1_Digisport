/**
 * This class was created to manage a team, calculate the team stats (goal, points)
 */

import java.util.HashSet;
import java.util.Set;

//Attributes
public class Team {
    /**
     * team name
     */
    private String name;
    /**
     * team's players
     */
    private Set<Player> players; // Utilisation de Set pour éviter les doublons
    /**
     * number of wins, losses, draws, points and team's goals and goals against
     */
    private int wins, losses, draws, points, goals, goalsAgainst;
    private static final int MAX_PLAYERS = 23; // Limite maximale de joueurs
    private static final int REQUIRED_PLAYERS_FOR_MATCH = 11;

    // Constructor
    public Team(String name) {
        this.name = name;
        this.players = new HashSet<>();
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.points = 0;
        this.goals = 0;
        this.goalsAgainst = 0;
    }

    // Getters and Setters
    /**
     * get the team name
     * @return team name
     */
    public String getName() { return name; }
    /**
     * get the team's players
     * @return team's players
     * */
    public Set<Player> getPlayers() { return players; }
    /**
     * Get the number of win
     * @return number of win
     */
    public int getWins() { return wins; }
    /**
     * Get the number of losses
     * @return number of losses
     */
    public int getLosses() { return losses; }
    /**
     * Get the number of draws
     * @return number of draws
     */
    public int getDraws() { return draws; }
    /**
     * Get the total points of the team
     * @return total points accumulated by the team
     */
    public int getPoints() { return points; }
    public int getGoals() { return goals; }
    public int getGoalsAgainst() { return goalsAgainst; }

    /**
     * Get the goal difference
     * @return difference between goals scored and conceded
     */
    public int getGoalDifference() {
        return goals - goalsAgainst;
    }

    /**
     * Checks if the team is valid for a match, meaning that the number of players is exactly 11
     * @return true if the team is valid for a match, false otherwise
     */
    public boolean isValidForMatch() {
        return players.size() == REQUIRED_PLAYERS_FOR_MATCH;
    }

    /**
     * Add a player to the team
     * @param player : the player to add
     * @return true if the player has been added, else false
     */
    public boolean addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            System.out.println("Error: Cannot add more players. The team already has " + MAX_PLAYERS + " players.");
            return false;
        }
        boolean added = players.add(player);
        if (added) {
            System.out.println("Player " + player.getName() + " added to the team " + name + ".");
        } else {
            System.out.println("Error: Player " + player.getName() + " is already in the team.");
        }
        return added;
    }

    /**
     * Find if a player is in the team
     * @param playerName the name of the player we want to look for
     * @return the player if found, and null otherwise
     */
    public Player findPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Remove a player from the team
     * @param playerName, the name of the player we want to remove
     * @return true if the player has been removed, false otherwise
     */
    public boolean removePlayer(String playerName) {
        Player playerToRemove = findPlayerByName(playerName);
        if (playerToRemove != null) {
            players.remove(playerToRemove);
            System.out.println("Player " + playerName + " removed from the team " + name + ".");
            return true;
        }
        System.out.println("Error: Player " + playerName + " not found in the team.");
        return false;
    }

    /**
     * Record the result of a match and update statistics
     * @param scoredGoals goals scored by the team
     * @param concededGoals goals conceded by the team
     */
    public void recordMatch(int scoredGoals, int concededGoals) {
        if (scoredGoals > concededGoals) {
            wins++;
            points += 3;
            System.out.println("Match result: WIN");
        } else if (scoredGoals < concededGoals) {
            losses++;
            System.out.println("Match result: LOSS");
        } else {
            draws++;
            points += 1;
            System.out.println("Match result: DRAW");
        }
        goals += scoredGoals;
        goalsAgainst += concededGoals;
        System.out.println("Match recorded: Scored " + scoredGoals + ", Conceded " + concededGoals);
    }

    /**
     * Export the team's information in JSON format
     * @return JSON representation of the team's information
     */
    public String toJson() {
        return "{ \"name\": \"" + name + "\", " +
                "\"wins\": " + wins + ", " +
                "\"losses\": " + losses + ", " +
                "\"draws\": " + draws + ", " +
                "\"points\": " + points + ", " +
                "\"goals\": " + goals + ", " +
                "\"goalsAgainst\": " + goalsAgainst + ", " +
                "\"goalDifference\": " + getGoalDifference() + ", " +
                "\"players\": " + players.toString() + " }";
    }

    /**
     * Display the team's information
     */
    public void displayTeamInfo() {
        System.out.println("Team: " + name);
        System.out.println("Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws + ", Points: " + points);
        System.out.println("Goals: " + goals + ", Goals Against: " + goalsAgainst + ", Goal Difference: " + getGoalDifference());
        System.out.println("Players (" + players.size() + "):");
        for (Player player : players) {
            System.out.println(" - " + player);
        }
    }
}
