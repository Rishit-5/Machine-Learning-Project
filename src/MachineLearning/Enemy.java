package MachineLearning;

import java.util.ArrayList;

public class Enemy {
    private double hp;
    private double strength;
    private String enName;
    private ArrayList<Enemy> newEn = new ArrayList<>();

    //precondition: a blank array
    //postcondition: An array with 5 characters with their own individual statistics
    public Enemy(String en) {
        enName = en;
        hp = (int) (Math.random() * 40 + 30);
        strength = (int) (Math.random() * 8) + 4;
    }

    //precondition: learning mode has been clicked and computer has stored data on the player
    //postcondition: a new enemy will be created for the next round that will be suited to beat the user better
    public ArrayList<Enemy> Enemies2() {
        return newEn;
    }

    //precondition:a variable that stores the health stat of a character
    //postcondition:the health of the enemy will be returned to the controller to be displayed
    public double getHp() {
        return hp;
    }

    public double getStrength() {
        return strength;
    }

    public String getEnName() {
        return enName;
    }
}
