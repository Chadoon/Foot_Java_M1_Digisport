import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // PLAYER
        System.out.println("*********PLAYER************");
        // Ajouter un joueur, lui ajouter une stat de but, une stat de assist
        //Voir les stats d'un joueur
        // Kai Havertz - Goals: 1, Assists: 1
        //Creer plusieurs joueurs en mm temps
        // Exemple : Ajout de plusieurs joueurs
        Player player = new Player(" Kai Havertz", Position.FWD);
        player.addGoal();
        player.addAssist();

        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(new Player("Kevin De Bruyne", Position.MID ));
        players.add(new Player("Erling Haaland", Position.FWD));
        players.add(new Player("Jack Grealish", Position.MID));

        for (Player p : players) {
            System.out.println(p.getStats());
        }
        players.remove(player);


        //TEAM
        System.out.println("*********TEAM************");
        // Liste des équipes
        List<Team> teams = new ArrayList<>();

        // Création ded équipes
        Team team1 = new Team("Arsenal");
        Team team2 = new Team("Manchester City");
        Team team3 = new Team("Liverpool");
        Team team4 = new Team("Manchester United");

        // Étape 2 : Ajouter des joueurs uniques à chaque équipe
        TeamUtils.addPlayersToTeam(team1);
        TeamUtils.addPlayersToTeam(team2);
        TeamUtils.addPlayersToTeam(team3);
        TeamUtils.addPlayersToTeam(team4);

        // Étape 3 : Afficher les informations des équipes
        System.out.println("\n=== Equipes créées ===");
        team1.displayTeamInfo();
        team2.displayTeamInfo();
        team3.displayTeamInfo();
        team4.displayTeamInfo();

        // Étape 4 : Afficher les joueurs restants
        System.out.println("\n=== Joueurs restants ===");
        TeamUtils.displayRemainingPlayers();


        // Ajout de joueurs à l'équipe
        try {
            team1.addPlayer(player);
        } catch (IllegalStateException e) {
            System.out.println("Erreur lors de l'ajout d'un joueur : " + e.getMessage());
        }

        // Ajout de l'équipe à la liste des équipes
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);


        // Enregistrement d'un match pour l'équipe
        try {
            team1.recordMatch(3, 1); // Victoire 3-1
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de l'enregistrement du match : " + e.getMessage());
        }

        // Affichage des informations mises à jour sur l'équipe
        System.out.println("\nMise à jour après le match :");
        team4.displayTeamInfo();

        // Ajout d'une nouvelle équipe
        Team arsenal = new Team("Arsenal");
        teams.add(arsenal);

        // Limite sur le nombre d'équipes
        if (teams.size() > 10) {
            System.out.println("Nombre maximum d'équipes atteint !");
        }

        // Affichage des équipes
        System.out.println("\nListe d'équipes  :");
        teams.forEach(team -> System.out.println("- " + team.getName()));

        //COMPETITION
        System.out.println("*******COMPETITION**********");
        Competition competition = new Competition("Championship");
        competition.addTeam(team3);
        competition.addTeam(team2);
        competition.addTeam(team4);

        // Création de matchs
        competition.scheduleMatch(team3, team2, LocalDateTime.of(2024, 12, 20, 18, 30));
        competition.scheduleMatch(team3, team1, LocalDateTime.of(2024, 12, 21, 20, 0));
        competition.scheduleMatch(team2, team1, LocalDateTime.of(2024, 12, 22, 15, 0));


        // Affichage des matchs
        competition.displayMatches();

        // Simulation des matchs avec des scores aléatoires
        for (Match match : competition.getMatches()) {
            competition.simulateMatch(match);
        }

        // Affichage des matchs après simulation
        competition.displayMatches();

        // Affichage du classement
        competition.displayStandings();

        System.out.println("\n=== Round Robin Match Generation ===");
        competition.generateMatchesRoundRobin();
        competition.displayMatches();

//        System.out.println("\n=== Exporting Standings ===");
//        try {
//            competition.exportStandingsToCSV("standings.csv");
//        } catch (IOException e) {
//            System.out.println("Error exporting standings: " + e.getMessage());
//        }

        System.out.println("\n=== Removing a Team ===");
        if (competition.removeTeam(team1)) {
            System.out.println("Team " + team1.getName() + " removed successfully.");
        }
        competition.displayMatches();

        System.out.println("\n=== Competition Standings ===");
        competition.displayStandings();

        System.out.println("\n=== Save a competition (dans un binary file )");
        System.out.println("\n=== Exporting Competition Details ===");
        competition.saveCompetitionToFile( "compet1") ;
        





    }



}
