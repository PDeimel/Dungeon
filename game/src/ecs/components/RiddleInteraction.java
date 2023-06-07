package ecs.components;

/**
  Class implements IInteraction to provide a riddle-based interaction for entities.
 */

import ecs.components.xp.XPComponent;
import ecs.entities.Entity;
import starter.Game;
import starter.SpawnCharacters;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RiddleInteraction implements IInteraction {

    private ArrayList<Questions> myQuestions= new ArrayList<>();
    private Logger riddlemanLogger = Logger.getLogger(this.getClass().getName());


    public RiddleInteraction(){

        myQuestions.add(new Questions("What are the three digits after the decimal point in pi?","141"));
        myQuestions.add(new Questions("How do you define a constant in Java?","final"));
        myQuestions.add(new Questions("What is the largest planet in our solar system?","jupiter"));
        myQuestions.add(new Questions("What is the term for a function that can call itself in Java?","recursion"));
        myQuestions.add(new Questions("What is the closest galaxy to the Milky Way?","andromeda"));
        myQuestions.add(new Questions("How many abstract methods can a functional interface have?","1"));
        myQuestions.add(new Questions("What is the name of the Egyptian god of the sun?","ra"));
        myQuestions.add(new Questions("What is the size of an int in Java?","4"));
        myQuestions.add(new Questions("What is the base class of all classes in Java?","object"));

    }

    /**
     * Interacts with the hero by asking a riddle.
     *
     * ask Hero a random question from a predetermined lis. Once the hero answers correct receive more Xp points
     * when the answer ist not correct, try to more times. If the answer is still false, more monsters are added in the Game
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

        while(attemptCounter < 3) {
            String input = sc.next();
            Matcher m = p.matcher(input);

            if (m.matches()) {
                XPComponent aComponent = (XPComponent) Game.getHero().get().getComponent(XPComponent.class).orElseThrow();
                aComponent.addXP(10);
                riddlemanLogger.info("XP was set in 10 extra points");
                Game.removeEntity(entity);
                break;
            } else {
                attemptCounter++;
                if (attemptCounter < 3) {
                    System.out.println("Incorrect answer. Try again.");
                }
                riddlemanLogger.info("tried to find answer one more time ");

                if(attemptCounter >= 3) {
                    SpawnCharacters punish = new SpawnCharacters(0);
                    punish.setAmountOfMonsters(1);
                    punish.onLevelLoad();
                    riddlemanLogger.info("more Monsters were add to game");
                    Game.removeEntity(entity);
                }
            }
        }

    }
}
