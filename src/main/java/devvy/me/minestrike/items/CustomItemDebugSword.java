package devvy.me.minestrike.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomItemDebugSword extends CustomItem {


    public CustomItemDebugSword(CustomItemType customItemType) {
        super(customItemType);
    }

    @Override
    public void setupItemStack() {
        itemStack = new ItemStack(customItemType.MATERIAL);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "DEBUG SWORD");
        meta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "yes", "", ChatColor.GRAY + "Damage: " + ChatColor.RED + customItemType.DAMAGE));
        itemStack.setItemMeta(meta);
    }
}
