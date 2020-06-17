package devvy.me.minestrike.commands;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.game.GameManager;
import devvy.me.minestrike.game.TeamType;
import devvy.me.minestrike.items.CustomItem;
import devvy.me.minestrike.items.CustomItemType;
import devvy.me.minestrike.player.CSPlayer;
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

    private final GameManager gameManager;

    public AdminCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        String[] subs = {"start", "end", "next", "status", "swap", "spectate", "give"};

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

            } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {

                ArrayList<String> options = new ArrayList<>();

                if (args[1].equalsIgnoreCase("")) {
                    for (CustomItemType type : CustomItemType.values())
                        options.add(type.toString());
                    return options;
                }

                for (CustomItemType type : CustomItemType.values()) {
                    if (type.toString().startsWith(args[1]))
                        options.add(type.toString());
                }

                Collections.sort(options);
                return options;
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

            case "GIVE":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Please provide what item you would like to give yourself!");
                    return true;
                }

                if (!(sender instanceof Player)){
                    sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                    return true;
                }

                String itemName = args[1].toUpperCase();
                handleGiveSubcommand((Player)sender, itemName);
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown argument " + ChatColor.DARK_RED + args[0]);
                return false;
        }

        return true;

    }

    private void handleGiveSubcommand(Player player, String itemName) {


        CustomItemType type;

        try {
            type = CustomItemType.valueOf(itemName.toUpperCase().replace(" ", ""));
        } catch (IllegalArgumentException ignored) {
            player.sendMessage(ChatColor.RED + "Invalid custom item type! Please try again!");
            return;
        }

        player.getInventory().addItem(Minestrike.getPlugin(Minestrike.class).getCustomItemManager().getCustomItemStack(type));
        player.sendMessage(ChatColor.GREEN + "Gave you the custom item: " + ChatColor.AQUA + type + ChatColor.GREEN + "!");

    }

    private void handleSpectateSubcommand(Player player) {

        CSPlayer csPlayer = gameManager.getPlayerManager().getCSPlayer(player);
        CSTeam currTeam = gameManager.getTeamManager().getPlayerTeam(csPlayer);

        if (currTeam.getType() == TeamType.SPECTATORS){
            player.sendMessage(ChatColor.RED + "You are already spectating!");
            return;
        }

        currTeam.removeMember(csPlayer);  // Remove them from whatever team they are on
        gameManager.getTeamManager().getSpectators().addMember(csPlayer);  // Add them to spectators
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.DARK_GRAY + "You are now a spectator");


    }

    private void handleSwapSubcommand(Player player) {

        CSPlayer csPlayer = gameManager.getPlayerManager().getCSPlayer(player);

        CSTeam currTeam = gameManager.getTeamManager().getPlayerTeam(csPlayer);
        CSTeam t = gameManager.getTeamManager().getAttackers();
        CSTeam ct = gameManager.getTeamManager().getDefenders();

        // Remove them from their current team and set them to adv just in case
        currTeam.removeMember(csPlayer);
        player.setGameMode(GameMode.ADVENTURE);

        // If they are spectating or a ct, set them to be a t
        if (currTeam.getType() == TeamType.SPECTATORS || currTeam == ct){
            t.addMember(csPlayer);
            player.sendMessage(ChatColor.GOLD + "You are now a T");
        // Must be a t, make them a ct
        } else {
            ct.addMember(csPlayer);
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

        // We can only go to next phase if there is a game
        if (!gameManager.isGameRunning()){
            sender.sendMessage(ChatColor.RED + "There is no game running!");
            return;
        }

        gameManager.nextPhase();
        sender.sendMessage(ChatColor.GREEN + "Going to the next phase!!!");
    }

    private void handleStatusSubcommand(CommandSender sender){

        // Game running?
        if (gameManager.isGameRunning())
            sender.sendMessage(ChatColor.GREEN + "Current Phase: " + ChatColor.GRAY + gameManager.getPhaseManager().getCurrentPhase().type());
         else
            sender.sendMessage(ChatColor.RED + "There is not a game running.");

        sender.sendMessage(ChatColor.GREEN + "There is currently a game running.");
        sender.sendMessage(ChatColor.AQUA + "Defenders: " + gameManager.getTeamManager().getDefenders().getMemberString());
        sender.sendMessage(ChatColor.RED + "Attackers: " + gameManager.getTeamManager().getAttackers().getMemberString());
        sender.sendMessage(ChatColor.GRAY + "Spectators: " + gameManager.getTeamManager().getSpectators().getMemberString());
        sender.sendMessage();
        sender.sendMessage(ChatColor.GRAY + "The score is [" + ChatColor.AQUA + gameManager.getTeamManager().getDefenders().getRoundsWon() + ChatColor.GRAY + " - " + ChatColor.RED + gameManager.getTeamManager().getAttackers().getRoundsWon() + ChatColor.GRAY + "]");

    }






}
