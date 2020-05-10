package devvy.me.minestrike;

import devvy.me.minestrike.commands.AdminCommand;
import devvy.me.minestrike.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minestrike extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {

        gameManager = new GameManager();

        getCommand("admin").setExecutor(new AdminCommand(gameManager));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
