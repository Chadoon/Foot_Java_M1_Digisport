import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.Serializable;

public class Competition implements Serializable {


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
    private static final long serialVersionUID = 1L;

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

    public static void saveCompetitionToFile( String filePath, Competition compet) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(compet);
            System.out.println("Competition saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving competition: " + e.getMessage());
        }
    }

    public static Competition loadCompetitionFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            return (Competition)obj ;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading competition: " + e.getMessage());
            return null;
        }
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
        //verifie que 2 equipes jouent pas au mm moment
        for (Match m : matches) {
            if ((m.getHomeTeam().equals(teamA) && m.getAwayTeam().equals(teamB) ||
                    m.getHomeTeam().equals(teamB) && m.getAwayTeam().equals(teamA)) &&
                    m.getDateTime().equals(matchDateTime)) {
                System.out.println("Error: A match between these teams is already scheduled at this time.");
                return;
            }
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


    public void generateMatchesRoundRobin() {
        if (teams.size() < 2) {
            System.out.println("Error: At least two teams are required to schedule matches.");
            return;
        }

        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);

        matches.clear();

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Team teamA = teams.get(i);
                Team teamB = teams.get(j);

                LocalDateTime matchDate = startDate.plusDays(random.nextInt(30));
                matches.add(new Match(matchDate, teamA, teamB)); // Match aller
                matches.add(new Match(matchDate.plusDays(7), teamB, teamA)); // Match retour
            }
        }

        System.out.println("Round-robin matches generated successfully.");
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

    /**
     * Display the standings for the competition, with teams sorted by points descending.
     * if 2 teams have similar points then we compare with the goals difference
     */
    public void displayStandings() {
        System.out.println("Standings for competition " + name + ":");
        teams.sort((t1, t2) -> {
            int pointComparison = Integer.compare(t2.getPoints(), t1.getPoints());
            if (pointComparison == 0) {
                int goalDiffComparison = Integer.compare((t2.getGoals() - t2.getGoalsAgainst()), (t1.getGoals() - t1.getGoalsAgainst()));
                if (goalDiffComparison == 0) {
                    return Integer.compare(t2.getGoals(), t1.getGoals());
                }
                return goalDiffComparison;
            }
            return pointComparison;
        });

        // Affichage des details de chaque equipe apres le tri
        for (Team team : teams) {
            System.out.println(team.getName() + " - Points: " + team.getPoints() +
                    ", Goals: " + team.getGoals() + ", Goals Against: " + team.getGoalsAgainst() +
                    ", Goal Difference: " + (team.getGoals() - team.getGoalsAgainst()));
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