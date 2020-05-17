package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class IntermissionRound extends RoundBase {

    public IntermissionRound(Minestrike plugin) {
        super(plugin);
    }

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
}
