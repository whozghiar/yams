package fr.lmg.games.yams.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Lancer {
    private final List<De> des;
    private int nombreLancersRestants = 3;

    /**
     * Constructeur de la classe Lancer.
     * Crée une liste de 5 dés, chacun initialisé avec une valeur aléatoire.
     * Chaque dé est un objet de la classe De, qui gère la valeur et l'état de verrouillage du dé.
     * Le nombre de lancers restants est initialisé à 3.
     */
    public Lancer() {
        // Initialisation du nombre de lancers restants à 3
        this.setNombreLancersRestants(3);

        // Création de la liste de dés
        des = new ArrayList<>();

        // Ajout de 5 dés à la liste
        for (int i = 0; i < 5; i++) {
            des.add(new De());
        }

    }

    /**
     * Lance tous les dés de la liste.
     * Chaque dé est lancé, ce qui signifie que sa valeur est mise à jour avec un nombre aléatoire entre 1 et 6.
     * Le nombre de lancers restants est décrémenté de 1.
     * Si le nombre de lancers restants atteint 0, les dés ne peuvent plus
     */
    public void lancerTous() {
        for (De de : des) {
            de.lancer();
        }
        nombreLancersRestants--;
    }

    /**
     * Lance uniquement les dés qui ne sont pas verrouillés.
     * Chaque dé non verrouillé est lancé, ce qui signifie que sa valeur est mise à jour avec un nombre aléatoire entre 1 et 6.
     * Le nombre de lancers restants est décrémenté de 1.
     */
    public void lancerNonVerrouilles() {
        for (De de : des) {
            if (!de.isVerrouille()) {
                de.lancer();
            }
        }
        nombreLancersRestants--;
    }
}
