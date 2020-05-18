package devvy.me.minestrike.game;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.player.CSPlayer;
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

    public CSTeam getSpectators() {
        return spectators;
    }

    /**
     * If you pass in defenders, return attackers, and vice versa
     *
     * @param team The opposite of the team you want
     * @return The opposite team
     */
    public CSTeam getOppositeTeam(CSTeam team){
        if (getDefenders() == team)
            return getAttackers();
        else if (getAttackers() == team)
            return getDefenders();
        throw new IllegalArgumentException("Invalid team passed in of type " + team.getType() + "! Must be attackers or defenders.");
    }

    public CSTeam getPlayerTeam(CSPlayer player){

        if (counterTerror.hasMember(player))
            return counterTerror;
        else if (terrorists.hasMember(player))
            return terrorists;

        if (!spectators.hasMember(player))
            spectators.addMember(player);
        return spectators;

    }
}
