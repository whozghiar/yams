package fr.lmg.games.yams;

import fr.lmg.games.yams.models.De;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour la classe De")
public class DeTest {
    private De de;

    @BeforeEach
    void setUp() {
        de = new De();
    }

    @Nested
    @DisplayName("Tests du constructeur")
    class ConstructeurTests {

        @Test
        @DisplayName("Le dé devrait être créé avec une valeur valide")
        void constructeurParDefaut() {
            assertNotNull(de);
            assertEquals(0, de.getValeur());
            assertFalse(de.isVerrouille());
        }

        @Test
        @DisplayName("Le constructeur avec paramètres devrait initialiser correctement")
        void constructeurAvecParametres() {
            De dePersonnalise = new De(4, true);
            assertEquals(4, dePersonnalise.getValeur());
            assertTrue(dePersonnalise.isVerrouille());
        }
    }

    @Nested
    @DisplayName("Tests de lancement du dé")
    class LancerTests {

        @Test
        @DisplayName("Le lancer devrait donner une valeur entre 1 et 6")
        void testLancerValeurValide() {
            de.lancer();
            int valeur = de.getValeur();
            assertTrue(valeur >= 1 && valeur <= 6,
                    "La valeur du dé devrait être entre 1 et 6, valeur actuelle : " + valeur);
        }

        @Test
        @DisplayName("Le lancer ne devrait pas changer la valeur si verrouillé")
        void testLancerDeVerrouille() {
            de.setValeur(3);
            de.verrouiller();
            de.lancer();
            assertEquals(3, de.getValeur());
        }

        @Test
        @DisplayName("Plusieurs lancers devraient donner des valeurs valides")
        void testPlusieurLancers() {
            for (int i = 0; i < 100; i++) {
                de.lancer();
                int valeur = de.getValeur();
                assertTrue(valeur >= 1 && valeur <= 6,
                        "La valeur du dé devrait toujours être entre 1 et 6, valeur actuelle : " + valeur);
            }
        }
    }

    @Nested
    @DisplayName("Tests de verrouillage")
    class VerrouillageTests {

        @Test
        @DisplayName("Le verrouillage devrait fonctionner correctement")
        void testVerrouillage() {
            assertFalse(de.isVerrouille());
            de.verrouiller();
            assertTrue(de.isVerrouille());
        }

        @Test
        @DisplayName("Le déverrouillage devrait fonctionner correctement")
        void testDeverrouillage() {
            de.verrouiller();
            assertTrue(de.isVerrouille());
            de.deverrouiller();
            assertFalse(de.isVerrouille());
        }
    }

    @Nested
    @DisplayName("Tests des setters")
    class SetterTests {

        @ParameterizedTest
        @DisplayName("setValeur devrait accepter des valeurs valides")
        @ValueSource(ints = {1, 2, 3, 4, 5, 6})
        void testSetValeurValide(int valeur) {
            de.setValeur(valeur);
            assertEquals(valeur, de.getValeur());
        }

        @Test
        @DisplayName("setVerrouille devrait modifier l'état correctement")
        void testSetVerrouille() {
            de.setVerrouille(true);
            assertTrue(de.isVerrouille());
            de.setVerrouille(false);
            assertFalse(de.isVerrouille());
        }
    }

    @Test
    @DisplayName("Test des méthodes equals et hashCode")
    void testEqualsEtHashCode() {
        De de1 = new De(3, true);
        De de2 = new De(3, true);
        De de3 = new De(4, true);

        assertEquals(de1, de2);
        assertNotEquals(de1, de3);
        assertEquals(de1.hashCode(), de2.hashCode());
    }

    @Test
    @DisplayName("Test de la méthode toString")
    void testToString() {
        de.setValeur(4);
        de.setVerrouille(true);
        String representation = de.toString();
        assertTrue(representation.contains("4"));
        assertTrue(representation.contains("true"));
    }
}