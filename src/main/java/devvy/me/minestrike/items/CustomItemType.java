package devvy.me.minestrike.items;

public enum CustomItemType {

    SCOUT(CustomItemScout.class, 71, 0);

    public final Class<? extends CustomItem> CLAZZ;
    public final int DAMAGE;
    public final int DEFENSE;

    CustomItemType(Class<? extends CustomItem> clazz, int damage, int defense){
        this.CLAZZ = clazz;
        this.DAMAGE = damage;
        this.DEFENSE = defense;

    }

}
