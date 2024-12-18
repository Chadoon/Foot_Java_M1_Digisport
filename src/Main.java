public class Main {
    public static void main(String[] args) {
        // Étape 1 : Créer les équipes
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

        // PLAYER
        // Ajouter un joueur, lui ajouter une stat de but, une stat de assist
        Player player = new Player(" Kai Havertz", Position.FWD);
        player.addGoal();
        player.addAssist();
        //Voir les stats d'un joueur
        System.out.println(player.getStats());
        // Kai Havertz - Goals: 1, Assists: 1




    }
}
