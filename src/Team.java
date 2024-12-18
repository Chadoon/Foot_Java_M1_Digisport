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

    public Player findPlayerByName(String playerName) {
        return players.stream()
                .filter(player -> player.getName().equalsIgnoreCase(playerName))
                .findFirst()
                .orElse(null);
    }

    public boolean removePlayer(String playerName) {
        Player playerToRemove = findPlayerByName(playerName);
        if (playerToRemove == null) {
            throw new IllegalStateException("Player " + playerName + " not found in the team.");
        }
        players.remove(playerToRemove);
        return true;
    }

    // --- Match Management ---
    public void recordMatch(int scoredGoals, int concededGoals) {
        if (scoredGoals < 0 || concededGoals < 0) {
            throw new IllegalArgumentException("Goals cannot be negative.");
        }
        if (scoredGoals > concededGoals) {
            wins++;
            points += 3;
        } else if (scoredGoals < concededGoals) {
            losses++;
        } else {
            draws++;
            points++;
        }
        goals += scoredGoals;
        goalsAgainst += concededGoals;
    }

    // --- Export and Display ---
    public String toJson() {
        return "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"wins\": " + wins + ",\n" +
                "  \"losses\": " + losses + ",\n" +
                "  \"draws\": " + draws + ",\n" +
                "  \"points\": " + points + ",\n" +
                "  \"goals\": " + goals + ",\n" +
                "  \"goalsAgainst\": " + goalsAgainst + ",\n" +
                "  \"goalDifference\": " + getGoalDifference() + ",\n" +
                "  \"players\": " + players.stream()
                .map(Player::toString)
                .collect(Collectors.toList()) + "\n" +
                "}";
    }

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
