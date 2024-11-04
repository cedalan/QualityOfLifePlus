package com.elias.qualityoflifeplus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.elias.qualityoflifeplus.blb.BlbEffectHandler;

public class PlayerChangedItemInHandListener implements Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, ItemStack> playerMainHandItems;

    public PlayerChangedItemInHandListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerMainHandItems = new HashMap<>();
    }
    
    @EventHandler
    public void onPlayerChangedMainHand(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        
        // Get the new item in the main hand
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        
        // Retrieve the previously stored item, if any
        ItemStack previousItem = playerMainHandItems.get(playerUUID);

        // Check if the item has actually changed
        if (!isSameItem(previousItem, newItem)) {
            // Update the player's effect based on the new item
            BlbEffectHandler.updatePlayerEffect(plugin, player, newItem);
            
            // Store the new item as the current main hand item
            playerMainHandItems.put(playerUUID, newItem);
        }
    }

    private boolean isSameItem(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return false;
        }
        return item1.isSimilar(item2);
    }
}
