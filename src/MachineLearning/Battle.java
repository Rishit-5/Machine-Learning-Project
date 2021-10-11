package MachineLearning;

import java.util.ArrayList;

public class Battle {
    private ArrayList<Character> newChar = new ArrayList<>();//stores the character data
    private ArrayList<Enemy> en = new ArrayList<>();//stores the enemy data
    private int gold = 0;//stores the amount of gold
    private double he = 50;//default health the user has
    private double x;// the health that the user has remaining after taking damage
    private double q;//the health the enemy has remaining after taking damage
    private int enmove;//stores the move the enemy makes
    private int prevmove;//stores the previous move that the user made
    private int[] moveamat = new int[3];//array that stores what move the user made after a previous move of attack
    private int[] moveambl = new int[3];//array that stores what move the user made after a previous move of block
    private int[] moveambb = new int[3];//array that stores what move the user made after a previous move of break block
    private int wins = 0;//stores the amount of wins the user has
    private int losses = 0;//stores the amount of losses the user has

    public Battle() {
        newChar.add(new Character("base", he, 8));
        he = newChar.get(0).getHp();//stores health in he so that health can be restored
        x = newChar.get(0).getHp();//user health
    }

//    public int charBat(Enemy bad) {//old method used for simulating a battle instead of user input
//        en.clear();
//        en.add(bad);
//        double q = en.get(0).getHp();//enemy health
//        double st = newChar.get(0).getStrength();//enemy strength
//        while (x >= 0 && q >= 0) {//does the battle to see who wins
//            q -= (newChar.get(0).getStrength());//subtract enemy health by user strength
//            x -= (en.get(0).getStrength());//subtract user health by enemy strength
//            newChar.add(new Character("base1", x, st));
//            newChar.remove(0);
//        }
//        if (x > q) {
//            gold += 20;
//            return 1;
//        } else {
//            gold += 40;
//            return 2;
//        }
//    }
    //precondition: there is a value of gold
    //postcondition: The value will be returned
    public int getGold() {
        return gold;
    }
    //precondition: user has gold and base statistics
    //postcondition: base statistics are increased and gold is subtracted
    public Character incStats(int upg) {
        x = he;//restores the user's health
        if (gold > 0) {
            double x = newChar.get(0).getHp();
            double y = newChar.get(0).getStrength();
            if (upg == 1) {//if user is upgrading health
                he += 2;
            } else {//if user is upgrading strength
                y += 0.40;
            }
            y *= 10;
            y = Math.floor(y);
            y /= 10;//rounds number so you don't get 9.000001 strength for example
            newChar.add(new Character("base", he, y));
            newChar.remove(0);
            gold -= 10;
        }
        if (gold < 0) {
            gold = 0;
        }
        return newChar.get(0);
    }
    //precondition: There is data stored
    //postcondition: that data is wiped and the program restarts
    public void clear() {
        newChar.clear();
        en.clear();
        gold = 0;
        he = 50;
        x = he;
        wins = 0;
        losses = 0;
        newChar.add(new Character("base", he, 8));
        enmove = 0;
        prevmove = 0;
        for (int i = 0; i < moveamat.length; i++) {//clears the array
            moveamat[i] = 0;
        }
        for (int i = 0; i < moveambl.length; i++) {
            moveambl[i] = 0;
        }
        for (int i = 0; i < moveambb.length; i++) {
            moveambb[i] = 0;
        }
    }
    //precondition: there is an enemy that was created
    //postcondition: a new enemy will be created for the user to fight
    public void setVals(Enemy bad) {
        en.clear();
        en.add(bad);//adds a new enemy into the en arraylist
        q = en.get(0).getHp();//enemy health
    }
    //precondition: the user has made a move and there is a difficulty
    //postcondition: the enemy will choose a move to make and it will affect the user's and/or the enemy's health
    public String doMove(int usmove, int diff) {
        enmove = 0;
        int rand;
        if (x > 0 && q > 0) {//if both the user and enemy health is greater than 0
            if (usmove == 1) {//if the user clicked attack
                if (diff == 1) {//random mode
                    enmove = ((int) (Math.random() * 3) + 1);//enemy decides what move it wants to do
                } else if (diff == 2) {//hard mode
                    rand = (((int) (Math.random() * 9)) + 1);//makes it so that there's a 30% chance of failure on hard mode
                    if (rand >= 8) {
                        enmove = ((int) (Math.random() * 3) + 1);//enemy decides what move it wants to do
                    } else {
                        enmove = 2;
                    }
                } else {//machine learning mode
                    if (prevmove == 1) {
                        enmove = findMax(moveamat) + 1;//this is since the number after a move is a counter towards that move
                        moveamat[0] += 1;

                    } else if (prevmove == 2) {
                        enmove = findMax(moveambl) + 1;//this is since the number after a move is a counter towards that move
                        moveambl[0] += 1;
                    } else {
                        enmove = findMax(moveambb) + 1;//this is since the number after a move is a counter towards that move
                        moveambb[0] += 1;
                    }
                    if (enmove >= 4) {//if counter is greater than 4 then it needs to restart to beginning
                        enmove = 1;
                    }
                    prevmove = usmove;
                }
                if (enmove == 1) {//cpu attacks
                    q -= (newChar.get(0).getStrength());//subtract enemy health by user strength
                    x -= (en.get(0).getStrength());//subtract user health by enemy strength
                    newChar.add(new Character("base1", x, newChar.get(0).getStrength()));//sets the character's health to the new damage that they've taken
                    newChar.remove(0);
                } else if (enmove == 2) {//cpu blocks
                    x -= 4;
                } else {//cpu breaks block
                    q -= (en.get(0).getStrength());
                    q -= (newChar.get(0).getStrength());//subtract user health by enemy strength
                }
            } else if (usmove == 2) {//if the user chose block
                if (diff == 1) {//random mode
                    enmove = ((int) (Math.random() * 3) + 1);//enemy decides what move it wants to do
                } else if (diff == 2) {//hard mode
                    rand = (((int) (Math.random() * 9)) + 1);//makes it so that there's a 40% chance of failure on hard mode
                    if (rand >= 8) {
                        enmove = ((int) (Math.random() * 3) + 1);//enemy decides what move it wants to do
                    } else {
                        enmove = 3;
                    }
                } else {//machine learning mode
                    if (prevmove == 1) {
                        enmove = findMax(moveamat) + 1;//this is since the number after a move is a counter towards that move
                        moveamat[1] += 1;
                    } else if (prevmove == 2) {
                        enmove = findMax(moveambl) + 1;//this is since the number after a move is a counter towards that move
                        moveambl[1] += 1;
                    } else {
                        enmove = findMax(moveambb) + 1;//this is since the number after a move is a counter towards that move
                        moveambb[1] += 1;
                    }
                    if (enmove >= 4) {
                        enmove = 1;
                    }
                    prevmove = usmove;
                }
                if (enmove == 1) {//if enemy chooses attack
                    q -= 4;
                } else if (enmove == 2) {//if enemy chooses block, nothing happens

                } else {//if enemy chooses break block
                    x -= ((en.get(0).getStrength()) * 2);//subtract user health by enemy strength, but twice since they broke the block
                    newChar.add(new Character("base1", x, newChar.get(0).getStrength()));//sets the character's health to the new damage that they've taken
                    newChar.remove(0);

                }
            } else {//if the user clicks break block
                if (diff == 1) {//random mode
                    enmove = ((int) (Math.random() * 3) + 1);//enemy decides what move it wants to do
                } else if (diff == 2) {//hard mode
                    rand = (((int) (Math.random() * 9)) + 1);//makes it so that there's a 40% chance of failure on hard mode
                    if (rand >= 8) {//machine learning mode
                        enmove = ((int) (Math.random() * 3) + 1);//enemy decides what move it wants to do
                    } else {
                        enmove = 1;
                    }
                } else {
                    if (prevmove == 1) {
                        enmove = findMax(moveamat) + 1;//this is since the number after a move is a counter towards that move
                        moveamat[2] += 1;
                    } else if (prevmove == 2) {
                        enmove = findMax(moveambl) + 1;//this is since the number after a move is a counter towards that move
                        moveambl[2] += 1;
                    } else {
                        enmove = findMax(moveambb) + 1;//this is since the number after a move is a counter towards that move
                        moveambb[2] += 1;
                    }
                    if (enmove >= 4) {
                        enmove = 1;
                    }
                    prevmove = usmove;
                }
                if (enmove == 1) {
                    x -= (newChar.get(0).getStrength());
                    x -= ((en.get(0).getStrength()));//subtract user health by enemy strength
                    newChar.add(new Character("base1", x, newChar.get(0).getStrength()));//sets the character's health to the new damage that they've taken
                    newChar.remove(0);
                } else if (enmove == 2) {//if enemy chooses block
                    q -= ((newChar.get(0).getStrength()) * 2);
                } else {//if enemy chooses break block, nothing happens

                }
            }
            if (x > 0 && q > 0) {//returns what move is made if both the user and enemy is alive
                if (enmove == 1) {
                    return "Enemy attacked";
                } else if (enmove == 2) {
                    return "Enemy blocked";
                } else {
                    return "Enemy attempted to break a block";
                }
            }
        }
        if (q > 0) {//if enemy still has health remaining
            losses += 1;
            gold += 40;
            return "Enemy wins";
        } else {
            wins += 1;
            gold += 20;
            return "You win";
        }
    }
    //precondition: A difference to the user health has been made
    //postcondition: the ratio of health to max health will be returned
    public double getUsProg() {
        return (x / he);
    }
    //precondition: A difference to the enemy health has been made
    //postcondition: the ratio of health to max health will be returned
    public double getEnProg() {
        return (q / (en.get(0).getHp()));
    }

    public int getEnMove() {
        return enmove;
    }
    //precondition: an array with the most made moves is created
    //postcondition: The method will return the value that is most often done after a certain move
    public int findMax(int[] mov) {
        int max = 0;
        int i;
        for (i = 0; i < mov.length; i++) {
            if (mov[i] > max) {
                max = i;
            }
        }
        return max + 1;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }


}
