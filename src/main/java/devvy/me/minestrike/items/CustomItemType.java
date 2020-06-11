package devvy.me.minestrike.items;

import org.bukkit.Material;

public enum CustomItemType {

    SCOUT(CustomItemScout.class, Material.CROSSBOW, 71, 0),
    BOMB(CustomItemBomb.class, Material.ZOMBIE_HEAD, 0, 0);

    public final Class<? extends CustomItem> CLAZZ;
    public final Material MATERIAL;
    public final int DAMAGE;
    public final int DEFENSE;

    CustomItemType(Class<? extends CustomItem> clazz, Material material, int damage, int defense){
        this.CLAZZ = clazz;
        this.MATERIAL = material;
        this.DAMAGE = damage;
        this.DEFENSE = defense;

    }

}
