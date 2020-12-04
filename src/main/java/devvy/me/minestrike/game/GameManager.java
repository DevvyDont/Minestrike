package devvy.me.minestrike.game;


import devvy.me.minestrike.GUI.BuyMenu;
import devvy.me.minestrike.items.GlobalDamageManager;
import devvy.me.minestrike.scoreboard.Sidebar;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.player.PlayerManager;
import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.phase.PhaseManager;
import org.bukkit.Bukkit;
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
    private final BuyMenu buyMenu;
    private Sidebar scoreboardManager;

    private GameState state;

    private List<BombBlock> bombs;

    private int roundNumber = 1;

    public GameManager() {

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        phaseManager = new PhaseManager(plugin);
        teamManager = new TeamManager();
        playerManager = new PlayerManager(plugin);
        globalDamageManager = new GlobalDamageManager();
        state = GameState.WAITING;
        buyMenu = new BuyMenu();


        bombs = new ArrayList<>();
        // Construct the bomb objects
        bombs.add(new BombBlock(new Location(plugin.getGameWorld(), 26, 64, -18)));
        bombs.add(new BombBlock(new Location(plugin.getGameWorld(), -21, 64, -2)));
        // Generate them into default state
        bombs.forEach(BombBlock::generate);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(teamManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(globalDamageManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(buyMenu, plugin);
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

    public void setState(GameState state) {
        this.state = state;
    }

    public List<BombBlock> getBombs() {
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
        bombs.forEach(BombBlock::destroy);
    }


    /**************************************************************************************
    EVENTS TO HANDLE
     *************************************************************************************/

    @EventHandler
    public void handlePlayerJoined(PlayerJoinEvent event){


        // Handle what we should do for their team
        CSPlayer player = getPlayerManager().getCSPlayer(event.getPlayer());
        CSTeam team = teamManager.getPlayerTeam(player);
        if (team.getType() == TeamType.SPECTATORS) {

            team.removeMember(player);  // Remove them as a spectator

            if (teamManager.getDefenders().size() < teamManager.getAttackers().size()) {
                teamManager.getDefenders().addMember(player);
                System.out.println("ct" + teamManager.getDefenders());
            } else {
                teamManager.getAttackers().addMember(player);
                System.out.println("t " + teamManager.getAttackers());
            }
        }

    }

    @EventHandler
    public void onPlayerInteractedWithBomb(PlayerInteractEvent event) {

        // Only listen to right click block events
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (event.getClickedBlock() == null)
            return;

        for (BombBlock bombBlock : bombs) {

            if (bombBlock.getLocation().toBlockLocation().equals(event.getClickedBlock().getLocation().toBlockLocation())) {

                if (bombBlock.getState() == BombBlock.BombState.IDLE)
                    bombBlock.plant(event.getPlayer());
            }
        }

    }


}
