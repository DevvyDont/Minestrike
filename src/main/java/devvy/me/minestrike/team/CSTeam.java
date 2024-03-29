package devvy.me.minestrike.team;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.player.CSPlayer;
import org.bukkit.Bukkit;
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
    private Vector defendersSpawnCoordinates = new Vector(0,64,-24);
    private Vector attackersSpawnCoordinates = new Vector(0,64,24);

    public CSTeam(TeamType type, String name) {
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        members = new ArrayList<>();

        this.type = type;
        this.name = name;
        roundsWon = 0;



        // todo make this not hard coded ish
        if (type == TeamType.ATTACKERS)
            this.spawn = new Spawn(new Location(plugin.getGameWorld(), attackersSpawnCoordinates.getX()+.5,attackersSpawnCoordinates.getY(), attackersSpawnCoordinates.getZ()+.5), this);
        else
            this.spawn = new Spawn(new Location(plugin.getGameWorld(), defendersSpawnCoordinates.getX()+.5,defendersSpawnCoordinates.getY(), defendersSpawnCoordinates.getZ()+.5), this);


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

        // update tab list to show clan tags properly
        updateTablist(player.getSpigotPlayer());
    }

    public void removeMember(CSPlayer player){
        members.remove(player);

        // update tab list to show clan tags properly
        updateTablist(player.getSpigotPlayer());
    }

    public void clearMembers(){
        members = new ArrayList<>();
        updateTablist();
    }

    public void updateTablist(Player p) {
            if (Minestrike.getPlugin(Minestrike.class).getGameManager() != null)
        Minestrike.getPlugin(Minestrike.class).getGameManager().getScoreboardManager().updatePlayerClanTag(p);
    }

    public void updateTablist() {
        if (Minestrike.getPlugin(Minestrike.class).getGameManager() != null)
            for (Player p : Bukkit.getOnlinePlayers())
                // update tab list to show clan tags properly
                Minestrike.getPlugin(Minestrike.class).getGameManager().getScoreboardManager().updatePlayerClanTag(p);
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
        addMoneyToAllMembers(amount, "");
    }

    public void addMoneyToAllMembers(int amount, String reason) {
        for (CSPlayer member : members)
            member.addMoney(amount, reason);
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
