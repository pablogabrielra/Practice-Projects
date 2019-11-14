package modules.map;

public class MapCreation {
    private String map[][];
    private int maxY, maxX;
    //Based on the maxX and maxY we create the size of the play area.
    public MapCreation(int maxY, int maxX) {
        this.maxY = maxY;
        this.maxX = maxX;
        map = new String[maxY][maxX];
        //Here we place the boundaries, setting their values inside the map matrix.
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (i == 0 || i == maxY - 1 || j == 0 || j == maxX - 1) {
                    map[i][j] = "X";
                } else {
                    map[i][j] = " ";
                }
            }
        }
    }
    //Basic get methods to call our info after the basic map is created.
    public String [][] getMap() { return map;}
    public int getMaxX() { return maxX;}
    public int getMaxY() { return maxY;}
}
