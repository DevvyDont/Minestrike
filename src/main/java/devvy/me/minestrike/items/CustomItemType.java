package devvy.me.minestrike.items;

import org.bukkit.Material;

public enum CustomItemType {

    SCOUT(CustomItemScout.class, Material.BOW, CustomItemCategory.RANGED,    71, 0, 0.19982229f * 1.5f),
    DEBUG_SWORD(CustomItemDebugSword.class, Material.GOLDEN_SWORD, CustomItemCategory.MELEE,    69, 0, 0.19982229f),
    BOMB(CustomItemBomb.class,   Material.TNT, CustomItemCategory.UTILITY,0,  0, 0.19982229f),
    AVP(CustomItem.class, Material.CROSSBOW, CustomItemCategory.RANGED, 99,0,0.19982229f * 0.2f);


    public final Class<? extends CustomItem> CLAZZ;
    public final Material MATERIAL;
    public final CustomItemCategory CATEGORY;
    public final int DAMAGE;
    public final int DEFENSE;
    public final float SPEED;

    CustomItemType(Class<? extends CustomItem> clazz, Material material, CustomItemCategory category, int damage, int defense, float speed){
        this.CLAZZ = clazz;
        this.MATERIAL = material;
        this.CATEGORY = category;
        this.DAMAGE = damage;
        this.DEFENSE = defense;
        this.SPEED = speed;

    }

    public enum CustomItemCategory {

        MELEE,
        RANGED,
        ARMOR,
        UTILITY

    }

}
