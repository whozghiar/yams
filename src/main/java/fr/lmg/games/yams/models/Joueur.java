package fr.lmg.games.yams.models;

import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Joueur {
    private final String nom;
    private final FeuilleScore feuilleScore;

    public Joueur(String nom) {
        this.nom = nom;
        this.feuilleScore = new FeuilleScore();
    }
}
