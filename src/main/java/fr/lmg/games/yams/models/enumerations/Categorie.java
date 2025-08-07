package fr.lmg.games.yams.models.enumerations;

import lombok.Getter;

@Getter
public enum Categorie {
    AS(1),
    DEUX(2),
    TROIS(3),
    QUATRE(4),
    CINQ(5),
    SIX(6),
    BRELAN(0),
    CARRE(0),
    FULL(0),
    PETITE_SUITE(0),
    GRANDE_SUITE(0),
    YAMS(0),
    CHANCE(0);

    private final int valeur;

    Categorie(int valeur) {
        this.valeur = valeur;
    }

}
