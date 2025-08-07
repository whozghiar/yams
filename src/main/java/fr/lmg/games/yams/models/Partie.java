package fr.lmg.games.yams.models;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Partie {
    private final List<Joueur> joueurs;
    private int indexJoueurActuel;
    private int tour;

    public Partie(List<String> nomsJoueurs) {
        joueurs = new ArrayList<>();
        for (String nom : nomsJoueurs) {
            joueurs.add(new Joueur(nom));
        }
        indexJoueurActuel = 0;
        tour = 1;
    }

    public Joueur getJoueurActuel() {
        return joueurs.get(indexJoueurActuel);
    }

    public void changerJoueur() {
        indexJoueurActuel = (indexJoueurActuel + 1) % joueurs.size();
        if (indexJoueurActuel == 0) tour++;
    }

    public boolean estTerminee() {
        return tour > 13;
    }

    public Joueur determinerGagnant() {
        return joueurs.stream()
                .max(Comparator.comparingInt(j -> j.getFeuilleScore().calculerTotal()))
                .orElse(null);
    }
}
