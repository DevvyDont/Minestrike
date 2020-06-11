package devvy.me.minestrike.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemBomb extends CustomItem {

    public CustomItemBomb(CustomItemType customItemType) {
        super(customItemType);
    }

    @Override
    public void setupItemStack() {
        itemStack = new ItemStack(customItemType.MATERIAL);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "BOMB");
        itemStack.setItemMeta(meta);
    }
}
