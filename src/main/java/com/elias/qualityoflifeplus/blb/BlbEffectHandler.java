package com.elias.qualityoflifeplus.blb;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.elias.qualityoflifeplus.utils.PlayerDataManager;
import com.elias.qualityoflifeplus.utils.ToolUtils;

public class BlbEffectHandler {
    
    public static void updatePlayerEffect(JavaPlugin plugin, Player player, ItemStack itemInHand) {
        Map<String, Integer> playerData = PlayerDataManager.getPlayerData(plugin, player);

        if (playerData == null) {
            player.sendMessage("No data found for player: " + player.getName());
            return; 
        }
        
        if (itemInHand == null) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            return;
        }

        String itemString = itemInHand.toString();
        Material itemMaterial = itemInHand.getType();
        String toolCategory = ToolUtils.getToolCategory(itemMaterial);
        int duration = 20 * 60 * 60;

        //player.sendMessage("Running update player effect with itemString: " + itemString);
        //Bukkit.broadcastMessage(playerData.toString());
        
        if (!toolCategory.equalsIgnoreCase("Other")) {
            int itemLevel = PlayerDataManager.xpToLevel(playerData.get(toolCategory)) - 1;
            Bukkit.getLogger().info(String.valueOf(itemLevel));
            if (itemLevel < 0) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            } else if (BlbEffectHandler.hasHasteAboveLevel(player, itemLevel)) {
                return;
            } else {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, duration, itemLevel));
            }
        } else {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }

    public static boolean hasHasteAboveLevel(Player player, int level) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.FAST_DIGGING)) {
                if (effect.getAmplifier() > level) {
                    return true;
                }
            }
        }
        return false;
    }
}
