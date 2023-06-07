package ecs.components;

import ecs.components.HealthComponent;
import ecs.components.IInteraction;
import ecs.components.xp.XPComponent;
import ecs.entities.Entity;
import ecs.entities.Riddleman;
import starter.Game;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RiddleInteraction implements IInteraction {

    private ArrayList<Questions> myQuestions= new ArrayList<>();
    Riddleman myRiddleman;


    public RiddleInteraction(Riddleman this_Riddleman){
        myRiddleman=this_Riddleman;
        myQuestions.add(new Questions("What are the three digits after the decimal point in pi?","145"));
        myQuestions.add(new Questions("What is the largest planet in our solar system?","jupiter"));
        myQuestions.add(new Questions("What is the closest galaxy to the Milky Way?","andromeda"));
        myQuestions.add(new Questions("How many abstract methods can a functional interface have?","1"));
        myQuestions.add(new Questions("What is the name of the Egyptian god of the sun?","ra"));
    }
    @Override
    public void onInteraction(Entity entity) {

        int size = myQuestions.size();
        Random randomNum = new Random();
        int randomIndex = randomNum.nextInt(size);
        Questions obj= myQuestions.get(randomIndex);
        System.out.println(obj.getQuestion());
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        String expectedAnswer = obj.getAnswer();
        Pattern p = Pattern.compile(expectedAnswer, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        int tryTimes = 0;

        if (m.matches()) {
            XPComponent xpc = (XPComponent)entity.getComponent(XPComponent.class).orElseThrow();
            xpc.addXP(10);
        }

    }
}
