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

    private static final long serialVersionUID = 1L;


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

    public List<GoalEvent> getGoalEvents() {
        return events;
    }

    /**
     * Add a goal event to the match, given the scorer, assister (if any), the team that scored, and the minute of the goal.
     * @param scorer the player who scored the goal
     * @param assister the player who assisted the goal (if any)
     * @param team the team that scored the goal
     * @param minute the minute of the goal
     */
    public void addGoalEvent(Player scorer, Player assister, Team team, int minute) {
        // Vérifications
        if (!team.getPlayers().contains(scorer)) {
            throw new IllegalArgumentException("Scorer " + scorer.getName() + " is not part of the team.");
        }
        if (assister != null && !team.getPlayers().contains(assister)) {
            throw new IllegalArgumentException("Assister " + assister.getName() + " is not part of the team.");
        }

        // Créer et enregistrer l'événement de but
        GoalEvent newEvent = new GoalEvent(scorer, assister, team, minute);
        if (events.contains(newEvent)) {
            System.out.println("Event already exists: " + newEvent);
            return;
        }

        events.add(newEvent);

        // Mise à jour des statistiques des joueurs
        playerGoals.put(scorer, playerGoals.getOrDefault(scorer, 0) + 1);
        scorer.addGoal();

        if (assister != null) {
            playerAssists.put(assister, playerAssists.getOrDefault(assister, 0) + 1);
            assister.addAssist();
        }

        // Mise à jour des scores
        updateTeamScore();
    }



    /**
     * Update the home and away scores based on the current events in the match.
     * This method is called every time a goal event is added to the match.
     */
    private void updateTeamScore() {
        homeScore = (int) events.stream()
                .filter(e -> e.getTeam().equals(homeTeam))
                .count();

        awayScore = (int) events.stream()
                .filter(e -> e.getTeam().equals(awayTeam))
                .count();
    }

    /**
     * Record the final score of the match, given the home and away scores.
     * This method checks that both teams have at least 11 players before recording the score.
     * @param homeScore the number of goals scored by the home team
     * @param awayScore the number of goals scored by the away team
     * @throws IllegalStateException if either team has fewer than 11 players
     */
    public void recordScore(int homeScore, int awayScore) {
        if (!homeTeam.isValidForMatch() || !awayTeam.isValidForMatch()) {
            throw new IllegalStateException("Both teams must have at least 11 players to record a score.");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
//
//    public void recordGoal(Player scorer, Player assistant, int minute) {
//        // Ajoute le but dans le registre des buts
//        playerGoals.put(scorer, playerGoals.getOrDefault(scorer, 0) + 1);
//
//        if (assistant != null) {
//            playerAssists.put(assistant, playerAssists.getOrDefault(assistant, 0) + 1);
//        }
//    }

    /**
     * Provides a string representation of the match, including the date and time of the match,
     * the home and away teams, the home and away scores, and the goals and assists for each player.
     * @return A string representation of the match
     */
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
     * Display the details of a match, including the date and time, the home and away teams, the home and away scores, and the goals and assists for each player.
     */
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
