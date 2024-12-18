import java.util.*;

public class TeamUtils {

    // Listes globales de joueurs disponibles par position (Premier League 2023-2024)
    private static final List<String> goalkeepers = new ArrayList<>(Arrays.asList(
            "Aaron Ramsdale", "David Raya", "Alisson Becker", "Ederson",
            "Nick Pope", "Andre Onana", "Emiliano Martinez", "Robert Sanchez",
            "Jordan Pickford", "Kepa Arrizabalaga", "Sam Johnstone", "Bernd Leno"
    ));

    private static final List<String> defenders = new ArrayList<>(Arrays.asList(
            "Virgil van Dijk", "William Saliba", "Ruben Dias", "Trent Alexander-Arnold",
            "Andy Robertson", "Ben White", "Lisandro Martinez", "Thiago Silva",
            "Ben Chilwell", "Luke Shaw", "Kyle Walker", "Aaron Wan-Bissaka",
            "Sven Botman", "Kieran Trippier", "Joachim Andersen", "Tyrone Mings",
            "Pervis Estupinan", "James Tarkowski", "Kurt Zouma", "Gabriel Magalhaes",
            "James Justin", "Timothy Castagne", "Ezri Konsa", "Marc Guehi",
            "Jonny Evans", "Nathan Ake", "Clement Lenglet", "Eric Dier",
            "Wesley Fofana", "Conor Coady", "Victor Lindelof", "Max Kilman",
            "Chris Richards", "Ricardo Pereira", "Raphael Varane", "Diogo Dalot",
            "Lewis Dunk", "Rico Henry", "Emerson Royal", "Matt Doherty"
    ));

    private static final List<String> midfielders = new ArrayList<>(Arrays.asList(
            "Kevin De Bruyne", "Declan Rice", "Martin Ødegaard", "Bruno Fernandes",
            "Casemiro", "Rodri", "James Maddison", "Mason Mount",
            "Kai Havertz", "Alexis Mac Allister", "Moises Caicedo", "Enzo Fernandez",
            "James Ward-Prowse", "Youri Tielemans", "Thomas Partey", "Fabinho",
            "Christian Eriksen", "Bernardo Silva", "Jacob Ramsey", "Kalvin Phillips",
            "Wilfred Ndidi", "Douglas Luiz", "Conor Gallagher", "Joao Palhinha",
            "Harvey Barnes", "Ruben Loftus-Cheek", "Jorginho", "Declan Rice",
            "Yves Bissouma", "Matheus Nunes", "Jordan Henderson", "Naby Keita",
            "Pablo Fornals", "Tom Davies", "Pierre-Emile Højbjerg", "Amadou Onana",
            "Andreas Pereira", "Eberechi Eze", "Lucas Paqueta", "Harrison Reed"
    ));

    private static final List<String> forwards = new ArrayList<>(Arrays.asList(
            "Erling Haaland", "Harry Kane", "Bukayo Saka", "Mohamed Salah",
            "Darwin Nunez", "Gabriel Martinelli", "Eddie Nketiah", "Son Heung-min",
            "Marcus Rashford", "Riyad Mahrez", "Aleksandar Mitrovic", "Phil Foden",
            "Raheem Sterling", "Anthony Gordon", "Dominic Calvert-Lewin", "Jarrod Bowen",
            "Cody Gakpo", "Ollie Watkins", "Callum Wilson", "Evan Ferguson"
    ));
    /**
     * Ajoute des joueurs à une équipe en prenant des joueurs disponibles.
     * Si une position n'a plus de joueurs disponibles, aucun joueur n'est ajouté pour cette position.
     *
     * @param team L'équipe à laquelle les joueurs seront ajoutés
     */
    public static void addPlayersToTeam(Team team) {
        // Ajouter 1 gardien
        if (!goalkeepers.isEmpty()) {
            String goalkeeper = getRandomPlayer(goalkeepers);
            team.addPlayer(new Player(goalkeeper, Position.GK));
        }

        // Ajouter 4 défenseurs
        for (int i = 0; i < 4; i++) {
            if (!defenders.isEmpty()) {
                String defender = getRandomPlayer(defenders);
                team.addPlayer(new Player(defender, Position.DEF));
            }
        }

        // Ajouter 4 milieux
        for (int i = 0; i < 4; i++) {
            if (!midfielders.isEmpty()) {
                String midfielder = getRandomPlayer(midfielders);
                team.addPlayer(new Player(midfielder, Position.MID));
            }
        }

        // Ajouter 2 attaquants
        for (int i = 0; i < 2; i++) {
            if (!forwards.isEmpty()) {
                String forward = getRandomPlayer(forwards);
                team.addPlayer(new Player(forward, Position.FWD));
            }
        }
    }

    /**
     * Sélectionne un joueur aléatoire d'une liste et le retire de cette liste.
     *
     * @param players La liste de joueurs disponibles
     * @return Le nom du joueur sélectionné
     */
    private static String getRandomPlayer(List<String> players) {
        Random random = new Random();
        int index = random.nextInt(players.size());
        return players.remove(index); // Retire et retourne le joueur
    }

    /**
     * Affiche les joueurs restants disponibles.
     */
    public static void displayRemainingPlayers() {
        System.out.println("Gardiens disponibles : " + goalkeepers);
        System.out.println("Défenseurs disponibles : " + defenders);
        System.out.println("Milieux disponibles : " + midfielders);
        System.out.println("Attaquants disponibles : " + forwards);
    }
}