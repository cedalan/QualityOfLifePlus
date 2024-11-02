package com.elias.qualityoflifeplus.blb;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private static Map<UUID, Map<String, Integer>> blocksBrokenWithTool = new HashMap<>();
    
        public BlbListener(QualityOfLifePlusPlugin plugin) {
            this.plugin = plugin;
            BlbListener.blocksBrokenWithTool = PlayerDataManager.getAllPlayerData(plugin);
        }
    
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event) {
            //Get player and tooltype
            Player player = event.getPlayer();
            UUID player_uuid = player.getUniqueId();
            Material specificTool = player.getInventory().getItemInMainHand().getType();
            String toolType = ToolUtils.getToolCategory(specificTool);
    
            //Set up new map if not exists, then get specific map for player
            blocksBrokenWithTool.putIfAbsent(player_uuid, new HashMap<>());
            Map<String, Integer> toolCount = blocksBrokenWithTool.get(player_uuid);
    
            //Update player hasmap
            toolCount.put(toolType, toolCount.getOrDefault(toolType, 0) + 1);
    
            //This is for debugging
            player.sendMessage("You've broken " + toolCount.get(toolType) + " blocks with " + toolType.toLowerCase() + ".");
        }
    
        public static Map<UUID, Map<String, Integer>> getBlocksBrokenWithToolForAllPlayers() {
            return blocksBrokenWithTool;
    }

    public static Map<String, Integer> getBlocksBrokenWithToolForPlayer(UUID player_uuid) {
        return blocksBrokenWithTool.getOrDefault(player_uuid, new HashMap<>());
    }
    
}
