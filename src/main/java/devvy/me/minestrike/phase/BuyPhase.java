package devvy.me.minestrike.phase;

import devvy.me.minestrike.game.BombBlock;
import devvy.me.minestrike.game.GameState;
import devvy.me.minestrike.items.CustomItemType;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class BuyPhase extends PhaseBase {

    private ExperienceTimer timer;

    public BuyPhase() {
        super();
    }

    @Override
    public void start() {
        plugin.getGameManager().getTeamManager().getAttackers().getSpawn().teleportMembersToSpawnPoints();
        plugin.getGameManager().getTeamManager().getDefenders().getSpawn().teleportMembersToSpawnPoints();


        plugin.getGameManager().setState(GameState.BUY_IN_PROGRESS);

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Buy Phase...");

        for (CSPlayer p : plugin.getGameManager().getTeamManager().getAttackers().getMembers()) {
            p.getSpigotPlayer().setGameMode(GameMode.ADVENTURE);
            // Remove any bombs from the t's
            p.getSpigotPlayer().getInventory().remove(plugin.getCustomItemManager().getCustomItemStack(CustomItemType.BOMB));
        }

        for (CSPlayer p : plugin.getGameManager().getTeamManager().getDefenders().getMembers())
            p.getSpigotPlayer().setGameMode(GameMode.ADVENTURE);

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();

        CSPlayer randomAttacker = plugin.getGameManager().getTeamManager().getAttackers().getRandomMember();
        if (randomAttacker != null)
            randomAttacker.getSpigotPlayer().getInventory().addItem(plugin.getCustomItemManager().getCustomItemStack(CustomItemType.BOMB));

        plugin.getGameManager().getBombs().forEach(BombBlock::reset);
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Buy Phase...");

        timer.endTimer();

    }

    @Override
    public void handlePlayerDeath(Player player) {
        plugin.getLogger().warning(player.getName() + " died during the buy phase. This shouldn't happen.");
    }

    @Override
    public PhaseType type() {
        return PhaseType.BUY;
    }

    @Override
    public PhaseType next() {
        return PhaseType.ACTION;
    }
    public ExperienceTimer getTimer() {
        return timer;
    }

}
