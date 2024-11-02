package com.elias.qualityoflifeplus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.elias.qualityoflifeplus.utils.PlayerDataManager;

public class PlayerLogoutListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerLogoutListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        playerDataManager.savePlayerData(event.getPlayer());
    }
}
