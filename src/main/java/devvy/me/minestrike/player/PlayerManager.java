package devvy.me.minestrike.player;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.round.RoundBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager implements Listener {


    private Minestrike plugin;
    private HashMap<UUID, CSPlayer> playerKDAttributesMap;

    public PlayerManager(Minestrike plugin){
        this.plugin = plugin;
        playerKDAttributesMap = new HashMap<>();
    }

    public CSPlayer getCSPlayer(Player player){
        CSPlayer ret = playerKDAttributesMap.get(player);
        if (ret != null)
            return ret;

        playerKDAttributesMap.put(player.getUniqueId(), new CSPlayer(player));
        return getCSPlayer(player);
    }

    public void resetPlayers(){
        for (CSPlayer p : playerKDAttributesMap.values())
            p.resetStats();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event){

        // Handle a new player right away
        CSPlayer csPlayer = playerKDAttributesMap.get(event.getPlayer().getUniqueId());

        // If its null this is a new player
        if (csPlayer == null){
            csPlayer = new CSPlayer(event.getPlayer());
            playerKDAttributesMap.put(event.getPlayer().getUniqueId(), csPlayer);
        } else {
            // Update the player to be the new instance of the player that joined
            csPlayer.setSpigotPlayer(event.getPlayer());
        }

    }


    @EventHandler
    public void playerKill(PlayerDeathEvent event){

        RoundBase round = plugin.getGameManager().getRoundManager().getCurrentRound();
        event.setCancelled(true);

        if (round == null)
            return;

        round.handlePlayerDeath(event.getEntity());

        if(event.getEntity().getKiller() != null){
            CSPlayer victim = getCSPlayer(event.getEntity());
            CSPlayer killer = getCSPlayer(event.getEntity().getKiller());
            victim.getKdTracker().increaseDeathCount();
            killer.getKdTracker().increaseKillCount();
            System.out.println("Killer K/D: " + killer.getKdTracker().getKDRation());
            System.out.println("Victim K/D: " + victim.getKdTracker().getKDRation());
        }
    }

}
