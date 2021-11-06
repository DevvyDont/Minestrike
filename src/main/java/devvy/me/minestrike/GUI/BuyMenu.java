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
        if (clickedItem.getType().equals(Material.STONE)){
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
        assert clickedItem != null;
        if (clickedItem.getItemMeta().getDisplayName().equals("Cow Skin")){
            player.getInventory().addItem(new ItemStack(Material.LEATHER_CHESTPLATE));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Iron Man")){
            player.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Wifey")){
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Boomstick")){
            player.getInventory().addItem(new ItemStack(Material.STICK));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Stoner")){
            player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Knife")){
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Excalibur")){
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Beheader")){
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Bowner")){
            player.getInventory().addItem(new ItemStack(Material.BOW));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("AVP")){
            player.getInventory().addItem(new ItemStack(Material.CROSSBOW));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Frag")){
            player.getInventory().addItem(new ItemStack(Material.FIRE_CHARGE));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Flash")){
            player.getInventory().addItem(new ItemStack(Material.SPLASH_POTION));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Power I")){
            player.getInventory().addItem(new ItemStack(Material.ENCHANTED_BOOK));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Knockback II")){
            player.getInventory().addItem(new ItemStack(Material.ENCHANTED_BOOK));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Infinity")){
            player.getInventory().addItem(new ItemStack(Material.ENCHANTED_BOOK));
        }else if(clickedItem.getItemMeta().getDisplayName().equals("Fire Aspect II")){
            player.getInventory().addItem(new ItemStack(Material.ENCHANTED_BOOK));
        }



    }



}










