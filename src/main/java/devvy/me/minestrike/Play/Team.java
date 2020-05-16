package devvy.me.minestrike.Play;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class Team {

    private ArrayList<Player> members;
    private String name;
    private int roundsWon;

    public Team(String name) {

        members = new ArrayList<>();
        this.name = name;
        roundsWon = 0;

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
