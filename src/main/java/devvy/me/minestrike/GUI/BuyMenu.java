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

    private HashMap<String, ItemStack> itemGuiMap = new HashMap();


    public BuyMenu() {
        inventory = Bukkit.createInventory(null, 9, "Buy Menu");
        InitializeBuyItems();
        System.out.println("buy gui");

        initItemMap();

    }

    public void initItemMap() {
        itemGuiMap.put("Cow Skin", new ItemStack(Material.LEATHER_CHESTPLATE));
        itemGuiMap.put("Iron Man", new ItemStack(Material.IRON_CHESTPLATE));
        itemGuiMap.put("Wifey", new ItemStack(Material.DIAMOND_CHESTPLATE));
        itemGuiMap.put("Boomstick", new ItemStack(Material.STICK));
        itemGuiMap.put("Stoner", new ItemStack(Material.STONE_SWORD));
        itemGuiMap.put("Knife", new ItemStack(Material.IRON_SWORD));
        itemGuiMap.put("Excalibur", new ItemStack(Material.DIAMOND_SWORD));
        itemGuiMap.put("Beheader", new ItemStack(Material.DIAMOND_AXE));

        itemGuiMap.put("Bowner", plugin.getCustomItemManager().getCustomItemStack(CustomItemType.SCOUT));
        itemGuiMap.put("AVP", plugin.getCustomItemManager().getCustomItemStack(CustomItemType.AVP));
        itemGuiMap.put("Frag", new ItemStack(Material.FIRE_CHARGE));
        itemGuiMap.put("Flash", new ItemStack(Material.SPLASH_POTION));
        itemGuiMap.put("Power I", new ItemStack(Material.ENCHANTED_BOOK));
        itemGuiMap.put("Knockback II", new ItemStack(Material.ENCHANTED_BOOK));
        itemGuiMap.put("Infinity", new ItemStack(Material.ENCHANTED_BOOK));
        itemGuiMap.put("Fire Aspect II", new ItemStack(Material.ENCHANTED_BOOK));
    }

    public void InitializeBuyItems() {

        inventory.addItem(CreateGUIItem(Material.LEATHER_CHESTPLATE, "Armor", "Armor Sets"));
        inventory.addItem(CreateGUIItem(Material.STONE_SWORD, "Melee", "Close Combat"));
        inventory.addItem(CreateGUIItem(Material.BOW, "Range", "Rifles"));
        inventory.addItem(CreateGUIItem(Material.FIRE_CHARGE, "Equipment", "Utility"));
        inventory.addItem(CreateGUIItem(Material.EXPERIENCE_BOTTLE, "Enchantment", "Upgrades"));
        System.out.println("initialized");

    }

    public void InitializeArmorItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.LEATHER_CHESTPLATE, "Cow Skin", "Leather Armor"));
        inventory.addItem(CreateGUIItem(Material.IRON_CHESTPLATE, "Iron Man", "Iron Armor"));
        inventory.addItem(CreateGUIItem(Material.DIAMOND_CHESTPLATE, "Wifey", "Diamond Armor"));
    }
    public void InitializeCCItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.STICK, "Boomstick", "Watch your fall"));
        inventory.addItem(CreateGUIItem(Material.STONE_SWORD, "Stoner", "Stone Sword"));
        inventory.addItem(CreateGUIItem(Material.IRON_SWORD, "Knife", "Iron Sword"));
        inventory.addItem(CreateGUIItem(Material.DIAMOND_SWORD, "Excalibur", "Diamond Sword"));
        inventory.addItem(CreateGUIItem(Material.DIAMOND_AXE, "Beheader", "Diamond Axe"));
    }
    public void InitializeRangeItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.BOW, "Bowner", "Shoot"));
        inventory.addItem(CreateGUIItem(Material.CROSSBOW, "AVP", "Snipe"));

    }

    public void InitializeEquipmentItems(){
        inventory.clear();
        inventory.addItem(CreateGUIItem(Material.FIRE_CHARGE, "Frag", "Explosive"));
        inventory.addItem(CreateGUIItem(Material.POTION, "Flash", "Flashbang"));

    }
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
    public void openGUI(InventoryClickEvent event){
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.EMERALD)){
            openInventory(player);
            inventory.clear();
            InitializeBuyItems();
        }
    }

    @EventHandler
    public void openSecondGUI(InventoryClickEvent event){
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        assert clickedItem != null;
        if (clickedItem.getType().equals(Material.LEATHER_CHESTPLATE)){
            InitializeArmorItems();
            openInventory(player);

        }else if(clickedItem.getType().equals(Material.STONE_SWORD)){
            InitializeCCItems();
            openInventory(player);

        }else if(clickedItem.getType().equals(Material.BOW)){
            InitializeRangeItems();
            openInventory(player);

        }else if(clickedItem.getType().equals(Material.FIRE_CHARGE)){
            InitializeEquipmentItems();
            openInventory(player);

        }else if(clickedItem.getType().equals(Material.EXPERIENCE_BOTTLE)){
            InitializeEnchantmentItems();
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

    @EventHandler
    public void retrieveItems(InventoryClickEvent event){
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();


        if(clickedItem == null){
            return;
        }

        String itemName = clickedItem.getItemMeta().getDisplayName();

        if (itemGuiMap.containsKey(itemName)) {
            ItemStack mappedItem = itemGuiMap.get(itemName);
            player.getInventory().addItem(mappedItem);
        }





    }



}










