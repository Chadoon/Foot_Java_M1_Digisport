//import java.util.List;
//
//public class GoalEvent extends Event {
//    private final Player scorer;
//    private final Player assister;
//
//    /**
//     * Constructor for GoalEvent.
//     * @param minute   The minute the goal was scored
//     * @param scorer   The player who scored the goal
//     * @param assister The player who assisted the goal (can be null)
//     */
//    public GoalEvent(int minute, Player scorer, Player assister) {
//        // Appel au constructeur de la classe parent avec un type spécifique et les joueurs impliqués
//        super(Type.GOAL, minute, assister == null ? List.of(scorer) : List.of(scorer, assister));
//        this.scorer = scorer;
//        this.assister = assister;
//    }
//
//    /**
//     * Get the player who scored the goal
//     * @return The scorer
//     */
//    public Player getScorer() {
//        return scorer;
//    }
//
//    /**
//     * Get the player who assisted the goal
//     * @return The assister, or null if no assist was provided
//     */
//    public Player getAssister() {
//        return assister;
//    }
//
//    /**
//     * Provides a string representation of the goal event
//     * @return A descriptive string for the goal event
//     */
//    @Override
//    public String toString() {
//        String assistText = (assister != null) ? " (Assisted by " + assister.getName() + ")" : "";
//        return "Goal by " + scorer.getName() + " at minute " + getMinute() + assistText;
//    }
//}