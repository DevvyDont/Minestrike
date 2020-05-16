package devvy.me.minestrike;
import devvy.me.minestrike.Play.KillDeath;
import devvy.me.minestrike.Play.TeamManager;
import devvy.me.minestrike.commands.AdminCommand;
import devvy.me.minestrike.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minestrike extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {

       this.getServer().getPluginManager().registerEvents(new TeamManager(), this);
       this.getServer().getPluginManager().registerEvents(new KillDeath(), this);
       //this.getServer().getPluginManager().registerEvents(new Scoreboard(), this);


        gameManager = new GameManager(this);

        AdminCommand adminCommand = new AdminCommand(gameManager);
        getCommand("admin").setExecutor(adminCommand);
        getCommand("admin").setTabCompleter(adminCommand);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
