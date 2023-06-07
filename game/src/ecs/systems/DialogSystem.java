package ecs.systems;

import starter.SpawnCharacters;
import java.util.logging.Logger;
import java.util.Scanner;

public class DialogSystem {
    private static final Logger logger = Logger.getLogger(DialogSystem.class.getName());

    public static void Raetzelloesen() {
        Scanner sc = new Scanner(System.in);
        int Counter = 0;
        boolean Rede = true;

        while (Rede = true) {
            String input = sc.nextLine();

            logger.info("Hallo Held willst du ein Rätzel hören?");
            if (input.matches("Ja|Ok|Natürlich|Auf Jedenfall")) {
                logger.info("Was trinkt die Führungsebene?");
                String answer = sc.nextLine();
                while (Counter >5) {
                    if (answer.matches("Leitungswasser|")); {
                    logger.info("Das ist richtig hier nimm das für deine Mühe!");

                    Counter = 6;
                    }
                    logger.info("Das ist nicht Correct! Hast noch: "+ Counter +"/4 Versuche übrig");
                    Counter++;
                }
            }
            if (Counter == 5) {
                logger.info("Hier für deine Mühe!!");
                SpawnCharacters punish = new SpawnCharacters(0);
                punish.setAmountOfMonsters(1);
                punish.onLevelLoad();
            }
            if (input.matches("Nein|No|Nope")) {
                logger.info("Okay bis zum nächstenmal");
                Rede = false;
            }
            else {
                logger.info("Könntest du dich bitte Wiederholen!");
            }
            sc.close();
        }

    }
}
