package modules.main;

import java.util.Scanner;
import modules.map.*;
import modules.movement.*;
import modules.combat.*;
import modules.characters.*;

public class Main {
    public static void main(String [] args) {
        boolean play = true;
        boolean error = true;
        int choice;
        int setSizeY = 0;
        int setSizeX = 0;

        Scanner action = new Scanner(System.in);
        while(error) {
            System.out.println("Set map height");
            try {
                setSizeX = action.nextInt();
                error = false;
            } catch (Exception e) {
                System.out.println("Not a valid input");
                action.next();
            }
        }
        error = true;

        while(error) {
            System.out.println("Set map length");
            try {
                setSizeY = action.nextInt();
                error = false;
            } catch (Exception e) {
                System.out.println("Not a valid input");
                action.next();
            }
        }

        MapCreation mapInit = new MapCreation(setSizeX, setSizeY);
        CombatModule combat = new CombatModule();
        MovementModule movement = new MovementModule();
        int maxX = mapInit.getMaxX();
        int maxY = mapInit.getMaxY();
        int currentPosY = Math.round(maxY/2);
        int currentPosX = Math.round(maxX/2);
        int exp = 0;
        String [][] map = mapInit.getMap();
        map[currentPosY][currentPosX] = "H";

        //CharacterCreation
        CharacterCreationModule cCreate = new CharacterCreationModule();
        Hero hero = new Hero(cCreate.getNameChar(),cCreate.getStr(),cCreate.getDex(), cCreate.getVit(), cCreate.getSpd());
        while (play) {
            //Action Lists
            MapOutput.checkMap(maxY, maxX, map);
            System.out.println("What action do you want to take?");
            System.out.println("1:Move North\n2:Move South\n3:Move East\n4:Move West\n5:Check Stats\n6:End Test");
            //Action Input
            choice = action.nextInt();
            switch (choice) {
                case (1):
                case (2):
                case (3):
                case (4):
                    map = movement.mapNew(maxX, maxY, currentPosX,currentPosY, map, choice);
                    currentPosY = movement.getCurrentPosY();
                    currentPosX = movement.getCurrentPosX();
                    if (Math.random() * 100 < 40) exp = combat.fight(hero);
                    if(exp == -1) play = false;
                    break;
                case (5):
                    System.out.println(hero.status());
                    break;
                case (6)://End Game
                    play = false;
                    break;
                default://Error Message
                    System.out.println("Not a valid Action!");
                    break;
            }
        }
        System.out.println("Thank You For Testing!");
    }
}
