package devvy.me.minestrike.GUI;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.items.CustomItemScout;
import devvy.me.minestrike.items.CustomItemType;
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

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class BuyMenu implements Listener {
    private final Inventory inventory;
    Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

    private static HashMap<String, ItemStack> itemGuiMap = null;


    public BuyMenu() {
        inventory = Bukkit.createInventory(null, 9, "Buy Menu");

        if (itemGuiMap == null)
            initItemMap();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void initItemMap() {
        itemGuiMap = new HashMap()
        {{
            put("Cow Skin", new ItemStack(Material.LEATHER_CHESTPLATE));
            put("Iron Man", new ItemStack(Material.IRON_CHESTPLATE));
            put("Wifey", new ItemStack(Material.DIAMOND_CHESTPLATE));
            put("Boomstick", new ItemStack(Material.STICK));
            put("Stoner", new ItemStack(Material.STONE_SWORD));
            put("Knife", new ItemStack(Material.IRON_SWORD));
            put("Excalibur", new ItemStack(Material.DIAMOND_SWORD));
            put("Beheader", new ItemStack(Material.DIAMOND_AXE));

            put("Bowner", plugin.getCustomItemManager().getCustomItemStack(CustomItemType.SCOUT));
            put("AVP", plugin.getCustomItemManager().getCustomItemStack(CustomItemType.AVP));
            put("Frag", new ItemStack(Material.FIRE_CHARGE));
            put("Flash", new ItemStack(Material.SPLASH_POTION));
            put("Power I", new ItemStack(Material.ENCHANTED_BOOK));
            put("Knockback II", new ItemStack(Material.ENCHANTED_BOOK));
            put("Infinity", new ItemStack(Material.ENCHANTED_BOOK));
            put("Fire Aspect II", new ItemStack(Material.ENCHANTED_BOOK));
        }};
    }

    // Called when a player opens the initial gui
    public void InitializeBuyItems() {

        inventory.addItem(CreateGUIItem(Material.LEATHER_CHESTPLATE, "Armor", "Armor Sets"));
        inventory.addItem(CreateGUIItem(Material.STONE_SWORD, "Melee", "Close Combat"));
        inventory.addItem(CreateGUIItem(Material.BOW, "Range", "Rifles"));
        inventory.addItem(CreateGUIItem(Material.FIRE_CHARGE, "Equipment", "Utility"));
        inventory.addItem(CreateGUIItem(Material.EXPERIENCE_BOTTLE, "Enchantment", "Upgrades"));

    }

    // Called when a player opens the armor gui
    public void InitializeArmorItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.LEATHER_CHESTPLATE, "Cow Skin", "Leather Armor"));
        inventory.addItem(CreateGUIItem(Material.IRON_CHESTPLATE, "Iron Man", "Iron Armor"));
        inventory.addItem(CreateGUIItem(Material.DIAMOND_CHESTPLATE, "Wifey", "Diamond Armor"));
    }

    // Called when a player opens the melee gui
    public void InitializeCCItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.STICK, "Boomstick", "Watch your fall"));
        inventory.addItem(CreateGUIItem(Material.STONE_SWORD, "Stoner", "Stone Sword"));
        inventory.addItem(CreateGUIItem(Material.IRON_SWORD, "Knife", "Iron Sword"));
        inventory.addItem(CreateGUIItem(Material.DIAMOND_SWORD, "Excalibur", "Diamond Sword"));
        inventory.addItem(CreateGUIItem(Material.DIAMOND_AXE, "Beheader", "Diamond Axe"));
    }

    // Called when a player opens the ranged gui
    public void InitializeRangeItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.BOW, "Bowner", "Shoot"));
        inventory.addItem(CreateGUIItem(Material.CROSSBOW, "AVP", "Snipe"));

    }

    // Called when a player opens the equipment gui
    public void InitializeEquipmentItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.FIRE_CHARGE, "Frag", "Explosive"));
        inventory.addItem(CreateGUIItem(Material.POTION, "Flash", "Flashbang"));

    }

    // Called when a player opens the enchant gui
    public void InitializeEnchantmentItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.ENCHANTED_BOOK, "Power I", "Power"));
        inventory.addItem(CreateGUIItem(Material.ENCHANTED_BOOK, "Knockback II", "Iron Sword"));
        inventory.addItem(CreateGUIItem(Material.ENCHANTED_BOOK, "Infinity", "Keep Shooting"));
        inventory.addItem(CreateGUIItem(Material.ENCHANTED_BOOK, "Fire Aspect II", "Hell's Redeemer"));
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
    public void openSecondGUI(InventoryClickEvent event){

        if (event.getClickedInventory() != inventory)
            return;

        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        // if they didnt click an item we dont care
        if (clickedItem == null)
            return;

        if (clickedItem.getType().equals(Material.LEATHER_CHESTPLATE))
            InitializeArmorItems();
        else if(clickedItem.getType().equals(Material.STONE_SWORD))
            InitializeCCItems();
        else if(clickedItem.getType().equals(Material.BOW))
            InitializeRangeItems();
        else if(clickedItem.getType().equals(Material.FIRE_CHARGE))
            InitializeEquipmentItems();
        else if(clickedItem.getType().equals(Material.EXPERIENCE_BOTTLE))
            InitializeEnchantmentItems();

    }

    @EventHandler
    public void inventoryClicked(InventoryClickEvent event){
        if (event.getInventory() == inventory){
            event.setCancelled(true);
        }
    }
//    @EventHandler
//    public void inventoryDrag(InventoryDragEvent event){
//        if (event.getInventory() == inventory){
//            event.setCancelled(true);
//        }
//    }

    @EventHandler
    public void retrieveItems(InventoryClickEvent event){

        if (event.getClickedInventory() != inventory)
            return;

        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if(clickedItem == null)
            return;

        String itemName = clickedItem.getItemMeta().getDisplayName();

        if (itemGuiMap.containsKey(itemName)) {
            ItemStack mappedItem = itemGuiMap.get(itemName);
            player.getInventory().addItem(mappedItem);
        }


    }



}










