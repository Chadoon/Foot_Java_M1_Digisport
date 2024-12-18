import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoalEvent extends Event implements Serializable {
    private final Player scorer;
    private final Player assister;
    private Team team;
    private static final long serialVersionUID = 1L;


    /**
     * Constructor for GoalEvent.
     *
     * @param minute   The minute the goal was scored
     * @param scorer   The player who scored the goal
     * @param assister The player who assisted the goal (can be null)
     */
    //Type eventType, int minute, ArrayList<Player> players
    public GoalEvent(Player scorer, Player assister, Team team, int minute) {
        super(Type.GOAL, minute, new ArrayList<>()); // Appelle le constructeur d'Event
        if (scorer == null || team == null) {
            throw new IllegalArgumentException("Scorer and team cannot be null");
        }
        if (!team.getPlayers().contains(scorer)) {
            throw new IllegalArgumentException("Scorer must belong to the team");
        }
        if (assister != null && !team.getPlayers().contains(assister)) {
            throw new IllegalArgumentException("Assister must belong to the team");
        }

        this.scorer = scorer;
        this.assister = assister;
        this.team = team;

        // Ajouter le buteur et l'assistant à la liste de joueurs
        getPlayersE().add(scorer);
        if (assister != null) {
            getPlayersE().add(assister);
        }
    }


    /**
     * Get the player who scored the goal
     *
     * @return The scorer
     */
    public Player getScorer() {
        return scorer;
    }



    /**
     * Get the player who assisted the goal
     *
     * @return The assister, or null if no assist was provided
     */
    public Player getAssister() {
        return assister;
    }

    public Team getTeam() {
        return team;
    }

    /**
     * Provides a string representation of the goal event
     *
     * @return A descriptive string for the goal event
     */
    @Override
    public String toString() {
        return getEvent(); // Réutilise la méthode surchargée
    }
}

