import java.time.LocalDateTime;
//HELLO

public class Main {
    public static void main(String[] args) {
        // Team creation
        Team team1 = new Team("Chelsea");
        Team team2 = new Team("Barcelona");
        Team team3 = new Team("Real Madrid");
        Team team4 = new Team("Manchester United");
        Team team5 = new Team("Paris Saint-Germain");
        Team team6 = new Team("Juventus");

        //Players creation
        Player p1 = new Player("Lionel Messi");
        Player p2 = new Player("Kylian Mbappé");
        Player p3 = new Player("Cristiano Ronaldo");
        Player p4 = new Player("Neymar Jr.");

        // Add players to the team
        team1.addPlayer(p1);
        team1.addPlayer(p2);
        team1.addPlayer(p3);
        team1.addPlayer(new Player("Karim Benzema"));
        team1.addPlayer(new Player("Robert Lewandowski"));

        // Display team info
        team1.displayTeamInfo();

        // Test: find player by name
        team1.findPlayerByName("Karim Benzema");
        team1.findPlayerByName(p4.getName());

        // Remove a player
        team1.removePlayer(p1.getName());

        // Record match results for team1
        team1.recordMatch(4, 2); // Chelsea wins
        team1.recordMatch(2, 3); // Chelsea loses
        team1.recordMatch(1, 1); // Chelsea draws
        team1.displayTeamInfo();

        // Create a match between two teams
        Match match = new Match(LocalDateTime.of(2024, 12, 14, 15, 30), team4, team2);
        match.addGoal(p1); // Example of a goal by Messi
        match.addGoal(p3); // Example of a goal by Ronaldo

        // Display match details
        match.displayMatchDetails();

        // Create competition and add teams
        Competition competition = new Competition("Champions League");
        competition.addTeam(team1);
        competition.addTeam(team2);
        competition.addTeam(team3);
        competition.addTeam(team4);
        competition.addTeam(team5);
        competition.addTeam(team6);

        // Add matches to competition
        competition.addMatch(new Match(
                LocalDateTime.of(2024, 12, 4, 12, 10),
                team1,
                team3, 2, 3));
        competition.addMatch(new Match(
                LocalDateTime.of(2024, 12, 15, 18, 45),
                team5,
                team6, 0, 3));

        // Display competition matches
        competition.displayMatches();

        // Record results in competition
        for (Match m : competition.getMatches()) {
            if (m.getHomeScore() > m.getAwayScore()) {
                m.getHomeTeam().recordMatch(3, 0);  // Home team wins
                m.getAwayTeam().recordMatch(0, 0);  // Away team loses
            } else if (m.getHomeScore() < m.getAwayScore()) {
                m.getHomeTeam().recordMatch(0, 0);  // Home team loses
                m.getAwayTeam().recordMatch(3, 0);  // Away team wins
            } else {
                m.getHomeTeam().recordMatch(1, 1);  // Draw
                m.getAwayTeam().recordMatch(1, 1);  // Draw
            }
        }

        // Display standings after the matches
        System.out.println("\n--- Updated Standings after Matches ---");
        competition.displayStandings();

        // Scenario: More matches and updates
        System.out.println("\n--- Adding More Matches ---");
        competition.addMatch(new Match(LocalDateTime.of(2024, 12, 6, 14, 30), team3, team4, 1, 0));
        competition.addMatch(new Match(LocalDateTime.of(2024, 12, 7, 17, 0), team5, team6, 1, 1));

        // Record the new matches
        competition.getMatches().get(2).getHomeTeam().recordMatch(3, 0);  // Real Madrid wins
        competition.getMatches().get(2).getAwayTeam().recordMatch(0, 0);  // Man United loses

        competition.getMatches().get(3).getHomeTeam().recordMatch(1, 1);  // PSG draws
        competition.getMatches().get(3).getAwayTeam().recordMatch(1, 1);  // Juventus draws

        // Display updated standings
        System.out.println("\n--- Updated Standings after More Matches ---");
        competition.displayStandings();
    }
}
