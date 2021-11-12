package devvy.me.minestrike.game;


import devvy.me.minestrike.GUI.BuyManager;
import devvy.me.minestrike.GUI.BuyMenu;
import devvy.me.minestrike.items.GlobalDamageManager;
import devvy.me.minestrike.scoreboard.Sidebar;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.player.PlayerManager;
import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.phase.PhaseManager;
import devvy.me.minestrike.tasks.SaturationTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Listener {

    private final PhaseManager phaseManager;
    private final TeamManager teamManager;
    private final PlayerManager playerManager;
    private final GlobalDamageManager globalDamageManager;
    private Sidebar scoreboardManager;
    private BuyManager buyManager;

    private GameState state;

    private List<BombSite> bombs;

    private int roundNumber = 1;

    public GameManager() {

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        // keep everyone saturated
        new SaturationTask().runTaskTimer(plugin, 0, 10);

        // instantiate all of our managers and set initial states
        phaseManager = new PhaseManager(plugin);
        teamManager = new TeamManager();
        playerManager = new PlayerManager(plugin);
        globalDamageManager = new GlobalDamageManager();
        buyManager = new BuyManager();
        state = GameState.WAITING;

        // set up bombs
        bombs = new ArrayList<>();
        // Construct the bomb objects
        bombs.add(new BombSite(new Location(plugin.getGameWorld(), 26, 64, -18)));
        bombs.add(new BombSite(new Location(plugin.getGameWorld(), -21, 64, -2)));
        // Generate them into default state
        bombs.forEach(BombSite::generate);

        // register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(teamManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(globalDamageManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(buyManager, plugin);

        for (Player p : Bukkit.getOnlinePlayers())
            handleNewPlayer(p);
    }

    public void initializeTabList(){
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        scoreboardManager = new Sidebar();
        for(Player player: Bukkit.getOnlinePlayers()){
            scoreboardManager.updatePlayerClanTag(player);
        }

        plugin.getServer().getPluginManager().registerEvents(scoreboardManager, plugin);

    }


    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GlobalDamageManager getGlobalDamageManager() {
        return globalDamageManager;
    }

    public GameState getState() {
        return state;
    }

    public Sidebar getScoreboardManager() {
        return scoreboardManager;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public List<BombSite> getBombs() {
        return bombs;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void incrementRoundNumber(){
        roundNumber++;
    }

    public void startGame(){
        phaseManager.startPhaseLoop();
    }

    public void nextPhase(){
        phaseManager.nextPhase();
    }

    public void endGame(){
        phaseManager.endPhaseLoop();
    }

    public boolean isGameRunning(){
        return phaseManager.getCurrentPhase() != null;
    }

    public void cleanup() {
        bombs.forEach(BombSite::destroy);
    }

    public List<CSPlayer> getAllPlayers() {
        List<CSPlayer> ret = new ArrayList<>();
        ret.addAll(getTeamManager().getAttackers().getMembers());
        ret.addAll(getTeamManager().getDefenders().getMembers());
        return ret;
    }

    public void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(message);
    }

    public void handleNewPlayer(Player player) {
        // Handle what we should do for their team
        CSPlayer csPlayer = getPlayerManager().getCSPlayer(player);
        CSTeam team = teamManager.getPlayerTeam(csPlayer);
        if (team.getType() == TeamType.SPECTATORS) {

            team.removeMember(csPlayer);  // Remove them as a spectator

            if (teamManager.getDefenders().size() < teamManager.getAttackers().size()) {
                teamManager.getDefenders().addMember(csPlayer);
                System.out.println("ct" + teamManager.getDefenders());
            } else {
                teamManager.getAttackers().addMember(csPlayer);
                System.out.println("t " + teamManager.getAttackers());
            }
        }
    }


    /**************************************************************************************
    EVENTS TO HANDLE
     *************************************************************************************/

    @EventHandler
    public void handlePlayerJoined(PlayerJoinEvent event){
        handleNewPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteractedWithBomb(PlayerInteractEvent event) {

        // Only listen to right click block events
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (event.getClickedBlock() == null)
            return;

        for (BombSite bombSite : bombs) {

            if (bombSite.getLocation().toBlockLocation().equals(event.getClickedBlock().getLocation().toBlockLocation())) {

                if (bombSite.getState() == BombSite.BombState.IDLE)
                    bombSite.plant(event.getPlayer());
            }
        }

    }


}
