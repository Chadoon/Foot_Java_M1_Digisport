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

    /**
     * Save a competition to a file.
     * @param filePath the path to the file to which to save the competition
     * @param compet the competition to save
     */
    public static void saveCompetitionToFile( String filePath, Competition compet) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(compet);
            System.out.println("Competition saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving competition: " + e.getMessage());
        }
    }

    /**
     * Load a competition from a file.
     * @param filePath the path to the file
     * @return the loaded competition, or null if an error occurs
     * @throws IOException if the file does not exist or cannot be read
     * @throws ClassNotFoundException if the file does not contain a Competition object
     */
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


/**
 * Generates a round-robin schedule of matches for all teams in the competition.
 * Each team plays every other team twice, once at home and once away, with random match dates.
 * If there are fewer than two teams, matches are not generated.
 * Clears any existing matches before generating new ones.
 */

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




/**
 * Simulates a football match between two teams, generating random goals
 * and updating the match's score and team statistics.
 *
 * The match is simulated minute by minute with a 10% chance of a goal being
 * scored each minute. Goals are randomly assigned to either the home or away
 * team, and the match's score is recorded accordingly.
 *
 * Updates the goals scored and goals against for both teams and awards points
 * based on the match result: 3 points for a win, 1 point for a draw.
 *
 * Displays the final result of the match.
 *
 * @param match The Match object representing the game to be simulated.
 */

    public void simulateMatch(Match match) {
        Random random = new Random();

        int homeGoals = 0;
        int awayGoals = 0;

        // Simulation de chaque minute du match
        for (int minute = 1; minute <= 90; minute += random.nextInt(10) + 1) {
            if (random.nextDouble() < 0.1) { // 10% de chance qu'un but soit marqué à chaque minute
                if (random.nextBoolean()) {
                   // But de l'équipe à domicile

                    simulateGoal(match.getHomeTeam(), match, minute, random);
                    homeGoals++;
                } else {
                    // But de l'équipe à l'extérieur
                    simulateGoal(match.getAwayTeam(), match, minute, random);
                    awayGoals++;
                }
            }
        }

        // Mettre à jour le score final dans le match
        match.recordScore(homeGoals, awayGoals);

        // **Mise à jour des équipes après le match**
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        // Mise à jour des buts marqués et encaissés
        homeTeam.addGoals(homeGoals);
        homeTeam.addGoalsAgainst(awayGoals);
        awayTeam.addGoals(awayGoals);
        awayTeam.addGoalsAgainst(homeGoals);

        // Mise à jour des points
        if (homeGoals > awayGoals) { // Victoire équipe domicile
            homeTeam.addPoints(3);
        } else if (homeGoals < awayGoals) { // Victoire équipe extérieure
            awayTeam.addPoints(3);
        } else { // Match nul
            homeTeam.addPoints(1);
            awayTeam.addPoints(1);
        }


        // Afficher le résultat du match
        System.out.println("Match simulated: " + match.getHomeTeam().getName() + " " +
                homeGoals + " - " + awayGoals + " " + match.getAwayTeam().getName());
    }

/**
 * Simulates a goal event in a match by randomly selecting a player from the team
 * to score and optionally selecting an assisting player.
 *
 * The function categorizes players into forwards, midfielders, defenders, and goalkeepers
 * and assigns probabilities for each category to score. It then randomly determines a scorer
 * based on these probabilities and, with a 20% chance, selects an assister from the team.
 *
 * If a goal is scored, the scorer's goal count and the assister's assist count are updated,
 * and a new GoalEvent is added to the match. The goal event is printed to the console.
 *
 * @param team the team for which the goal is being simulated
 * @param match the match in which the goal event occurs
 * @param minute the minute in the match when the goal is scored
 * @param random the Random object used for random number generation
 */

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
 * Displays the standings of all teams in the competition, sorted by points,
 * goal difference, and goals scored. Teams are first compared based on their
 * points, followed by goal difference if points are equal, and finally goals
 * scored if both points and goal difference are equal. The details of each
 * team, including name, points, goals scored, goals against, and goal
 * difference, are printed to the console.
 */

    public void displayStandings() {
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