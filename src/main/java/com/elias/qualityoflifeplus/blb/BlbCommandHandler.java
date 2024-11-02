package com.elias.qualityoflifeplus.blb;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elias.qualityoflifeplus.QualityOfLifePlusPlugin;
import com.elias.qualityoflifeplus.utils.PlayerDataManager;

public class BlbCommandHandler implements CommandExecutor {

    private final QualityOfLifePlusPlugin plugin;
    private final BlbListener listener;
    private final Map<String, String> commands = new HashMap<>();

    public BlbCommandHandler(QualityOfLifePlusPlugin plugin, BlbListener listener) {
        this.plugin = plugin;
        this.listener = listener;
        this.commands.put("list", "lists all commands");
        this.commands.put("leaderboard <specifier>", "shows leaderboarad for specified tool");
        this.commands.put("level <specifier>", "returns player level given specifier");
        this.commands.put("levels", "returns player levels for all specifiers");
        this.commands.put("xp <add/remove> <amount>", "adds or removes xp according to amount");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You need to provide additional arguments. Do /blb list for a list of all commands");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "list":
                showBlbCommandListToPlayer(sender, args);
                break;
            case "leaderboard":
                showLeaderboardToPlayer(sender, args);
                break;

            case "level":
                showLevelToPlayer(sender, args);
                break;

            case "levels":
                showLevelsToPlayer(sender, args);
                break;

            case "xp":
                adjustPlayerExperience(sender, args);
                break;
        
            default:
                sender.sendMessage("You need to provide a valid subcommand.");
                break;
        }

        return true;
    }

    private void showBlbCommandListToPlayer(CommandSender sender, String[] args) {
        //TODO: 
        /*
         * /blb list
         * 
         * Show a list of all commands to player
         */
        StringBuilder strLine = new StringBuilder();

        for (String command : commands.keySet()) {
            strLine.append("/blb " + command + " - " + commands.get(command) + "\n");
        }
        sender.sendMessage(strLine.toString());
    }
    
    private void showLeaderboardToPlayer(CommandSender sender, String[] args) {
        // TODO:
        /* /blb leaderboard
         * Handle if user does not specify which leaderboard (which tool that is)
         * 
         * Handle the different cases
         * 
         * Handle if leaderboard does not exist
         */
    }

    private void showLevelsToPlayer(CommandSender sender, String[] args) {
        // TODO:
        /* /blb levels
         * Return all levels for the sender.
         */
        Player player = sender.getServer().getPlayer(sender.getName());
        UUID player_uuid = player.getUniqueId();
        Map<String, Integer> playerData = PlayerDataManager.getAllPlayerData(plugin).get(player_uuid);

        StringBuilder strLine = new StringBuilder();

        for (String tool : playerData.keySet()) {
            strLine.append(tool + ":");
        }
    }

    private void showLevelToPlayer(CommandSender sender, String[] args) {
        // TODO: 
        /* /blb level <tool>
         * Return user level for a given tool. If no tool/profession is given, tell user they neeed to specify.
         */
    }

    private void adjustPlayerExperience(CommandSender sender, String[] args) {
        // TODO: 
        /* /blb xp <+/-> <amount>
         * Adjust user experience level
         */
        
    }
}
