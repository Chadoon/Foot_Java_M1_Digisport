import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ********** PLAYER **********
        System.out.println("*********PLAYER************");

        // Création d'un joueur et ajout de statistiques de buts et passes décisives
        Player player = new Player("Kai Havertz", Position.FWD);
        player.addGoal(); // Ajouter un but au joueur
        player.addAssist(); // Ajouter une passe décisive au joueur

        // Création et ajout de plusieurs joueurs dans une liste
        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(new Player("Kevin De Bruyne", Position.MID));
        players.add(new Player("Erling Haaland", Position.FWD));
        players.add(new Player("Jack Grealish", Position.MID));

        // Affichage des statistiques de chaque joueur
        for (Player p : players) {
            System.out.println(p.getStats());
        }

        // Suppression d'un joueur de la liste
        players.remove(player);

        // ********** TEAM **********
        System.out.println("*********TEAM************");

        // Création d'une liste pour stocker les équipes
        List<Team> teams = new ArrayList<>();

        // Création de plusieurs équipes
        Team team1 = new Team("Arsenal");
        Team team2 = new Team("Manchester City");
        Team team3 = new Team("Liverpool");
        Team team4 = new Team("Manchester United");

        // Ajout de joueurs uniques à chaque équipe via une méthode utilitaire
        TeamUtils.addPlayersToTeam(team1);
        TeamUtils.addPlayersToTeam(team2);
        TeamUtils.addPlayersToTeam(team3);
        TeamUtils.addPlayersToTeam(team4);

        // Affichage des informations de chaque équipe
        System.out.println("\n=== Equipes créées ===");
        team1.displayTeamInfo();
        team2.displayTeamInfo();
        team3.displayTeamInfo();
        team4.displayTeamInfo();

        // Affichage des joueurs non assignés à une équipe
        System.out.println("\n=== Joueurs restants ===");
        TeamUtils.displayRemainingPlayers();

        // Ajout d'un joueur à une équipe et gestion des erreurs
        try {
            team1.addPlayer(player); // Ajoute "Kai Havertz" à l'équipe "Arsenal"
        } catch (IllegalStateException e) {
            System.out.println("Erreur lors de l'ajout d'un joueur : " + e.getMessage());
        }

        // Ajout des équipes créées à la liste principale
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);

        // Ajout d'une nouvelle équipe (exemple)
        Team arsenal = new Team("Arsenal");
        teams.add(arsenal);

        // Vérification si le nombre maximum d'équipes est atteint
        if (teams.size() > 10) {
            System.out.println("Nombre maximum d'équipes atteint !");
        }

        // Affichage de la liste des équipes
        System.out.println("\nListe d'équipes  :");
        teams.forEach(team -> System.out.println("- " + team.getName()));

        // ********** MATCH **********
        System.out.println("*********MATCH************");

        // Création d'un match manuel entre deux équipes avec une date et une heure spécifiques
        LocalDateTime matchDateTime = LocalDateTime.of(2024, 12, 20, 18, 30);
        Match manualMatch = new Match(matchDateTime, team3, team2);

        // Sélection des joueurs de chaque équipe
        List<Player> team3Players = new ArrayList<>(team3.getPlayers());
        List<Player> team2Players = new ArrayList<>(team2.getPlayers());

        // Ajout d'événements de buts manuellement
        Player manualScorer2 = team2Players.get(0); // Le premier joueur de l'équipe 2 marque
        Player manualAssister2 = team2Players.size() > 1 ? team2Players.get(1) : null; // Deuxième joueur comme assistant
        Player manualScorer3 = team3Players.get(0); // Le premier joueur de l'équipe 3 marque

        manualMatch.addGoalEvent(manualScorer2, manualAssister2, team2, 10); // But de l'équipe 2 à la 10e minute
        manualMatch.addGoalEvent(manualScorer3, null, team3, 25); // But de l'équipe 3 à la 25e minute

        // Affichage des détails du match
        manualMatch.displayMatchDetails();

        // ********** COMPETITION **********
        System.out.println("\n******* COMPETITION **********\n");

        // Création de nouvelles équipes pour une compétition
        Team team6 = new Team("Chelsea");
        Team team7 = new Team("Aston Villa");
        Team team8 = new Team("Leicester");
        Team team9 = new Team("Westham");
        Team team10 = new Team("Tottenham");

        // Ajout de joueurs uniques à ces équipes
        TeamUtils.addPlayersToTeam(team6);
        TeamUtils.addPlayersToTeam(team7);
        TeamUtils.addPlayersToTeam(team8);
        TeamUtils.addPlayersToTeam(team9);
        TeamUtils.addPlayersToTeam(team10);

        // Création et configuration d'une compétition
        Competition competition = new Competition("Premier League");
        competition.addTeam(team6);
        competition.addTeam(team7);
        competition.addTeam(team8);
        competition.addTeam(team9);
        competition.addTeam(team10);

        // Affichage des équipes participant à la compétition
        System.out.println("\n=== Équipes participant à la compétition ===");
        competition.getTeams().forEach(team -> System.out.println("- " + team.getName()));

        // Programmation manuelle de matchs
        competition.scheduleMatch(team7, team6, LocalDateTime.of(2024, 12, 20, 18, 30));
        competition.scheduleMatch(team8, team9, LocalDateTime.of(2024, 12, 21, 20, 00));
        competition.scheduleMatch(team9, team10, LocalDateTime.of(2024, 12, 22, 15, 00));

        // Génération automatique des matchs avec la méthode Round Robin
        System.out.println("\n=== Génération automatique des matchs (Round Robin) ===");
        competition.generateMatchesRoundRobin();
        competition.displayMatches();

        // Simulation des scores pour chaque match
        System.out.println("\n=== Simulation des matchs ===");
        for (Match match : competition.getMatches()) {
            competition.simulateMatch(match);
        }

        // Affichage des résultats après simulation
        System.out.println("\n=== Résultats des matchs après simulation ===");
        competition.displayMatches();

        // Affichage du classement de la compétition
        System.out.println("\n=== Classement de la compétition ===");
        competition.displayStandings();

        // Sauvegarde de la compétition dans un fichier
        System.out.println("\n=== Sauvegarde de la compétition dans un fichier binaire ===");
        Competition.saveCompetitionToFile("competitionData", competition);

        // Chargement de la compétition depuis le fichier
        System.out.println("\n=== Chargement de la compétition depuis un fichier ===");
        Competition loadedCompetition = Competition.loadCompetitionFromFile("competitionData");

        // Suppression d'une équipe et mise à jour des matchs restants
        System.out.println("\n=== Suppression d'une équipe ===");
        if (competition.removeTeam(team6)) {
            System.out.println("Équipe " + team6.getName() + " supprimée avec succès.");
        }

        // Affichage des matchs après suppression
        System.out.println("\n=== Matchs restants après suppression d'une équipe ===");
        competition.displayMatches();

        // Affichage du classement final
        System.out.println("\n=== Classement final ===");
        competition.displayStandings();
    }
}
