package devvy.me.minestrike.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BuyManager implements Listener {

    @EventHandler
    public void onEmeraldClick(InventoryClickEvent event) {

        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        // if they didnt click an item we dont care
        if (clickedItem == null)
            return;

        // DId they click an emerald?
        if (clickedItem.getType() != Material.EMERALD)
            return;

        BuyMenu menu = new BuyMenu();
        menu.openInventory(player);
        menu.InitializeBuyItems();

    }


}
