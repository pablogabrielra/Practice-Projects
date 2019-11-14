package modules.characters;

public class BaseTemplate {
    private String name;
    private int str, dex, vit, spd;

    public BaseTemplate(String name, int str, int dex, int vit, int spd) {
        this.name = name;
        this.str = str;
        this.dex = dex;
        this.vit = vit;
        this.spd = spd;
    }
    public float criticalChance() {return (float) (1.1 + Math.floor(Math.random() * 2.5) - 0.0001) ; }
    public int getStr() { return str;}
    public int getDex() { return dex; }
    public int getVit() { return vit; }
    public int getSpd() { return spd; }
    public String getName() { return name;}

}


