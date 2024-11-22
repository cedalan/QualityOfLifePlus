package com.elias.qualityoflifeplus.blb;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.elias.qualityoflifeplus.QualityOfLifePlusPlugin;
import com.elias.qualityoflifeplus.utils.PlayerDataManager;
import com.elias.qualityoflifeplus.utils.ToolUtils;

public class BlbListener implements Listener {
    
    private final QualityOfLifePlusPlugin plugin;
    
        public BlbListener(QualityOfLifePlusPlugin plugin, PlayerDataManager playerDataManager) {
            this.plugin = plugin;
        }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        //Get player and tooltype
        Player player = event.getPlayer();
        Material specificTool = player.getInventory().getItemInMainHand().getType();
        String toolType = ToolUtils.getToolCategory(specificTool);

        //Get player data:
        Map<String, Integer> playerData = PlayerDataManager.getPlayerData(plugin, player);

        // Initialize count for the toolType if not present
        playerData.putIfAbsent(toolType, 0);

        Integer previousLevel = PlayerDataManager.xpToLevel(playerData.get(toolType));
        Integer newLevel = PlayerDataManager.xpToLevel(playerData.get(toolType) + 1);

        if (previousLevel < newLevel) {
            player.sendMessage("Your " + toolType + " skill leveled up to level " + String.valueOf(newLevel));
        }

        //Update playerdata
        playerData.put(toolType, playerData.get(toolType) + 1);

        //Save player data
        PlayerDataManager.savePlayerData(player, playerData, toolType);
    }  
}
