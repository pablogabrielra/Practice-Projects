package modules.characters;

public class Enemy extends BaseTemplate {
    private float dmgMulti;
    public Enemy(String name, int str, int dex, int vit, int spd, float dmgMulti) {
        super(name, str, dex, vit, spd);
        this.dmgMulti = dmgMulti;
    }
    public float getDmgMulti() {
        return dmgMulti;
    }

}
