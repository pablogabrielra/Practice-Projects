package modules.movement;

public class MovementModule {
     public MovementModule(){
     }
     private int currentPosY, currentPosX;
     private String map [][];
     private String wall = "\nThere is a wall in front of you!\n";

     public String [][] mapNew(int maxX, int maxY, int currentPosX, int currentPosY, String map[][], int choice) {
        switch (choice) {
            case (1): //Move north
                if (currentPosY != 1) {
                    map[currentPosY][currentPosX] = " ";
                    --currentPosY;
                    this.currentPosY = currentPosY;
                    this.currentPosX = currentPosX;
                    map[currentPosY][currentPosX] = "H";
                } else {
                    System.out.println(wall);
                }
                break;
            case (2)://Move South
                if (currentPosY != maxY - 2) {
                    map[currentPosY][currentPosX] = " ";
                    ++currentPosY;
                    this.currentPosY = currentPosY;
                    this.currentPosX = currentPosX;
                    map[currentPosY][currentPosX] = "H";
                } else {
                    System.out.println(wall);
                }
                break;
            case (3)://Move East
                if (currentPosX != maxX - 2) {
                    map[currentPosY][currentPosX] = " ";
                    ++currentPosX;
                    this.currentPosX = currentPosX;
                    this.currentPosY = currentPosY;
                    map[currentPosY][currentPosX] = "H";
                } else {
                    System.out.println(wall);
                }
                break;
            case (4)://Move West
                if (currentPosX != 1) {
                    map[currentPosY][currentPosX] = " ";
                    --currentPosX;
                    this.currentPosX = currentPosX;
                    this.currentPosY = currentPosY;
                    map[currentPosY][currentPosX] = "H";
                } else {
                    System.out.println(wall);
                }
                break;
        }
        this.map = map;
        return this.map;
    }
    public int getCurrentPosY(){ return currentPosY;}
    public int getCurrentPosX(){ return currentPosX;}
}
