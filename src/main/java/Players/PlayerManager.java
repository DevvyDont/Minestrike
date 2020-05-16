package Players;

import devvy.me.minestrike.Minestrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class PlayerManager implements Listener {
    private Minestrike plugin;
    private HashMap<Player, csPlayer> playerKDAttributesMap;


    public PlayerManager(Minestrike plugin){
        this.plugin = plugin;
        playerKDAttributesMap = new HashMap<>();
        for (Player player : plugin.getServer().getOnlinePlayers()){
            playerKDAttributesMap.put(player, new csPlayer(player));
        }
    }

    @EventHandler
    public void playerKill(PlayerDeathEvent event){
        if(event.getEntity().getKiller() != null){
            csPlayer victim = playerKDAttributesMap.get(event.getEntity());
            csPlayer killer = playerKDAttributesMap.get(event.getEntity().getKiller());
            victim.getKdTracker().increaseDeathCount();
            killer.getKdTracker().increaseKillCount();
            System.out.println("Killer K/D: " + killer.getKdTracker().getKDRation());
            System.out.println("Victim K/D: " + victim.getKdTracker().getKDRation());
        }
    }

}
