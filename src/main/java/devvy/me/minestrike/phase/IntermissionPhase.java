package devvy.me.minestrike.phase;

import devvy.me.minestrike.game.BombSite;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class IntermissionPhase extends PhaseBase {
    private ExperienceTimer timer;

    public IntermissionPhase() {
        super();
    }


    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Intermission Phase...");

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Intermission Phase...");
        timer.endTimer();

        plugin.getGameManager().getBombs().forEach(BombSite::hide);

    }

    @Override
    public void handlePlayerDeath(Player player) {
        Bukkit.getServer().broadcastMessage(player.getDisplayName() + " died!");
    }

    @Override
    public PhaseType type() {
        return PhaseType.INTERMISSION;
    }

    @Override
    public PhaseType next() {
        return PhaseType.BUY;
    }
    public ExperienceTimer getTimer() {
        return timer;
    }

}
