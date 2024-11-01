package com.elias.qualityoflifeplus.blb;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.elias.qualityoflifeplus.QualityOfLifePlusPlugin;

public class BlbListener implements Listener {
    
    private final QualityOfLifePlusPlugin plugin;
    private final Map<Player, Map<Material, Integer>> blocksBrokenWithTool = new HashMap<>();

    public BlbListener(QualityOfLifePlusPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        //Get player and tool
        Player player = event.getPlayer();
        Material toolType = player.getInventory().getItemInMainHand().getType();


        blocksBrokenWithTool.putIfAbsent(player, new HashMap<>());
        Map<Material, Integer> toolCount = blocksBrokenWithTool.get(player);

        toolCount.put(toolType, toolCount.getOrDefault(toolType, 0) + 1);
        player.sendMessage("You've broken " + toolCount.get(toolType) + " blocks with " + toolType.toString().toLowerCase() + ".");
    }

}
