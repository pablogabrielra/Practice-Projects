package modules.characters;

import java.util.Scanner;

public class CharacterCreationModule {
    private Scanner action = new Scanner(System.in);
    private String nameChar;
    private String choice;
    private boolean reset = true;
    private int str, dex, vit, spd;
    StatInput statInput = new StatInput();

    public CharacterCreationModule() {
        choice = "n";
        while (choice.equals("n")) {
            System.out.println("Choose your Characters name.");
            nameChar = action.nextLine();
            reset = true;
            while (reset) {
                System.out.println("Are you ok with your choice? y/n ");
                choice = action.nextLine();
                if(choice.equals("n") || choice.equals("y")){
                    reset = false;
                } else {
                    System.out.print("Not a valid input!");
                }
            }

        }
        choice = "n";
        System.out.println("Welcome to the game " + nameChar + "!");
        System.out.println("Time to choose you characters stats!");
        System.out.println("Str determines how much damage you do. It also affect your Hit points to a lesser degree!");
        System.out.println("Dex determines how accurate you are. It also affect damage a bit");
        System.out.println("Vit determines how much Hit points you have");
        System.out.println("Spd determines how goes first and it also determines how good at dodging you are.");
        System.out.println("You have 42 Stat points to distribute and start with 5 of each stat. You cannot set more than 18 in one stat");
        while (choice.equals("n")) {
            reset = true;
            int remainingPoints = 42;
            str = statInput.statImport("str", remainingPoints, 0);
            remainingPoints = statInput.getNewRemainingPoints();
            dex = statInput.statImport("dex", remainingPoints, 0);
            remainingPoints = statInput.getNewRemainingPoints();
            vit = statInput.statImport("vit", remainingPoints, 0);
            remainingPoints = statInput.getNewRemainingPoints();
            spd = statInput.statImport("spd", remainingPoints, 1);
            while (reset) {
                System.out.println("Are you ok with your choices? y/n ");
                choice = action.nextLine();
                if(choice.equals("n") || choice.equals("y")){
                    reset = false;
                } else {
                    System.out.print("Not a valid input!");
                }
            }
        }
        System.out.println("This is your Character!\nName:" + nameChar +"\nStr: " + str + "\nDex: " + dex + "\nVit: "+ vit + "\nSpd: " + spd);
    }
    public int getStr () {
        return str;
    }
    public int getDex () {
        return dex;
    }
    public int getVit () {
        return vit;
    }
    public int getSpd () {
        return spd;
    }
    public String getNameChar () {
        return nameChar;
    }
}
