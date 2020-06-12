package devvy.me.minestrike.round;

import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class IntermissionRound extends RoundBase {
    private ExperienceTimer timer;

    public IntermissionRound() {
        super();
    }


    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Intermission Round...");

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Intermission Round...");
        timer.endTimer();

    }

    @Override
    public void handlePlayerDeath(Player player) {
        Bukkit.getServer().broadcastMessage(player.getDisplayName() + " died!");
    }

    @Override
    public RoundType type() {
        return RoundType.INTERMISSION;
    }

    @Override
    public RoundType next() {
        return RoundType.BUY;
    }
    public ExperienceTimer getTimer() {
        return timer;
    }

}
