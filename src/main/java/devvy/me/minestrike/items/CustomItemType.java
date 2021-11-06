package devvy.me.minestrike.items;

import org.bukkit.Material;

public enum CustomItemType {

    SCOUT(CustomItemScout.class, Material.CROSSBOW, CustomItemCategory.RANGED,    71, 0),
    DEBUG_SWORD(CustomItemDebugSword.class, Material.GOLDEN_SWORD, CustomItemCategory.MELEE,    69, 0),
    BOMB(CustomItemBomb.class,   Material.TNT, CustomItemCategory.UTILITY,0,  0);

    public final Class<? extends CustomItem> CLAZZ;
    public final Material MATERIAL;
    public final CustomItemCategory CATEGORY;
    public final int DAMAGE;
    public final int DEFENSE;

    CustomItemType(Class<? extends CustomItem> clazz, Material material, CustomItemCategory category, int damage, int defense){
        this.CLAZZ = clazz;
        this.MATERIAL = material;
        this.CATEGORY = category;
        this.DAMAGE = damage;
        this.DEFENSE = defense;

    }

    public enum CustomItemCategory {

        MELEE,
        RANGED,
        ARMOR,
        UTILITY

    }

}
