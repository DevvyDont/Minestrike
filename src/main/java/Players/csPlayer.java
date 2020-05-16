package Players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class csPlayer {
    private Player player;
    private int playerKills = 0;
    private int playerDeaths = 0;
    private int playerCurrency = 0;


    @EventHandler
    public void playerKill(PlayerDeathEvent event){
        if(event.getEntity().getKiller() != null){
            playerKills++;
            System.out.println("K/D: " + playerKills + " / " + playerDeaths);
        }


    }
    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        event.getEntity();
        playerDeaths++;
        System.out.println("K/D: " + playerKills + " / " + playerDeaths);


    }

    @EventHandler
    public void playerCurrency(){

    }
}
