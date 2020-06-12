package devvy.me.minestrike.phase;

import devvy.me.minestrike.Minestrike;
import org.bukkit.entity.Player;

public abstract class PhaseBase {

    protected Minestrike plugin;

    public PhaseBase() {
        this.plugin = Minestrike.getPlugin(Minestrike.class);
    }

    public abstract void start();
    public abstract void end();
    public abstract void handlePlayerDeath(Player player);
    public abstract PhaseType type();
    public abstract PhaseType next();

}
