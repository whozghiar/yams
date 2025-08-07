package fr.lmg.games.yams.models;

import lombok.*;

import java.util.Random;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class De {
    private int valeur;
    private boolean verrouille;
    private static final Random random = new Random();

    /**
     * Lance le dé si celui-ci n'est pas verrouillé.
     * La valeur du dé est un entier aléatoire entre 1 et 6.
     * Si le dé est verrouillé, sa valeur reste inchangée.
     */
    public void lancer() {
        if (!this.isVerrouille()) {
            valeur = random.nextInt(6) + 1;
        }
    }

    /**
     * La fonction verrouiller() permet de verrouiller le dé,
     * empêchant ainsi son relancement lors des prochains lancers.
     */
    public void verrouiller() {
        verrouille = true;
    }

    /**
     * La fonction deverrouiller() permet de déverrouiller le dé,
     * permettant ainsi son relancement lors des prochains lancers.
     */
    public void deverrouiller() {
        verrouille = false;
    }
}
