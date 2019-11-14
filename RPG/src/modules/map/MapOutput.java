package modules.map;


public class MapOutput {
        //Based on the size of the matrix, we add and space the string values inside a String mapOutput, using the break function to be able to stack lines and columns.
    public static void checkMap(int maxY, int maxX, String [][] map) {
        String mapOutput = "";
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                mapOutput += "  " + map[i][j];
            }
            mapOutput += "\n";
        }
        System.out.println(mapOutput);
    }
}
