package devvy.me.minestrike.game;

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

    public int size(){
        return members.size();
    }
}
