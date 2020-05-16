package devvy.me.minestrike.player;

import devvy.me.minestrike.Minestrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class PlayerManager implements Listener {


    private Minestrike plugin;
    private HashMap<Player, CSPlayer> playerKDAttributesMap;

    public PlayerManager(Minestrike plugin){
        this.plugin = plugin;
        playerKDAttributesMap = new HashMap<>();
    }

    public CSPlayer getCSPlayer(Player player){
        CSPlayer ret = playerKDAttributesMap.get(player);
        if (ret != null)
            return ret;

        playerKDAttributesMap.put(player, new CSPlayer(player));
        return getCSPlayer(player);
    }

    public void resetPlayers(){
        for (CSPlayer p : playerKDAttributesMap.values())
            p.resetStats();
    }


    @EventHandler
    public void playerKill(PlayerDeathEvent event){
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
