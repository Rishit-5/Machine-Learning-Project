package MachineLearning;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {
    @FXML
    private Label lbl, lblGold, lblWin, lblLoss;
    @FXML
    private ListView lstv, lstStats;
    @FXML
    private ProgressBar pbar, pbar2;
    @FXML
    private TextArea txtA;
    private ArrayList<Enemy> newEn = new ArrayList<>();//arraylist stores the enemies
    private int gold = 0;//stores the amount of gold a user has
    private int whichlstv = 0;//indicates whether user is battling or upgrading stats
    private boolean inbat = false;//makes sure that the user is battling or not
    private int difficulty;//presents the difficulty level, 1 = random mode, 2 = hard mode, 3 = machine learning mode
    private int temp;//stores the list view index
    Battle b1 = new Battle();

    public Controller() {

    }

    //precondition: user has begun program
    //postcondition: it will set the difficulty to 1 and begin the program
    @FXML
    private void handleRand() {
        handlebtn3();
        difficulty = 1;
        newEn.clear();
        String[] enNames = {"Stitch", "Bob", "Buzz Light-year", "John Smith"};
        for (int i = 0; i < enNames.length; i++) {//will create four enemies for the user to face
            newEn.add(new Enemy(enNames[i]));
        }
        lbl.setText("Click See Enemy Stats");
    }
    //precondition: user has begun program
    //postcondition: it will set the difficulty to 2 and begin the program
    @FXML
    private void handleHard() {
        handlebtn3();
        difficulty = 2;
        newEn.clear();
        String[] enNames = {"Stitch", "Bob", "Buzz Light-year", "John Smith"};
        for (int i = 0; i < enNames.length; i++) {
            newEn.add(new Enemy(enNames[i]));
        }
        lbl.setText("Click See Enemy Stats");
    }
    //precondition: user has begun program
    //postcondition: it will set the difficulty to 3 and begin the program
    @FXML
    private void handleMach() {
        handlebtn3();
        difficulty = 3;
        newEn.clear();
        String[] enNames = {"Stitch", "Bob", "Buzz Light-year", "John Smith"};
        for (int i = 0; i < enNames.length; i++) {
            newEn.add(new Enemy(enNames[i]));
        }
        lbl.setText("Click See Enemy Stats");
    }

    @FXML
    //precondition: an array full of random stats
    //postcondition: ListView will display the stats that the user can upgrade, clicking on the item will upgrade automatically
    private void handlebtn1() {//see enemies button
        whichlstv = 1;
        lstv.getItems().clear();
        lstStats.getItems().clear();
        for (int i = 0; i < newEn.size(); i++) {//fills list view with enemies
            lstv.getItems().add(newEn.get(i).getEnName());
        }
        lbl.setText("Click on an enemy");
    }
    //precondition: user has opened the program
    //postcondition: A text area will be created containing a guide to all the moves
    @FXML
    private void handleGuide() {
        txtA.setText("Attack: Attacks an enemy with your strength. Block: Blocks the enemy move and causes the attacker to" +
                " take 5 damage if they attack. Break Block: If the enemy blocks, it will do twice the damage to the enemy, but if they " +
                "attack, then you will do damage to yourself on top of their damage to you.");
    }
    //precondition:ListView exists with values in it
    //postcondition: ListView will show stats of the enemies or upgrade the user's stats, based on what the listview contains
    @FXML
    private void handlelstv(MouseEvent event) {//if user clicks on listview
        if (lstv.getItems().size() > 0) {
            lstStats.getItems().clear();
            temp = lstv.getSelectionModel().getSelectedIndex();
            if (whichlstv == 1) {//if the listview shows enemies
                lbl.setText("Choose a move");
                if (!inbat) {//if not in battle
                    b1.setVals(newEn.get(temp));
                }
                lstStats.getItems().add("Health: " + newEn.get(temp).getHp());//adds health and strength to the second list view
                lstStats.getItems().add("Strength: " + newEn.get(temp).getStrength());
                pbar.setProgress(b1.getUsProg());//updates health
                pbar2.setProgress(b1.getEnProg());
//        ArrayList<String> tempString = new ArrayList<>();
//        tempString = r1.getFoodNames(tempHunger);
//        System.out.println(tempString);
////        System.out.println(lstCust.getSelectionModel().getSelectedItem());
////        System.out.println(lstCust.getSelectionModel().getSelectedIndex());
            } else if (whichlstv == 2) {//if the listview shows stats
                if (temp == 0) {
                    lstStats.getItems().add(b1.incStats(1).getHp());//increases health and strength
                } else if (temp == 1) {
                    lstStats.getItems().add(b1.incStats(2).getStrength());
                }
                lblGold.setText("Gold: " + b1.getGold());
            }
        }
    }
    //precondition: Data is stored
    //postcondition: Data is cleared to test the program again
    @FXML
    private void handlebtn3() {//reset button
        temp = 0;
        gold = 0;
        whichlstv = 0;
        lstv.getItems().clear();
        lstStats.getItems().clear();
        newEn.clear();
        lbl.setText("Click start and choose a mode");
        newEn.clear();
        b1.clear();
        inbat = false;
        difficulty = 0;
        pbar.setProgress(0);
        pbar2.setProgress(0);
        txtA.setText("");
        lblWin.setText("Wins: ");
        lblLoss.setText("Losses: ");

    }
    //precondition: User is not in the middle of a battle
    //postcondition: Will fill a list view with strength and health that can be clicked on to upgrade them by using an
    //upgrade stats method in the battle class
    @FXML
    private void handlebtn4() {//Upgrade Stats button
        if (!inbat) {
            whichlstv = 2;
            lstv.getItems().clear();
            lstStats.getItems().clear();
            lbl.setText("Choose a stat to upgrade, costs 10 gold");
            lblGold.setText("Gold: " + b1.getGold());
            lstv.getItems().add("Health");
            lstv.getItems().add("Strength");
        }

    }

    //precondition: Battle has begun and there's a character array with the character and an enemy array with all the enemies
    //postcondition: the user will attack the enemy by passing a parameter to the battle class indicating the difficulty and the move the user made
    // and also passing a parameter to a method that will do what all the moves do
    @FXML
    private void handleAt() {
        if (pbar.getProgress() > 0 && newEn.size() > 0) {//used to prevent extra gold being added for just spamming afterwards
            String temp12 = b1.doMove(1, difficulty);
            startNext(temp12);
        }
    }

    //precondition: Battle has begun and there's a character array with the character and an enemy array with all the enemies
    //postcondition: the user will not take damage from the enemy's attacks by passing a parameter to the battle class indicating the difficulty and the move the user made
    //    // and also passing a parameter to a method that will do what all the moves do
    @FXML
    private void handleBl() {
        if (pbar.getProgress() > 0 && newEn.size() > 0) {//used to prevent extra gold being added for just spamming afterwards
            String temp12 = b1.doMove(2, difficulty);
            startNext(temp12);
        }
    }

    //precondition: Battle has begun and there's a character array with the character and an enemy array with all the enemies
    //postcondition: a button will be clicked and pass a parameter to the battle class indicating that the user attempted to break a block(does twice the damage if the enemy blocks and does damage to yourself if they don't
    //by passing a parameter to the battle class indicating the difficulty and the move the user made
    // and also passing a parameter to a method that will do what all the moves do
    @FXML
    private void handleBB() {
        if (pbar.getProgress() > 0 && newEn.size() > 0) {//used to prevent extra gold being added for just spamming afterwards
            String temp12 = b1.doMove(3, difficulty);
            startNext(temp12);
        }
    }
    //precondition: a move has been clicked on
    //postcondition: Data from the battle class will be displayed
    private void startNext(String temp12) {
        if (lstv.getItems().size() > 0) {
            if (temp12.equalsIgnoreCase("You win")) {
                lstv.getItems().remove(temp);//removes that enemy from the listview so you don't have to fight them again
                lstStats.getItems().clear();
                b1.setVals(newEn.get(temp));//sets up next enemy
                lbl.setText("You beat " + newEn.get(temp).getEnName());
                newEn.remove(temp);
                if (lstv.getItems().size() == 0) {//if there are no more enemies
                    handlebtn3();
                    lbl.setText("You beat the game!");
                }

            } else if (temp12.equalsIgnoreCase("Enemy wins")) {
                b1.setVals(newEn.get(temp));
                lbl.setText("Enemy won, now upgrade stats");
                inbat = false;
            } else {
                txtA.setText(temp12);
                inbat = true;
            }
            if (newEn.size() > 0) {
                pbar.setProgress(b1.getUsProg());
                pbar2.setProgress(b1.getEnProg());
            }
            lblWin.setText("Wins: " + b1.getWins());
            lblLoss.setText("Losses: " + b1.getLosses());
        }
    }
}
