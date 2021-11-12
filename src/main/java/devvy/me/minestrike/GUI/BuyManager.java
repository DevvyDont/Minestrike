package devvy.me.minestrike.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BuyManager implements Listener {

    public void openGUI(Player player) {
        BuyMenu menu = new BuyMenu();
        menu.openInventory(player);
        menu.InitializeBuyItems();
    }

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

        event.setCancelled(true);
        openGUI(player);

    }

    @EventHandler
    public void onEmeraldInteract(PlayerInteractEvent event) {

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR))
            return;

        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.EMERALD)
            return;

        openGUI(event.getPlayer());



    }

    @EventHandler
    public void onEmeraldThrow(PlayerDropItemEvent event) {

        if (event.getItemDrop().getItemStack().getType() == Material.EMERALD)
            event.setCancelled(true);

    }


}
