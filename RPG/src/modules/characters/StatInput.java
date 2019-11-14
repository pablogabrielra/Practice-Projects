package modules.characters;

import java.util.Scanner;

public class StatInput {
    public StatInput() {}
    private Scanner action = new Scanner(System.in);
    private int statInput, statOutput, newRemainingPoints;
    private boolean error;

    public int statImport(String stat, int remainingPoints, int lastStatCheck) {
        String statChoice = "n";
        while (statChoice.equals("n")) {
            error = true;
            while (error) {
                try {
                    newRemainingPoints = 0;
                    statOutput = 5;
                    System.out.println("You have " + remainingPoints + " left.");
                    System.out.println("Please set " + stat);
                    statInput = action.nextInt();
                    if (statInput < remainingPoints && lastStatCheck == 1) {
                        System.out.println("You have extra points, so the left over points were added to spd!");
                        statOutput += remainingPoints;
                        statChoice = "y";
                        error = false;
                    } else if (statInput <= 18 && statInput <= remainingPoints) {
                        statOutput += statInput;
                        newRemainingPoints = remainingPoints - statInput;
                        statChoice = "y";
                        error = false;
                    } else {
                        System.out.println("You can't set more than 18 points in one stat!");
                    }
                } catch (Exception e) {
                        System.out.println("Not a valid input");
                        action.next();
                }
            }
        }
    return statOutput;
    }
    public int getNewRemainingPoints() {
        return newRemainingPoints;
    }
}
