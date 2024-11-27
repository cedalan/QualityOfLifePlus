package com.elias.qualityoflifeplus.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.elias.qualityoflifeplus.tombstone.Tombstone;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Location location = player.getLocation();

        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();

        String deathMessage = player.getDisplayName() + " died at - " 
                            + "X: " + String.valueOf(x) + ","
                            + "Y: " + String.valueOf(y) + ","
                            + "Z: " + String.valueOf(z);

        Bukkit.broadcastMessage(deathMessage);

        boolean createdTombstone = Tombstone.createTrombstone(player);

        if (createdTombstone) {
            player.sendMessage("A tombstone was created for you!");
        } else {
            player.sendMessage("Could not create tombstone for you... sorry");
        }

        event.getDrops().clear();
    }
}