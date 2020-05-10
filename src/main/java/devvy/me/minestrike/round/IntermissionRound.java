package devvy.me.minestrike.round;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class IntermissionRound implements Round {

    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Intermission Round...");

    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Intermission Round...");

    }

    @Override
    public RoundType type() {
        return RoundType.INTERMISSION;
    }

    @Override
    public RoundType next() {
        return RoundType.BUY;
    }
}
