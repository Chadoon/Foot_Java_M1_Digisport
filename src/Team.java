import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents a football team with players, stats, and utilities.
 */
public class Team implements Serializable{
    private String name;
    private Set<Player> players;
    private int wins, losses, draws, points, goals, goalsAgainst;

    private static final int MAX_PLAYERS = 23; // Maximum allowed players in the team
    private static final int REQUIRED_PLAYERS_FOR_MATCH = 11;

    private static final long serialVersionUID = 1L;

    private static final Map<Position, Integer> FORMATION = Map.of(
            Position.GK, 1,
            Position.DEF, 4,
            Position.MID, 4,
            Position.FWD, 2
    );

    /**
     * Constructor for the Team class.
     * @param name Name of the team.
     */
    public Team(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty.");
        }
        this.name = name;
        this.players = new HashSet<>();
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.points = 0;
        this.goals = 0;
        this.goalsAgainst = 0;
    }

    // --- Getters ---
    public String getName() { return name; }
    public Set<Player> getPlayers() { return players; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public int getPoints() { return points; }
    public int getGoals() { return goals; }
    public int getGoalsAgainst() { return goalsAgainst; }

    public int getGoalDifference() {
        return goals - goalsAgainst;
    }


    // Méthodes pour mettre à jour les statistiques
    public void addPoints(int points) { this.points += points; }
    public void addGoals(int goals) { this.goals += goals; }
    public void addGoalsAgainst(int goalsAgainst) { this.goalsAgainst += goalsAgainst; }


// --- Validation Methods ---
    public boolean isValidFormation() {
        Map<Position, Integer> positionCounts = new HashMap<>();
        for (Position pos : Position.values()) {
            positionCounts.put(pos, 0);
        }
        players.forEach(player -> positionCounts.put(player.getPosition(),
                positionCounts.get(player.getPosition()) + 1));
        return FORMATION.entrySet().stream()
                .allMatch(entry -> positionCounts.getOrDefault(entry.getKey(), 0).equals(entry.getValue()));
    }

/**
 * Validates if the team is eligible for a match.
 *
 * @return true if the team has the required number of players and a valid formation.
 * @throws IllegalStateException if the team does not have exactly the required number
 *                               of players or if the team's formation is invalid.
 */

    public boolean isValidForMatch() {
        if (players.size() != REQUIRED_PLAYERS_FOR_MATCH) {
            throw new IllegalStateException("Team must have exactly " + REQUIRED_PLAYERS_FOR_MATCH + " players.");
        }
        if (!isValidFormation()) {
            throw new IllegalStateException("Team formation is invalid.");
        }
        return true;
    }

    // --- Player Management ---

    
/**
 * Adds a player to the team if possible.
 * 
 * @param player the player to be added
 * @return true if the player was successfully added
 * @throws IllegalArgumentException if the player is null
 * @throws IllegalStateException if the team already has the maximum number of players or 
 *                               if the player is already in the team
 */

    public boolean addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Cannot add a null player.");
        }
        if (players.size() >= MAX_PLAYERS) {
            throw new IllegalStateException("Cannot add more players. Maximum limit of " + MAX_PLAYERS + " reached.");
        }
        if (!players.add(player)) {
            throw new IllegalStateException("Player " + player.getName() + " is already in the team.");
        }
        return true;
    }

    /**
     * Finds a player in the team by name, ignoring case.
     * @param playerName the name of the player to find
     * @return the player with the given name, or null if not found
     */
    public Player findPlayerByName(String playerName) {
        return players.stream()
                .filter(player -> player.getName().equalsIgnoreCase(playerName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes a player from the team by name, ignoring case.
     * 
     * @param playerName the name of the player to remove
     * @return true if the player was found and removed
     * @throws IllegalStateException if the player is not found in the team
     */
    public boolean removePlayer(String playerName) {
        Player playerToRemove = findPlayerByName(playerName);
        if (playerToRemove == null) {
            throw new IllegalStateException("Player " + playerName + " not found in the team.");
        }
        players.remove(playerToRemove);
        return true;
    }



    /**
     * Display team information to the console, including the team name, wins, losses, draws, points, goals, goals against, goal difference, and the players in the team.
     */
    public void displayTeamInfo() {
        System.out.println("Team: " + name);
        System.out.println("Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws + ", Points: " + points);
        System.out.println("Goals: " + goals + ", Goals Against: " + goalsAgainst + ", Goal Difference: " + getGoalDifference());
        System.out.println("Players (" + players.size() + "):");
        players.forEach(player -> System.out.println(" - " + player));
    }

    //classement joueurs
    public static List<Player> sortPlayersByGoals(List<Player> players) {
        players.sort((p1, p2) -> Integer.compare(p2.getGoals(), p1.getGoals())); // Tri décroissant par nombre de buts
        return players;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                ", wins=" + wins +
                ", losses=" + losses +
                ", draws=" + draws +
                ", points=" + points +
                ", goals=" + goals +
                ", goalsAgainst=" + goalsAgainst +
                '}';
    }
}
