package devvy.me.minestrike.items;

import devvy.me.minestrike.Minestrike;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemScout extends CustomItem {

    public CustomItemScout(CustomItemType customItemType) {
        super(customItemType);
    }

    @Override
    public void setupItemStack() {
        this.itemStack = new ItemStack(customItemType.MATERIAL);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "SCOUT");
        itemStack.setItemMeta(meta);
    }

    @EventHandler(ignoreCancelled = true)
    public void onRightClickWithScout(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            if (event.getItem() != null && Minestrike.getPlugin(Minestrike.class).getCustomItemManager().isCustomItemType(event.getItem(), customItemType))
                event.getPlayer().sendMessage("scout");

    }
}
