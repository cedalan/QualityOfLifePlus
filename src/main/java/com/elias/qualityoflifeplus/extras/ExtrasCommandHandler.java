package com.elias.qualityoflifeplus.extras;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExtrasCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO Auto-generated method stub
        switch (command.getName().toLowerCase()) {
            case "changenick":
            handleChangeNick(sender, args);
        }
        
        return true;
    }

    public void handleChangeNick(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("You need to provide a nickname!");
        }

        String newName = args[0];
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setDisplayName(newName);
            player.sendMessage("You changed your name to " + newName + "!");
        } else {
            sender.sendMessage("Only players can change their names!");
        }
    }
    
}
