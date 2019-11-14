package modules.combat;

import java.util.Scanner;
import modules.characters.*;

public class CombatModule {
    public CombatModule() {}
    private int choice;
    private EnemyList enemyList = new EnemyList();
    private Enemy enemies[] = enemyList.geteList();
    private Scanner action = new Scanner(System.in);

    public int fight(Hero hero) {
        int randomEnemy = (int) (Math.floor(Math.random() * 7 - 0.01));
        int charHp = hero.getVit() * 12 + hero.getStr();
        int maxCharHp = charHp;
        int monsterHp = enemies[randomEnemy].getVit() * 12 + enemies[randomEnemy].getStr();
        int maxMonsterHp = monsterHp;
        int charDamage = Math.round(hero.getStr() * hero.getWeapon() + hero.getDex());
        int monsterDamage = Math.round(enemies[randomEnemy].getStr() * enemies[randomEnemy].getDmgMulti() + enemies[randomEnemy].getDex());
        float hitChanceChar = enemies[randomEnemy].getSpd() / hero.getDex() * 15;
        float hitChanceMonster = hero.getSpd() / enemies[randomEnemy].getDex() * 15;
        float critChanceDmg;
        boolean yourTurn = true;
        boolean continueBattle = true;
        int monstersChoice;
        int exp = 123;

        System.out.println("You have encountered a wild " + enemies[randomEnemy].getName() + "!");
        if (hero.getSpd() > enemies[randomEnemy].getSpd()){
            yourTurn = true;
        } else {
            yourTurn = false;
            System.out.println("The " + enemies[randomEnemy].getName() + " caught you off guard! \n");
        }
        while (monsterHp >= 0 && continueBattle) {
            while (yourTurn) {
                System.out.println(hero.getName() + " Hp: " + charHp + "/" + maxCharHp);
                System.out.println("What do you want to do?\n1:Attack\n2:RUN!");
                choice = action.nextInt();
                switch (choice) {
                    case (1):
                        if(Math.random() * 100 > hitChanceChar) {
                            if (Math.random() * 100 < 20) {
                                critChanceDmg = hero.criticalChance();
                                System.out.println("You hit a critical!");
                            } else {
                                critChanceDmg = 1;
                            }
                            System.out.println("You dealt " + (int) (Math.floor(charDamage * critChanceDmg)) + " damage!");
                            monsterHp -= (int) (Math.floor(charDamage * critChanceDmg));
                            System.out.println("Monster recoiled in fear!\n");
                        } else {
                            System.out.println("You missed your attack!");
                        }
                        yourTurn = false;
                        break;
                    case (2):
                        if (Math.random() * 100 < hero.runChance(enemies[randomEnemy].getSpd())) {
                            continueBattle = false;
                        } else {
                            System.out.println("You couldn't run away! ");
                        }
                        yourTurn = false;
                        break;
                    default:
                        System.out.println("Not a valid input!");
                }
            }
            while (continueBattle && !yourTurn && monsterHp > 0) {
                System.out.println("Monsters turn to fight!");
                monstersChoice = (int) Math.floor(Math.random() * 6);
                switch (monstersChoice) {
                        case (0):
                        case (1):
                        case (2):
                        case (3):
                        case (4):
                            if (Math.random() * 100 > hitChanceMonster) {
                                if (Math.random() * 100 < 20) {
                                    critChanceDmg = enemies[randomEnemy].criticalChance();
                                    System.out.println("The " + enemies[randomEnemy].getName() + " hit a critical!");
                                } else {
                                    critChanceDmg = 1;
                                }
                                System.out.println("The " + enemies[randomEnemy].getName() + " dealt " + (int) (Math.floor(monsterDamage * critChanceDmg)) + " damage to you!\n");
                                charHp -= (int) (Math.floor(monsterDamage * critChanceDmg));
                                if (charHp < 0) continueBattle = false;
                            } else {
                                System.out.println("You dodged the attack!\n");
                            }
                            yourTurn = true;
                            break;
                        case (5):
                            if ((float)(monsterHp/maxMonsterHp) < 0.3) {
                                System.out.println("The " + enemies[randomEnemy].getName() + " drank a potion and healed itself!\n");
                                monsterHp += (int)(Math.floor(maxMonsterHp / 4));
                                yourTurn = true;
                            } else {
                                System.out.println("Monster stares at you!\n");
                                yourTurn = true;
                            }
                            break;
                        default:
                            System.out.println("Monster stares at you!\n");
                            yourTurn = true;
                            break;
                    }
            }
        }
        if (continueBattle) {
            System.out.println("You killed the monster!");
            System.out.println("You gained " + exp + " exp!\n");
            return exp;
        } else if (charHp < 0) {
            System.out.println("You died!\n GAME OVER!");
            return -1;
        } else {
            System.out.println("You were able to run away!");
            System.out.println("You gained no exp!\n");
            return 0;
        }
    }
}

