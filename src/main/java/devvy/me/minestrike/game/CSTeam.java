package devvy.me.minestrike.game;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.player.CSPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class CSTeam {

    private ArrayList<CSPlayer> members;
    private final TeamType type;
    private String name;
    private int roundsWon;
    private int lossStreak;
    private Spawn spawn;
    //DEBUG
    private Vector defendersSpawnCoordinates = new Vector(3,64,-23);
    private Vector attackersSpawnCoordinates = new Vector(3,64,23);

    public CSTeam(TeamType type, String name) {
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        members = new ArrayList<>();

        this.type = type;
        this.name = name;
        roundsWon = 0;



        //DEBUG
        if (type == TeamType.ATTACKERS){
            this.spawn = new Spawn(new Location(plugin.getGameWorld(), attackersSpawnCoordinates.getX(),attackersSpawnCoordinates.getY(), attackersSpawnCoordinates.getZ()), this);
            this.spawn.setModifier(-1);
        }else{
            this.spawn = new Spawn(new Location(plugin.getGameWorld(), defendersSpawnCoordinates.getX(),defendersSpawnCoordinates.getY(), defendersSpawnCoordinates.getZ()), this);
        }

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

    public int getLossStreak() {
        return lossStreak;
    }

    public void resetLossStreak(){
        lossStreak = 0;
    }

    public void incrimentLossStreak(){
        lossStreak++;

        //makes sure lossStreak doesnt get too high
        if (lossStreak > 5)
            lossStreak = 5;
    }

    public boolean hasMember(CSPlayer player){
        return members.contains(player);
    }

    public void addMember(CSPlayer player){
        if (!members.contains(player))
            members.add(player);
    }

    public void removeMember(CSPlayer player){
        members.remove(player);
    }

    public void clearMembers(){
        members = new ArrayList<>();
    }

    public Collection<CSPlayer> getMembers(){
        return members;
    }
    public CSPlayer getRandomMember() {
        if (members.isEmpty())
            return null;
        return members.get((int) (Math.random() * members.size()));
    }

    public int getNumMembersAlive(){
        int alive = 0;
        for (CSPlayer player : getMembers())
            if (player.isAlive())
                alive++;
        return alive;
    }

    public int getTeamTotalCurrency() {
        int economy = 0;
        for (CSPlayer player : getMembers())
            economy += player.getMoney();
        return economy;
    }

    public void addMoneyToAllMembers(int amount) {
        for (CSPlayer member : members)
            member.addMoney(amount);
    }

    public int size(){
        return members.size();
    }

    /**
     * Gets a string form of the member's on this team separated by commas
     * @return A string representation of this team
     */
    public String getMemberString(){

        if (getMembers().isEmpty())
            return "Empty!";

        StringBuilder sb = new StringBuilder();

        for (CSPlayer p : getMembers())
            sb.append(p.getSpigotPlayer().getName()).append(", ");

        return sb.substring(0, sb.length() - 2);
    }

    public Spawn getSpawn() {
        return spawn;
    }
}
