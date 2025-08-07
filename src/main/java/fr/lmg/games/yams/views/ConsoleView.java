package fr.lmg.games.yams.views;

import fr.lmg.games.yams.models.De;
import fr.lmg.games.yams.models.Joueur;
import fr.lmg.games.yams.models.Partie;
import fr.lmg.games.yams.models.enumerations.Categorie;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void afficherTour(Joueur joueur) {
        System.out.println("\nAu tour de " + joueur.getNom());
    }

    /**
     * Affiche les dés du joueur.
     * Chaque dé est affiché avec son index, sa valeur et s'il est verrouillé
     * @param des
     */
    public void afficherDes(List<De> des) {
        IntStream.range(0, des.size()).forEach(i -> {
            De de = des.get(i);
            System.out.printf("Dé %d: %d %s\n", i + 1, de.getValeur(), de.isVerrouille() ? "(verrouillé)" : "");
        });
    }

    /**
     * Affiche le nombre de lancers restants.
     * @param nombreLancersRestants
     */
    public void afficherNombreLancersRestants(int nombreLancersRestants) {
        System.out.println("Nombre de lancers restants : " + nombreLancersRestants);
    }

    /**
     * Demande à l'utilisateur s'il souhaite relancer les dés.
     * @return true si l'utilisateur souhaite relancer, false sinon.
     * Si l'utilisateur entre autre chose que "o"
     */
    public boolean demanderLancer() {
        System.out.print("Lancer les dés ? (o/n): ");
        return scanner.nextLine().equalsIgnoreCase("o");
    }

    public void demanderVerrouillageDes(List<De> des) {
        System.out.print("Indices des dés à verrouiller (ex: 1 3 5): ");
        String[] tokens = scanner.nextLine().split(" ");
        for (String token : tokens) {
            try {
                int idx = Integer.parseInt(token) - 1;
                if (idx >= 0 && idx < des.size()) {
                    des.get(idx).verrouiller();
                }
            } catch (NumberFormatException ignored) { }
        }
    }

    public Categorie demanderCategorie(Joueur joueur) {
        System.out.println("Catégories disponibles : ");
        joueur.getFeuilleScore().getScores().keySet().forEach(used -> System.out.print(""));
        for (Categorie c : Categorie.values()) {
            if (joueur.getFeuilleScore().estCategorieDisponible(c)) {
                System.out.println("- " + c);
            }
        }
        while (true) {
            try {
                String saisie = scanner.nextLine().toUpperCase();
                Categorie choisie = Categorie.valueOf(saisie);
                if (!joueur.getFeuilleScore().estCategorieDisponible(choisie)) {
                    System.out.println("Catégorie déjà utilisée. Veuillez en choisir une autre.");
                } else {
                    return choisie;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Catégorie invalide. Réessayez : ");
            }
        }
    }

    public void afficherFinPartie(Partie partie) {
        System.out.println("\n--- Fin de la partie ---");
        for (Joueur joueur : partie.getJoueurs()) {
            System.out.printf("%s : %d points\n", joueur.getNom(), joueur.getFeuilleScore().calculerTotal());
        }
        Joueur gagnant = partie.determinerGagnant();
        System.out.println("Gagnant : " + gagnant.getNom());
    }

    public void afficherFeuilleScore(Joueur joueur) {
        System.out.println("\nFeuille de score de " + joueur.getNom() + ":");
        joueur.getFeuilleScore().getScores().forEach((categorie, score) -> {
            System.out.printf("%s : %d\n", categorie, score);
        });
        System.out.println("Total : " + joueur.getFeuilleScore().calculerTotal());
    }

    public void afficherMessage(Joueur joueur) {
        System.out.println("Message pour " + joueur.getNom() + ":" + "\n");
        System.out.println("Veuillez choisir une catégorie qui n'est pas déjà utilisée.");
    }

}
