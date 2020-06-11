package devvy.me.minestrike.items;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class CustomItem implements Listener {

    protected ItemStack itemStack;
    protected CustomItemType customItemType;

    public CustomItem(CustomItemType customItemType) {
        this.customItemType = customItemType;
        setupItemStack();
    }

    public CustomItemType getCustomItemType() {
        return customItemType;
    }

    public ItemStack getItemStackCopy() {
        ItemStack item = itemStack.clone();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(CustomItemManager.CUSTOM_ITEM_KEY, PersistentDataType.INTEGER, customItemType.ordinal());
        item.setItemMeta(meta);
        return item;
    }

    public abstract void setupItemStack();

}
