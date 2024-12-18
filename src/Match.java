/**
 * This class was created to manage a match between two teams and to get the match details
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match implements Serializable {

    // Attributes
    private List<GoalEvent> events; // Liste des événements de type GoalEvent
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

    // Constructor
    public Match(LocalDateTime dateTime, Team homeTeam, Team awayTeam) {
        if (!homeTeam.isValidForMatch() || !awayTeam.isValidForMatch()) {
            throw new IllegalArgumentException("Both teams must have exactly 11 players to start the match.");
        }
        this.matchDateTime = dateTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.playerGoals = new HashMap<>();
        this.playerAssists = new HashMap<>();
        this.events = new ArrayList<>();
    }

    // Getters and Setters

    public LocalDateTime getDateTime() {
        return matchDateTime;
    }

    public void setDateTime(LocalDateTime date) {
        this.matchDateTime = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void addGoalEvent(Player scorer, Player assister, Team team, int minute) {
        if (!team.getPlayers().contains(scorer)) {
            throw new IllegalArgumentException("Scorer " + scorer.getName() + " is not part of the team.");
        }

        GoalEvent newEvent = new GoalEvent(scorer, assister, team, minute);
        if (events.contains(newEvent)) {
            System.out.println("Event already exists: " + newEvent);
            return;
        }

        events.add(newEvent);
        playerGoals.put(scorer, playerGoals.getOrDefault(scorer, 0) + 1);
        scorer.addGoal();

        if (assister != null) {
            playerAssists.put(assister, playerAssists.getOrDefault(assister, 0) + 1);
            assister.addAssist();
        }

        updateTeamScore();
    }

    private void updateTeamScore() {
        int homeGoals = playerGoals.entrySet().stream()
                .filter(e -> homeTeam.getPlayers().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue).sum();
        int awayGoals = playerGoals.entrySet().stream()
                .filter(e -> awayTeam.getPlayers().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue).sum();

        this.homeScore = homeGoals;
        this.awayScore = awayGoals;
    }

    public void recordScore(int homeScore, int awayScore) {
        if (!homeTeam.isValidForMatch() || !awayTeam.isValidForMatch()) {
            throw new IllegalStateException("Both teams must have at least 11 players to record a score.");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        updateTeamScore();
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

    public void displayMatchDetails() {
        System.out.println("Match Details:");
        System.out.println("Date & Time: " + matchDateTime);
        System.out.println(homeTeam.getName() + " (" + homeScore + ") vs " + awayTeam.getName() + " (" + awayScore + ")");
        System.out.println("Events:");
        for (GoalEvent event : events) {
            System.out.println(" - " + event.getMinute() + "': " + event.getScorer().getName() +
                    (event.getAssister() != null ? " (Assist: " + event.getAssister().getName() + ")" : ""));
        }
    }

}
