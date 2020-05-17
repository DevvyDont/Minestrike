package devvy.me.minestrike.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class CSTeam {

    private ArrayList<Player> members;
    private TeamType type;
    private String name;
    private int roundsWon;

    public CSTeam(TeamType type, String name) {

        members = new ArrayList<>();

        this.type = type;
        this.name = name;
        roundsWon = 0;

    }

    public TeamType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public void addRoundWin(){
        roundsWon++;
    }

    public boolean hasMember(Player player){
        return members.contains(player);
    }

    public void addMember(Player player){
        if (!members.contains(player))
            members.add(player);
    }

    public void removeMember(Player player){
        members.remove(player);
    }

    public void clearMembers(){
        members = new ArrayList<>();
    }

    public Collection<Player> getMembers(){
        return members;
    }

    public int getNumMembersAlive(){
        int alive = 0;
        for (Player player : getMembers())
            if (player.isValid() && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE))
                alive++;
        return alive;
    }

    public int size(){
        return members.size();
    }

    /**
     * Gets a string form of the member's on this team separated by commas
     * @return
     */
    public String getMemberString(){

        if (getMembers().isEmpty())
            return "Empty!";

        StringBuilder sb = new StringBuilder();

        for (Player p : getMembers())
            sb.append(p.getName() + ", ");

        return sb.substring(0, sb.length() - 2);
    }
}
