package com.elias.qualityoflifeplus.blb;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elias.qualityoflifeplus.QualityOfLifePlusPlugin;
import com.elias.qualityoflifeplus.utils.PlayerDataManager;
import com.elias.qualityoflifeplus.utils.ToolUtils;

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
        /* /blb leaderboard <tool> 
         * Handle if user does not specify which leaderboard (which tool that is)
         * 
         * Handle the different cases
         * 
         * Handle if leaderboard does not exist
         */
        Player player = sender.getServer().getPlayer(sender.getName());

        if (args.length < 2) {
            player.sendMessage("You need to specify a tool.");
            return;
        }

        String toolArg = args[1];
        char firstLetter = toolArg.charAt(0);
        String adjustTool = Character.toUpperCase(firstLetter) + toolArg.substring(1).toLowerCase();

        if (ToolUtils.isValidTool(adjustTool)) {
            Map<String, Integer> allToolData = PlayerDataManager.getAllToolData(plugin, adjustTool);

            Map<String, Integer> sortedData = allToolData.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

            int i = 0;
            player.sendMessage("BLB leaderboard for " + adjustTool + ":");
            for (String key : sortedData.keySet()) {
                i++;
                player.sendMessage("#" + String.valueOf(i) + ": " + key + " - " + String.valueOf(sortedData.get(key)) + " blocks.");
            }
        } else {
            player.sendMessage("You need to provide a correct tool label!");
        }
    }

    private void showLevelsToPlayer(CommandSender sender, String[] args) {
        // TODO:
        /* /blb levels
         * Return all levels for the sender.
         */
        Player player = sender.getServer().getPlayer(sender.getName());
        Map<String, Integer> playerData = PlayerDataManager.getPlayerData(plugin, player);

        player.sendMessage("Your stats are:");
        for (String key : playerData.keySet()) {
            player.sendMessage(key + " " + String.valueOf(playerData.get(key)));
        }
    }

    private void showLevelToPlayer(CommandSender sender, String[] args) {
        // TODO: 
        /* /blb level <tool>
         * Return user level for a given tool. If no tool/profession is given, tell user they neeed to specify.
         */
        Player player = sender.getServer().getPlayer(sender.getName());
        Map<String, Integer> playerData = PlayerDataManager.getPlayerData(plugin, player);

        String toolArg = args[1];
        char firstLetter = toolArg.charAt(0);
        String adjustTool = Character.toUpperCase(firstLetter) + toolArg.substring(1).toLowerCase();

        if (ToolUtils.isValidTool(adjustTool)) {
            if (playerData.get(adjustTool) == null) {
                player.sendMessage("You have no stats for " + toolArg);
            } else {
                player.sendMessage("Your xp with " + adjustTool + " is " + String.valueOf(playerData.get(adjustTool)));
            }
        } else {
            player.sendMessage(toolArg + " is not a valid tool!");
        }
    }

    private void adjustPlayerExperience(CommandSender sender, String[] args) {
        // TODO: 
        /* /blb xp <tool> <+/-> <amount> 
         * Adjust user experience level
         */
        Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage("You do not have access to that command!");
            return;
        } else if (args.length < 4 || args.length > 4) {
            player.sendMessage("You need to provide ONLY 5 arguments!!");
            return;
        } else {
            Map<String, Integer> playerData = PlayerDataManager.getPlayerData(plugin, player);
            
            String toolArg = args[1];
            char firstLetter = toolArg.charAt(0);
            String adjustTool = Character.toUpperCase(firstLetter) + toolArg.substring(1).toLowerCase();

            if (ToolUtils.isValidTool(adjustTool)) {
                if (args[2].equals("+")) {
                    try {
                        int amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("You need to provide a number...");
                        return;
                    }
                    Integer amount = Integer.parseInt(args[3]);

                    if (playerData.get(adjustTool) == null) {
                        playerData.put(adjustTool, amount);
                    } else {
                        playerData.put(adjustTool, amount + playerData.get(adjustTool));
                    }
                } else if (args[2].equals("-")) {
                    try {
                        int amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("You need to provide a number...");
                        return;
                    }
                    Integer amount = Integer.parseInt(args[3]);

                    if (playerData.get(adjustTool) == null) {
                        playerData.put(adjustTool, amount);
                    } else {
                        playerData.put(adjustTool, amount + playerData.get(adjustTool));
                    }
    
                } else {
                    player.sendMessage("You need to provide valid arguments! + or -.");
                    return;
                }

                PlayerDataManager.savePlayerData(player, playerData, adjustTool);
                player.sendMessage("Successfully adjusted experience for " + toolArg + " to " + String.valueOf(playerData.get(adjustTool)));
            } else {
                player.sendMessage("You need to provide a valid argument for oyur tool!");
            }
        }
    }
}
