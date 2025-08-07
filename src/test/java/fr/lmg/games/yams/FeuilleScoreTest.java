package fr.lmg.games.yams;

import fr.lmg.games.yams.models.De;
import fr.lmg.games.yams.models.FeuilleScore;
import fr.lmg.games.yams.models.enumerations.Categorie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour la classe FeuilleScore")
class FeuilleScoreTest {

    private FeuilleScore feuilleScore;
    private List<De> des;

    @BeforeEach
    void setUp() {
        feuilleScore = new FeuilleScore();
        des = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            des.add(new De());
        }
    }

    @Nested
    @DisplayName("Tests de disponibilité des catégories")
    class DisponibiliteTests {

        @Test
        @DisplayName("Toutes les catégories devraient être disponibles au début")
        void testCategoriesInitialementDisponibles() {
            for (Categorie c : Categorie.values()) {
                assertTrue(feuilleScore.estCategorieDisponible(c));
            }
        }

        @Test
        @DisplayName("Une catégorie ne devrait plus être disponible après utilisation")
        void testCategorieNonDisponibleApresUtilisation() {
            preparerDes(1, 1, 1, 1, 1); // Yams de 1
            feuilleScore.enregistrerScore(Categorie.YAMS, des);
            assertFalse(feuilleScore.estCategorieDisponible(Categorie.YAMS));
        }
    }

    @Nested
    @DisplayName("Tests d'enregistrement des scores")
    class EnregistrementScoreTests {

        @Test
        @DisplayName("Devrait lever une exception si la catégorie est déjà utilisée")
        void testEnregistrementCategorieDejaUtilisee() {
            preparerDes(1, 1, 1, 1, 1);
            feuilleScore.enregistrerScore(Categorie.YAMS, des);

            assertThrows(IllegalStateException.class, () ->
                    feuilleScore.enregistrerScore(Categorie.YAMS, des));
        }

        @ParameterizedTest
        @DisplayName("Test des scores pour les catégories numériques")
        @EnumSource(value = Categorie.class, names = {"AS", "DEUX", "TROIS", "QUATRE", "CINQ", "SIX"})
        void testScoresCategoriesNumeriques(Categorie categorie) {
            int valeur = categorie.getValeur();
            preparerDes(valeur, valeur, valeur, 1, 2);
            feuilleScore.enregistrerScore(categorie, des);
            assertEquals(valeur * 3, feuilleScore.getScores().get(categorie));
        }
    }

    @Nested
    @DisplayName("Tests des combinaisons spéciales")
    class CombinaisonsSpecialesTests {

        @Test
        @DisplayName("Devrait calculer correctement le score d'un brelan")
        void testScoreBrelan() {
            preparerDes(3, 3, 3, 4, 5);
            feuilleScore.enregistrerScore(Categorie.BRELAN, des);
            assertEquals(18, feuilleScore.getScores().get(Categorie.BRELAN));
        }

        @Test
        @DisplayName("Devrait calculer correctement le score d'un carré")
        void testScoreCarre() {
            preparerDes(4, 4, 4, 4, 5);
            feuilleScore.enregistrerScore(Categorie.CARRE, des);
            assertEquals(21, feuilleScore.getScores().get(Categorie.CARRE));
        }

        @Test
        @DisplayName("Devrait calculer correctement le score d'un full")
        void testScoreFull() {
            preparerDes(2, 2, 2, 3, 3);
            feuilleScore.enregistrerScore(Categorie.FULL, des);
            assertEquals(25, feuilleScore.getScores().get(Categorie.FULL));
        }

        @Test
        @DisplayName("Devrait calculer correctement le score d'une petite suite")
        void testScorePetiteSuite() {
            preparerDes(1, 2, 3, 4, 6);
            feuilleScore.enregistrerScore(Categorie.PETITE_SUITE, des);
            assertEquals(30, feuilleScore.getScores().get(Categorie.PETITE_SUITE));
        }

        @Test
        @DisplayName("Devrait calculer correctement le score d'une grande suite")
        void testScoreGrandeSuite() {
            preparerDes(2, 3, 4, 5, 6);
            feuilleScore.enregistrerScore(Categorie.GRANDE_SUITE, des);
            assertEquals(40, feuilleScore.getScores().get(Categorie.GRANDE_SUITE));
        }
    }

    @Nested
    @DisplayName("Tests des bonus")
    class BonusTests {

        @Test
        @DisplayName("Devrait attribuer le bonus supérieur quand le total ≥ 63")
        void testBonusSuperieur() {
            preparerDes(6, 6, 6, 6, 6);
            feuilleScore.enregistrerScore(Categorie.SIX, des);
            preparerDes(5, 5, 5, 5, 5);
            feuilleScore.enregistrerScore(Categorie.CINQ, des);
            assertEquals(35, feuilleScore.calculerTotal() - 55);
        }

        @Test
        @DisplayName("Devrait attribuer le bonus Yams")
        void testBonusYams() {
            preparerDes(6, 6, 6, 6, 6);
            feuilleScore.enregistrerScore(Categorie.YAMS, des);
            preparerDes(5, 5, 5, 5, 5);
            feuilleScore.enregistrerScore(Categorie.CHANCE, des);
            assertTrue(feuilleScore.isYamsBonusAttribue());
        }
    }

    private void preparerDes(int... valeurs) {
        for (int i = 0; i < valeurs.length; i++) {
            des.get(i).setValeur(valeurs[i]);
        }
    }
}