package ecs.components;

/** Class implements IInteraction to provide a riddle-based interaction for entities. */
import ecs.components.xp.XPComponent;
import ecs.entities.Entity;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import starter.Game;
import starter.SpawnCharacters;

public class RiddleInteraction implements IInteraction {

    private final ArrayList<Questions> myQuestions = new ArrayList<>();
    private final Logger riddlemanLogger = Logger.getLogger(this.getClass().getName());

    public RiddleInteraction() {

        myQuestions.add(
                new Questions("What are the three digits after the decimal point in pi?", "141"));
        myQuestions.add(new Questions("How do you define a constant in Java?", "final"));
        myQuestions.add(
                new Questions("What is the largest planet in our solar system?", "jupiter"));
        myQuestions.add(
                new Questions(
                        "What is the term for a function that can call itself in Java?",
                        "recursion"));
        myQuestions.add(new Questions("What is the closest galaxy to the Milky Way?", "andromeda"));
        myQuestions.add(
                new Questions("How many abstract methods can a functional interface have?", "1"));
        myQuestions.add(new Questions("What is the name of the Egyptian god of the sun?", "ra"));
        myQuestions.add(new Questions("What is the size of an int in Java?", "4"));
        myQuestions.add(new Questions("What is the base class of all classes in Java?", "object"));
    }

    /**
     * Interacts with the hero by asking him to solve a riddle. The hero is presented with a
     * question chosen randomly from a list. With the correct solving of the riddle the hero
     * receives a bit of XP. If he answers wrongly, he is asked to answer again. If the player
     * doesn't succeed after the third trie he will be punished in the form of monsters.
     *
     * @param entity Riddleman
     */
    @Override
    public void onInteraction(Entity entity) {

        int size = myQuestions.size();
        Random randomNum = new Random();
        int randomIndex = randomNum.nextInt(size);
        Questions obj = myQuestions.get(randomIndex);
        System.out.println(obj.getQuestion());
        Scanner sc = new Scanner(System.in);
        String expectedAnswer = obj.getAnswer();
        Pattern p = Pattern.compile(expectedAnswer, Pattern.CASE_INSENSITIVE);
        int attemptCounter = 0;

        while (attemptCounter < 3) {
            String input = sc.next();
            Matcher m = p.matcher(input);

            if (m.matches()) {
                XPComponent aComponent =
                        (XPComponent)
                                Game.getHero().get().getComponent(XPComponent.class).orElseThrow();
                aComponent.addXP(50);
                riddlemanLogger.info("XP was increased by 50");
                Game.removeEntity(entity);
                break;
            } else {
                attemptCounter++;
                if (attemptCounter < 3) {
                    System.out.println("Incorrect answer. Try again.");
                }
                riddlemanLogger.info("tried to find answer one more time ");

                if (attemptCounter == 3) {
                    SpawnCharacters punish = new SpawnCharacters(0);
                    punish.setAmountOfMonsters(1);
                    punish.onLevelLoad();
                    riddlemanLogger.info("Monsters were spawned as a punishment");
                    Game.removeEntity(entity);
                }
            }
        }
    }
}
