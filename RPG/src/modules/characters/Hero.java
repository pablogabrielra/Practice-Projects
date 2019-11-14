package modules.characters;

public class Hero extends BaseTemplate{
    public Hero (String name, int str, int dex, int vit, int spd){ super(name, str, dex, vit, spd);}
    public int runChance(int monstersSpd) { return (int)Math.floor((super.getSpd() / monstersSpd) * 15);}
    public float getWeapon() { return 1.3f;}
    public String status () {return "Hero Name: " + super.getName() +"\nStr: " + super.getStr() + "\nDex: " + super.getDex() + "\nVit: "+ super.getVit() + "\nSpd: " + super.getSpd(); }
}
