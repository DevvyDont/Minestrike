package devvy.me.minestrike.round;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionRound implements Round {

    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Action Round...");

    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Action Round...");

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
