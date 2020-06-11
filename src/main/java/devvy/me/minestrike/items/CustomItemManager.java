package devvy.me.minestrike.items;

import devvy.me.minestrike.Minestrike;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CustomItemManager {

    public static final NamespacedKey CUSTOM_ITEM_KEY = new NamespacedKey(Minestrike.getPlugin(Minestrike.class), "customitem");

    private final Map<CustomItemType, CustomItem> customItemMap;

    public CustomItemManager() {

        customItemMap = new HashMap<>();

        for (CustomItemType type : CustomItemType.values()){

            // Attempt to create an instance of the custom item's class type, this shouldn't fail unless it's coded incorrectly
            try {
                customItemMap.put(type, type.CLAZZ.getDeclaredConstructor(CustomItemType.class).newInstance(type));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                Minestrike.getPlugin(Minestrike.class).getPluginLoader().disablePlugin(Minestrike.getPlugin(Minestrike.class));
                return;
            }

        }

        // Register all the events
        for (CustomItem cu : customItemMap.values())
            Minestrike.getPlugin(Minestrike.class).getServer().getPluginManager().registerEvents(cu, Minestrike.getPlugin(Minestrike.class));

    }

    public CustomItem getCustomItem(CustomItemType type) {
        return customItemMap.get(type);
    }

    /**
     * Returns a new item stack of a custom item
     *
     * @param type The custom item enum you want
     * @return The raw itemstack instance of the custom item
     */
    public ItemStack getCustomItemStack(CustomItemType type) {
        return getCustomItem(type).getItemStackCopy();
    }

    public boolean isCustomItem(ItemStack item) {
        return getCustomItemType(item) != null;
    }

    public boolean isCustomItemType(ItemStack item, CustomItemType type) {
        return getCustomItemType(item) == type;
    }

    public CustomItemType getCustomItemType(ItemStack item) {

        Integer itemID = item.getItemMeta().getPersistentDataContainer().get(CUSTOM_ITEM_KEY, PersistentDataType.INTEGER);
        if (itemID == null)
            return null;

        if (itemID >= 0 && itemID < CustomItemType.values().length)
            return CustomItemType.values()[itemID];

        Minestrike.getPlugin(Minestrike.class).getLogger().warning("Tried to find custom item type " + itemID + " but was not in bounds of CustomItemType enum");
        return null;
    }

}
