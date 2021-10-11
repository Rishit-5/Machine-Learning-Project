package MachineLearning;

public class Character {
    private double hp;
    private double strength;
    private String charName;

    public Character(String cn, double h, double s){
        charName = cn;
        hp = h;
        strength = s;
    }
    public String getCharName(){
        return charName;
    }
    //precondition:a variable that stores the health stat of a character
    //postcondition:the health of the character will be returned to the controller to be displayed
    public double getHp(){
        return hp;
    }

    public double getStrength(){
        return strength;
    }
}
