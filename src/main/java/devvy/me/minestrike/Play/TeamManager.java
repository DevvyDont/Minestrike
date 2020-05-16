package devvy.me.minestrike.Play;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class TeamManager implements Listener {

    private Team counterTerror;
    private Team terrorists;

    public TeamManager() {

        counterTerror = new Team("Defenders");
        terrorists = new Team("Attackers");

    }

    public Team getDefenders() {
        return counterTerror;
    }

    public Team getAttackers() {
        return terrorists;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (terrorists.size() < counterTerror.size()){
            terrorists.addMember(player);
            System.out.println("t" + terrorists);
        }else if (counterTerror.size() < terrorists.size()){
            counterTerror.addMember(player);
            System.out.println("ct" + counterTerror);
        }else{
            terrorists.addMember(player);
            System.out.println("t" + terrorists);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (terrorists.hasMember(player)){
            terrorists.removeMember(player);
        }else counterTerror.removeMember(player);
    }
}
