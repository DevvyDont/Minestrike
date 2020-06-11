package devvy.me.minestrike;
import devvy.me.minestrike.commands.AdminCommand;
import devvy.me.minestrike.game.GameManager;
import devvy.me.minestrike.items.CustomItemManager;
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
        // Plugin shutdown logic
    }

    private void initializeManagers() {

        gameManager = new GameManager();
        customItemManager = new CustomItemManager();

        // Some scoreboard/teams stuff relies on the entire manager to be generated so this has to be outside the constructor
        gameManager.initializeTabList();

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
