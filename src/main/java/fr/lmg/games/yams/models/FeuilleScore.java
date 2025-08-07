package fr.lmg.games.yams.models;

import fr.lmg.games.yams.models.enumerations.Categorie;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Stream.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class FeuilleScore {
    private final Map<Categorie, Integer> scores = new EnumMap<>(Categorie.class);
    private boolean yamsBonusAttribue = false;

    public boolean estCategorieDisponible(Categorie c) {
        return !scores.containsKey(c);
    }

    public void enregistrerScore(Categorie c, List<De> des) {
        if (!estCategorieDisponible(c)) {
            throw new IllegalStateException("La catégorie " + c + " a déjà été utilisée.");
        }

        int score = calculerScore(c, des);
        scores.put(c, score);

        // Vérifie les Yams supplémentaires
        if (c != Categorie.YAMS && isYams(des) && scores.containsKey(Categorie.YAMS)) {
            yamsBonusAttribue = true;
        }
    }

    private int calculerScore(Categorie c, List<De> des) {
        Map<Integer, Long> counts = des.stream()
                .collect(Collectors.groupingBy(De::getValeur, Collectors.counting()));

        List<Integer> valeurs = des.stream().map(De::getValeur).sorted().toList();

        return switch (c) {
            case AS, DEUX, TROIS, QUATRE, CINQ, SIX -> des.stream()
                    .filter(d -> d.getValeur() == c.getValeur())
                    .mapToInt(De::getValeur)
                    .sum();
            case BRELAN -> counts.values().stream().anyMatch(v -> v >= 3) ? sommeDes(des) : 0;
            case CARRE -> counts.values().stream().anyMatch(v -> v >= 4) ? sommeDes(des) : 0;
            case FULL -> (counts.containsValue(3L) && counts.containsValue(2L)) ? 25 : 0;
            case PETITE_SUITE -> contientSuite(valeurs, 4) ? 30 : 0;
            case GRANDE_SUITE -> contientSuite(valeurs, 5) ? 40 : 0;
            case YAMS -> isYams(des) ? 50 : 0;
            case CHANCE -> sommeDes(des);
        };
    }

    private boolean isYams(List<De> des) {
        return des.stream().map(De::getValeur).distinct().count() == 1;
    }

    private int sommeDes(List<De> des) {
        return des.stream().mapToInt(De::getValeur).sum();
    }

    private boolean contientSuite(List<Integer> sortedValeurs, int longueur) {
        Set<Integer> uniques = new TreeSet<>(sortedValeurs);
        List<Integer> list = new ArrayList<>(uniques);

        int max = 1, current = 1;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == list.get(i - 1) + 1) {
                current++;
                max = Math.max(max, current);
            } else {
                current = 1;
            }
        }
        return max >= longueur;
    }

    public int calculerTotal() {
        int total = scores.values().stream().mapToInt(Integer::intValue).sum() + bonusSiApplicable();
        if (yamsBonusAttribue) total += 100;
        return total;
    }

    private int bonusSiApplicable() {
        int totalSuperieur = of(
                Categorie.AS,
                Categorie.DEUX,
                Categorie.TROIS,
                Categorie.QUATRE,
                Categorie.CINQ,
                Categorie.SIX
        ).mapToInt(c -> scores.getOrDefault(c, 0))
                .sum();

        return totalSuperieur >= 63 ? 35 : 0;
    }
}

