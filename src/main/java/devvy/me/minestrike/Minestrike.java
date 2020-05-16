package devvy.me.minestrike;

import devvy.me.minestrike.Play.KillDeath;
import devvy.me.minestrike.Play.Scoreboard;
import devvy.me.minestrike.Play.Teams;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minestrike extends JavaPlugin {

    @Override
    public void onEnable() {
       this.getServer().getPluginManager().registerEvents(new Teams(), this);
       this.getServer().getPluginManager().registerEvents(new KillDeath(), this);
       //this.getServer().getPluginManager().registerEvents(new Scoreboard(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
