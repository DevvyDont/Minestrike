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
    private Team spectators;

    public TeamManager() {

        counterTerror = new Team(TeamType.DEFENDERS, "Defenders");
        terrorists = new Team(TeamType.ATTACKERS, "Attackers");
        spectators = new Team(TeamType.SPECTATORS, "Spectators");

    }

    public Team getDefenders() {
        return counterTerror;
    }

    public Team getAttackers() {
        return terrorists;
    }

    public Team getPlayerTeam(Player player){
        if (counterTerror.hasMember(player))
            return counterTerror;
        else if (terrorists.hasMember(player))
            return terrorists;

        if (!spectators.hasMember(player))
            spectators.addMember(player);
        return spectators;

    }
}
