package devvy.me.minestrike;
import devvy.me.minestrike.commands.AdminCommand;
import devvy.me.minestrike.game.GameManager;
import devvy.me.minestrike.items.CustomItemManager;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minestrike extends JavaPlugin  {

    private GameManager gameManager;
    private CustomItemManager customItemManager;

    public GameManager getGameManager(){
        return gameManager;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }

    @Override
    public void onEnable() {

        initializeManagers();
        initializeCommands();

    }

    @Override
    public void onDisable() {
        gameManager.cleanup();
    }

    private void initializeManagers() {

        customItemManager = new CustomItemManager();
        gameManager = new GameManager();

        // Some scoreboard/teams stuff relies on the entire manager to be generated so this has to be outside the constructor
        gameManager.initializeTabList();

    }
    public World getGameWorld(){
        return getServer().getWorld("world");
    }

    private void initializeCommands() {

        // First init the admin command's executor and tab completer
        AdminCommand adminCommand = new AdminCommand(gameManager);

        try {
            getCommand("admin").setExecutor(adminCommand);
            getCommand("admin").setTabCompleter(adminCommand);
        } catch (NullPointerException e) {
            getLogger().severe("Command doesn't exist in plugin.yml! Aborting startup!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

    }
}
