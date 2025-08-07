package fr.lmg.games.yams;

import fr.lmg.games.yams.controllers.GameController;
import fr.lmg.games.yams.views.ConsoleView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class YamsApplication {

    /*
	public static void main(String[] args) {

		SpringApplication.run(YamsApplication.class, args);
	}*/

    public static void main(String[] args) {
        ConsoleView vue = new ConsoleView();
        GameController controller = new GameController(vue);

        Scanner sc = new Scanner(System.in);
        List<String> noms = new ArrayList<>();
        System.out.println("Nombre de joueurs : ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.print("Nom du joueur " + (i + 1) + " : ");
            noms.add(sc.nextLine());
        }

        controller.initialiserPartie(noms);
        controller.jouerPartie();
    }

}
