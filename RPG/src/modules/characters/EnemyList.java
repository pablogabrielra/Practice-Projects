package modules.characters;

public class EnemyList {
    public EnemyList() {}
    private Enemy eList[] = new Enemy[7];
    private Enemy mSlime = new Enemy("Slime", 7, 7, 7, 7, 1f);
    private Enemy mOrc = new Enemy("Orc", 15, 9, 16, 7, 1.1f);
    private Enemy mOgre = new Enemy("Ogre", 14, 4, 25, 4, 1.4f);
    private Enemy mNinja = new Enemy("Ninja", 10, 20, 14, 25, 1.2f);
    private Enemy mCactus = new Enemy("Cactus", 1, 1, 35, 1, 1f);
    private Enemy mThanos = new Enemy("Thanos", 40, 30, 35, 33, 1.5f);
    private Enemy mOnePunchMan = new Enemy("One Punch Man", 9999, 9999, 9999, 2, 9999f);
    public Enemy[] geteList() {
        eList[0] = mSlime;
        eList[1] = mOrc;
        eList[2] = mOgre;
        eList[3] = mNinja;
        eList[4] = mCactus;
        eList[5] = mThanos;
        eList[6] = mOnePunchMan;
        return eList;
    }
}
