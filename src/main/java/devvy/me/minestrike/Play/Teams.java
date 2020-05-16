package devvy.me.minestrike.Play;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class Teams implements Listener {
    private ArrayList<Player> counterTerror = new ArrayList();
    private ArrayList<Player> terrorists = new ArrayList();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (terrorists.size() < counterTerror.size()){
            terrorists.add(player);
            System.out.println("t" + terrorists);
        }else if (counterTerror.size() < terrorists.size()){
            counterTerror.add(player);
            System.out.println("ct" + counterTerror);
        }else{
            terrorists.add(player);
            System.out.println("t" + terrorists);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (terrorists.contains(player)){
            terrorists.remove(player);
        }else counterTerror.remove(player);
    }
}
