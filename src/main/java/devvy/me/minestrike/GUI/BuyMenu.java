package devvy.me.minestrike.GUI;

import devvy.me.minestrike.Minestrike;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class BuyMenu implements Listener {
    private final Inventory inventory;
    Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

    public BuyMenu() {
        inventory = Bukkit.createInventory(null, 9, "BaseGUI");
        InitializeBuyItems();
        System.out.println("buy gui");

    }

    public void InitializeBuyItems() {

        inventory.addItem(CreateGUIItem(Material.LEATHER_CHESTPLATE, "Armor", "Armor Sets"));
        inventory.addItem(CreateGUIItem(Material.STONE_SWORD, "Melee", "Close Combat"));
        inventory.addItem(CreateGUIItem(Material.BOW, "Range", "Rifles"));
        inventory.addItem(CreateGUIItem(Material.FIRE_CHARGE, "Equipment", "Utility"));
        inventory.addItem(CreateGUIItem(Material.EXPERIENCE_BOTTLE, "Enchantment", "Upgrades"));
        System.out.println("initialized");

    }
    public ItemStack CreateGUIItem(Material material, String Title, String...lore){
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Title);
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void openInventory(Player player){
        player.openInventory(inventory);
    }

    @EventHandler
    public void openGUI(InventoryClickEvent event){


        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.STONE)){
            openInventory(player);
        }
    }

    @EventHandler
    public void inventoryClicked(InventoryClickEvent event){
        if (event.getInventory() == inventory){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void inventoryDrag(InventoryDragEvent event){
        if (event.getInventory() == inventory){
            event.setCancelled(true);
        }
    }



}










