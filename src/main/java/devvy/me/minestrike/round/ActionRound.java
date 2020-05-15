package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionRound extends RoundBase {

    private ExperienceTimer timer;

    public ActionRound(Minestrike plugin) {
        super(plugin);
    }

    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Action Round...");

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Action Round...");

        timer.endTimer();

    }

    @Override
    public RoundType type() {
        return RoundType.ACTION;
    }

    @Override
    public RoundType next() {
        return RoundType.INTERMISSION;
    }
}
