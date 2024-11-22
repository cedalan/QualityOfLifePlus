package com.elias.qualityoflifeplus.blb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class BlbTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        //If sender is not a Player return empty list
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            suggestions.add("list");
            suggestions.add("leaderboard");
            suggestions.add("level");
            suggestions.add("levels");
            suggestions.add("xp");
        } else if (args.length == 2) {
            suggestions.add("Shovel");
            suggestions.add("Axe");
            suggestions.add("Pickaxe");
            suggestions.add("Shovel");
            suggestions.add("Hoe");
        } else if (args.length == 3) {
            suggestions.add("add");
            suggestions.add("remove");
        }

        return suggestions;
    }
    
}
