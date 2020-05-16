package devvy.me.minestrike.game;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class TeamManager implements Listener {

    private CSTeam counterTerror;
    private CSTeam terrorists;
    private CSTeam spectators;

    public TeamManager() {

        counterTerror = new CSTeam(TeamType.DEFENDERS, "Defenders");
        terrorists = new CSTeam(TeamType.ATTACKERS, "Attackers");
        spectators = new CSTeam(TeamType.SPECTATORS, "Spectators");

    }

    public CSTeam getDefenders() {
        return counterTerror;
    }

    public CSTeam getAttackers() {
        return terrorists;
    }

    public CSTeam getPlayerTeam(Player player){
        if (counterTerror.hasMember(player))
            return counterTerror;
        else if (terrorists.hasMember(player))
            return terrorists;

        if (!spectators.hasMember(player))
            spectators.addMember(player);
        return spectators;

    }
}
