package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import org.bukkit.entity.Player;

public abstract class RoundBase {

    protected Minestrike plugin;

    public RoundBase() {
        this.plugin = Minestrike.getPlugin(Minestrike.class);
    }

    public abstract void start();
    public abstract void end();
    public abstract void handlePlayerDeath(Player player);
    public abstract RoundType type();
    public abstract RoundType next();

}
