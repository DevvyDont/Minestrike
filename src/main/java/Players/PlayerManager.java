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

    public csPlayer getCSPlayer(Player player){
        csPlayer ret = playerKDAttributesMap.get(player);
        if (ret != null)
            return ret;

        playerKDAttributesMap.put(player, new csPlayer(player));
        return getCSPlayer(player);
    }

    public void resetPlayers(){
        for (csPlayer p : playerKDAttributesMap.values())
            p.resetStats();
    }


    @EventHandler
    public void playerKill(PlayerDeathEvent event){
        if(event.getEntity().getKiller() != null){
            csPlayer victim = getCSPlayer(event.getEntity());
            csPlayer killer = getCSPlayer(event.getEntity().getKiller());
            victim.getKdTracker().increaseDeathCount();
            killer.getKdTracker().increaseKillCount();
            System.out.println("Killer K/D: " + killer.getKdTracker().getKDRation());
            System.out.println("Victim K/D: " + victim.getKdTracker().getKDRation());
        }
    }

}
