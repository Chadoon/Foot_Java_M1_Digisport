import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Competition {

    // Attributes
    /**
     * The name of the competition
     */
    private String name;
    /**
     * The list of matches, the list of teams
     */
    private List<Match> matches;
    private List<Team> teams;

    // Constructor
    public Competition(String name) {
        this.name = name;
        this.matches = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    // Getters
    /**
     * Get all matches in the competition.
     * @return the list of matches in the competition
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * Get all teams in the competition.
     * @return the list of teams in the competition
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Add a match to the competition.
     * @param match the match to add
     */
    public void addMatch(Match match) {
        matches.add(match);
    }

    /**
     * Add a team to the competition
     * @param team the team to add
     * @return true if the team has been added, false otherwise
     * @throws IllegalArgumentException if the team already exists
     */
    public boolean addTeam(Team team) {
        if (!team.isValidForMatch()) {
            System.out.println("Error: Team " + team.getName() + " is not valid and cannot be added to the competition.");
            return false;
        }
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(team.getName())) {
                //throw new IllegalArgumentException("Error: Team " + team.getName() + " is already in the competition.");
                teams.remove(t);
                System.out.println("Team " + team.getName() + " replaced in competition " + name + ".");
                break;
            }
        }
        teams.add(team);
        System.out.println("Team " + team.getName() + " added to competition " + name + ".");
        return true;
    }

    /**
     * Remove a team from the competition.
     * @param team the team to remove
     * @return true if the team has been removed, false otherwise
     */
    public boolean removeTeam(Team team) {
        if (!teams.contains(team)) {
            System.out.println("Error: Team " + team.getName() + " is not part of the competition.");
            return false;
        }

        // Remove the team from the competition
        teams.remove(team);
        System.out.println("Team " + team.getName() + " removed from competition " + name + ".");

        // Remove all matches involving this team
        matches.removeIf(match ->
                match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team));

        System.out.println("All matches involving " + team.getName() + " have been removed.");
        return true;
    }

    /**
     * Schedule a match between two teams at the given date and time.
     * @param teamA the first team
     * @param teamB the second team
     * @param matchDateTime the date and time of the match
     */
    public void scheduleMatch(Team teamA, Team teamB, LocalDateTime matchDateTime) {
        if (!teams.contains(teamA) || !teams.contains(teamB)) {
            System.out.println("Error: One or both teams are not part of the competition.");
            return;
        }

        Match match = new Match(matchDateTime, teamA, teamB);
        matches.add(match);
        System.out.println("Match scheduled: " + teamA.getName() + " vs " + teamB.getName() + " on " + matchDateTime);
    }

    /**
     * Display all matches in the competition, with their details.
     */
    public void displayMatches() {
        System.out.println("Matches in " + name + ":");
        for (Match match : matches) {
            match.displayMatchDetails();
        }
    }

    /**
     * Display the standings for the competition, with teams sorted by points descending.
     * if 2 teams have similar points then we compare with the goals difference
     */
    public void displayStandings() {
        System.out.println("Standings for competition " + name + ":");
        teams.sort((t1, t2) -> {
            int pointComparison = Integer.compare(t2.getPoints(), t1.getPoints());
            if (pointComparison == 0) {
                return Integer.compare((t2.getGoals() - t2.getGoalsAgainst()), (t1.getGoals() - t1.getGoalsAgainst()));
            }
            return pointComparison;
        });

        // Affichage des dÃ©tails de chaque Ã©quipe aprÃ¨s le tri
        for (Team team : teams) {
            System.out.println(team.getName() + " - Points: " + team.getPoints() +
                    ", Goals: " + team.getGoals() + ", Goals Against: " + team.getGoalsAgainst() +
                    ", Goal Difference: " + (team.getGoals() - team.getGoalsAgainst()));
        }
    }

    public void generateMatches() {
        if (teams.size() < 2) {
            System.out.println("Error: At least two teams are required to schedule matches.");
            return;
        }

        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);

        // Clear existing matches
        matches.clear();

        // Generate all possible matches
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Team teamA = teams.get(i);
                Team teamB = teams.get(j);

                // Generate a random date for the match
                LocalDateTime matchDate = startDate.plusDays(random.nextInt(30)); // Random within 30 days

                // Schedule the match
                Match match = new Match(matchDate, teamA, teamB);
                matches.add(match);
            }
        }

        System.out.println("All matches generated successfully. Total matches: " + matches.size());
    }

    // Simuler un match avec des scores aléatoires
    public void simulateMatch(Match match) {
        Random random = new Random();
        int homeGoals = random.nextInt(5);
        int awayGoals = random.nextInt(5);

        // Simulation de chaque but (en utilisant simulateGoal)
        for (int minute = 1; minute <= 90; minute += random.nextInt(10) + 1) {
            if (random.nextDouble() < 0.1) { // 10% de chance qu'un but soit marqué à chaque minute
                if (random.nextBoolean()) {
                    simulateGoal(match.getHomeTeam(), match, minute, random);
                } else {
                    simulateGoal(match.getAwayTeam(), match, minute, random);
                }
            }
        }

        // Mettre à jour le score final
        match.recordScore(homeGoals, awayGoals);
    }

    // Simuler un but pour une team
    private void simulateGoal(Team team, Match match, int minute, Random random) {
        List<Player> players = new ArrayList<>(team.getPlayers());

        // Catégorisation des joueurs par poste
        List<Player> forwards = new ArrayList<>();
        List<Player> midfielders = new ArrayList<>();
        List<Player> defenders = new ArrayList<>();
        List<Player> goalkeepers = new ArrayList<>();

        for (Player player : players) {
            switch (player.getPosition()) {
                case FWD: forwards.add(player); break;
                case MID: midfielders.add(player); break;
                case DEF: defenders.add(player); break;
                case GK: goalkeepers.add(player); break;
            }
        }

        Player scorer = null;
        Player assister = null;

        // Probabilités pour choisir le buteur
        double roll = random.nextDouble();
        if (roll < 0.50 && !forwards.isEmpty()) { // 50 % attaquants
            scorer = forwards.get(random.nextInt(forwards.size()));
        } else if (roll < 0.80 && !midfielders.isEmpty()) { // 30 % milieux
            scorer = midfielders.get(random.nextInt(midfielders.size()));
        } else if (roll < 0.95 && !defenders.isEmpty()) { // 15 % défenseurs
            scorer = defenders.get(random.nextInt(defenders.size()));
        } else if (!goalkeepers.isEmpty()) { // 5 % gardiens
            scorer = goalkeepers.get(random.nextInt(goalkeepers.size()));
        }

        // Probabilité pour l'assistant (20 %)
        if (random.nextDouble() < 0.2) {
            assister = players.get(random.nextInt(players.size()));
        }

        // Si un buteur a été trouvé
        if (scorer != null) {
            scorer.addGoal();
            if (assister != null) {
                assister.addAssist();
            }
            match.addGoalEvent(scorer, assister, team, minute);

            // Affichage du but simulé
            System.out.println("Minute " + minute + ": Goal for " + team.getName() + " by " + scorer.getName() +
                    (assister != null ? " (Assist: " + assister.getName() + ")" : ""));
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Competition: " + name + "\n");
        for (Match match : matches) {
            sb.append(match.toString()).append("\n");
        }
        return sb.toString();
    }
}