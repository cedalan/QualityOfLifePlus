package com.elias.qualityoflifeplus.blb;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.elias.qualityoflifeplus.QualityOfLifePlusPlugin;

public class BlbCommandHandler implements CommandExecutor {

    private final QualityOfLifePlusPlugin plugin;
    private final BlbListener listener;

    public BlbCommandHandler(QualityOfLifePlusPlugin plugin, BlbListener listener) {
        this.plugin = plugin;
        this.listener = listener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You need to provide additional arguments. Do /blb list for a list of all commands");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
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
