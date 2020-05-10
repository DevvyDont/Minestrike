package devvy.me.minestrike.round;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BuyRound implements Round {

    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Buy Round...");

    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Buy Round...");

    }

    @Override
    public RoundType type() {
        return RoundType.BUY;
    }

    @Override
    public RoundType next() {
        return RoundType.ACTION;
    }
}
