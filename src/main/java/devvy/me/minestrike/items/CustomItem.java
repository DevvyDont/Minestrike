package devvy.me.minestrike.items;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomItem implements Listener {

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

    public void setupItemStack(){

        String name = String.valueOf(customItemType);

        itemStack = new ItemStack(customItemType.MATERIAL);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);



    }

}
