package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;

public abstract class RoundBase {

    protected Minestrike plugin;

    public RoundBase(Minestrike plugin) {
        this.plugin = plugin;
    }

    public abstract void start();
    public abstract void end();
    public abstract RoundType type();
    public abstract RoundType next();

}
