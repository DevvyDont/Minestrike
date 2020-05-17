package devvy.me.minestrike.commands;

import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.game.GameManager;
import devvy.me.minestrike.game.TeamType;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminCommand implements CommandExecutor, TabCompleter {

    private GameManager gameManager;

    public AdminCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] subs = {"start", "end", "next", "status", "swap", "spectate"};
        if (command.getName().equalsIgnoreCase("admin")){
            if (args.length == 1){
                ArrayList<String> options = new ArrayList<>();

                if (!args[0].equalsIgnoreCase("")){
                    for (String sub : subs) {
                        if (sub.startsWith(args[0]))
                            options.add(sub);
                    }
                    Collections.sort(options);
                    return options;

                } else
                    return Arrays.asList(subs);
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // We need at least one arg
        if (args.length < 1){
            sender.sendMessage(ChatColor.RED + "Please provide at least one argument.");
            return false;
        }

        String action = args[0].toUpperCase();

        // What should we do?
        switch (action){

            case "START":
                handleStartSubcommand(sender);
                break;

            case "END":
                handleEndSubcommand(sender);
                break;

            case "NEXT":
                handleNextSubcommand(sender);
                break;

            case "SWAP":

                if (sender instanceof Player)
                    handleSwapSubcommand((Player)sender);
                else
                    sender.sendMessage(ChatColor.RED + "Only players can do this!");
                break;

            case "SPECTATE":
                if (sender instanceof Player)
                    handleSpectateSubcommand((Player)sender);
                else
                    sender.sendMessage(ChatColor.RED + "Only players can do this!");
                break;

            case "STATUS":
                handleStatusSubcommand(sender);
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown argument " + ChatColor.DARK_RED + args[0]);
                return false;
        }

        return true;

    }

    private void handleSpectateSubcommand(Player player) {

        CSTeam currTeam = gameManager.getTeamManager().getPlayerTeam(player);

        if (currTeam.getType() == TeamType.SPECTATORS){
            player.sendMessage(ChatColor.RED + "You are already spectating!");
            return;
        }

        currTeam.removeMember(player);  // Remove them from whatever team they are on
        gameManager.getTeamManager().getSpectators().addMember(player);  // Add them to spectators
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.DARK_GRAY + "You are now a spectator");


    }

    private void handleSwapSubcommand(Player player) {

        CSTeam currTeam = gameManager.getTeamManager().getPlayerTeam(player);
        CSTeam t = gameManager.getTeamManager().getAttackers();
        CSTeam ct = gameManager.getTeamManager().getDefenders();

        // Remove them from their current team and set them to adv just in case
        currTeam.removeMember(player);
        player.setGameMode(GameMode.ADVENTURE);

        // If they are spectating or a ct, set them to be a t
        if (currTeam.getType() == TeamType.SPECTATORS || currTeam == ct){
            t.addMember(player);
            player.sendMessage(ChatColor.GOLD + "You are now a T");
        // Must be a t, make them a ct
        } else {
            ct.addMember(player);
            player.sendMessage(ChatColor.BLUE + "You are now a CT");
        }

    }

    private void handleStartSubcommand(CommandSender sender){

        // We can only start if there is no game
        if (gameManager.isGameRunning()){
            sender.sendMessage(ChatColor.RED + "There is already a game running!");
            return;
        }

        gameManager.startGame();
        sender.sendMessage(ChatColor.GREEN + "Starting a new game!!!");

    }

    private void handleEndSubcommand(CommandSender sender){

        // We can only end if there is a game
        if (!gameManager.isGameRunning()){
            sender.sendMessage(ChatColor.RED + "There is no game running!");
            return;
        }

        gameManager.endGame();
        sender.sendMessage(ChatColor.GREEN + "Ending the current game!!!");
    }

    private void handleNextSubcommand(CommandSender sender){

        // We can only go to next round if there is a game
        if (!gameManager.isGameRunning()){
            sender.sendMessage(ChatColor.RED + "There is no game running!");
            return;
        }

        gameManager.nextRound();
        sender.sendMessage(ChatColor.GREEN + "Going to the next round!!!");
    }

    private void handleStatusSubcommand(CommandSender sender){

        // Game running?
        if (gameManager.isGameRunning()){
            sender.sendMessage(ChatColor.GREEN + "There is currently a game running.");
            sender.sendMessage(ChatColor.GREEN + "Current Round: " + ChatColor.GRAY + gameManager.getRoundManager().getCurrentRound().type());
            sender.sendMessage(ChatColor.AQUA + "Defenders: " + gameManager.getTeamManager().getDefenders().getMemberString());
            sender.sendMessage(ChatColor.RED + "Attackers: " + gameManager.getTeamManager().getAttackers().getMemberString());
            sender.sendMessage(ChatColor.GRAY + "Spectators: " + gameManager.getTeamManager().getSpectators().getMemberString());
        } else {
            sender.sendMessage(ChatColor.RED + "There is not a game running.");
        }

    }






}
