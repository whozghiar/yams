package fr.lmg.games.yams.controllers;


import fr.lmg.games.yams.models.Joueur;
import fr.lmg.games.yams.models.Lancer;
import fr.lmg.games.yams.models.Partie;
import fr.lmg.games.yams.models.enumerations.Categorie;
import fr.lmg.games.yams.views.ConsoleView;

import java.util.*;

public class GameController {
    private Partie partie;
    private Lancer lancer;
    private final ConsoleView vue;

    public GameController(ConsoleView vue) {
        this.vue = vue;
    }

    public void initialiserPartie(List<String> noms) {
        this.partie = new Partie(noms);
        this.lancer = new Lancer();
    }

    public void jouerPartie() {
        while (!partie.estTerminee()) {

            // Afficher le tour du joueur actuel
            Joueur joueurActuel = partie.getJoueurActuel();
            this.vue.afficherTour(joueurActuel);

            // Lancer les dés
            lancer.lancerTous();
            this.vue.afficherDes(lancer.getDes());
            this.vue.afficherNombreLancersRestants(lancer.getNombreLancersRestants());

            // En fonction du nombre de lancer et de la demande de relance
            while (lancer.getNombreLancersRestants() > 0 && vue.demanderLancer()) {
                // Demander au joueur de verrouiller certains dés
                vue.demanderVerrouillageDes(lancer.getDes());

                // Lancer les dés non verrouillés
                lancer.lancerNonVerrouilles();
                this.vue.afficherDes(lancer.getDes());
                this.vue.afficherNombreLancersRestants(lancer.getNombreLancersRestants());

                // Si le joueur n'a plus de lancers, on sort de la boucle
                if (lancer.getNombreLancersRestants() == 0) {
                    break;
                }
            }

            // Demander au joueur de choisir une catégorie
            Categorie categorie = vue.demanderCategorie();
            // Enregistrer le score du joueur dans la catégorie choisie
            joueurActuel.getFeuilleScore().enregistrerScore(categorie, lancer.getDes());

            // Afficher le score du joueur
            this.vue.afficherFeuilleScore(joueurActuel);

            // Changer de joueur
            partie.changerJoueur();
            // Réinitialiser le lancer pour le prochain joueur
            this.lancer = new Lancer();
        }
        vue.afficherFinPartie(partie);
    }
}

