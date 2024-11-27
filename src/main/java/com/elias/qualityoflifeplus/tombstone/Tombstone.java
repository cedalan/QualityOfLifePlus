package com.elias.qualityoflifeplus.tombstone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tombstone {
    
    public static boolean createTrombstone(Player player) {

        //Get location of block beside player and set it to chest
        Block chestBlock = player.getLocation().getBlock();

        //Ensure block does not replace another block
        boolean hasFoundPos = false;
        for (int i = 0; i < 3; i++) {
            int[] addPos = new int[3];
            addPos[i] = 1;
            Location tryPos = chestBlock.getLocation().add(addPos[0], addPos[1], addPos[2]);

            if (tryPos.getBlock().getType() == Material.AIR && !hasFoundPos) {
                hasFoundPos = true;
                chestBlock.getLocation().add(addPos[0], addPos[1], addPos[2]);
            }
        }

        if (!hasFoundPos) {
            player.sendMessage("Could not find a place to place your tombstone...");
            return false;
        }

        chestBlock.setType(Material.CHEST);

        Chest chest = (Chest) chestBlock.getState();

        // Get player's main inventory and armour. Armour is included in main inventory, but to prioritize armour in chest it is added through getarmour
        ItemStack[] mainInventory = player.getInventory().getContents();
        ItemStack[] armourContents = player.getInventory().getArmorContents();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        List<ItemStack> allItems = new ArrayList<>();

        for (ItemStack armourPiece : armourContents) {
            if (armourPiece != null) {
                allItems.add(armourPiece);
            }
        }

        if (offHandItem != null) {
            allItems.add(offHandItem);
        }

        for (ItemStack item : mainInventory) {
            if (item != null && !armorContains(armourContents, item)) {
                allItems.add(item);
            }
        }

        Location dropLocation = chest.getLocation().add(0, 1, 0); //Location to drop items if chest is full
        for (ItemStack item : allItems) {
            if (chest.getInventory().firstEmpty() != -1) { //If chest is not full
                chest.getInventory().addItem(item);
            } else {
                dropLocation.getWorld().dropItem(dropLocation, item);
            }
        }

        return true;
    }

    public static boolean armorContains(ItemStack[] armorContents, ItemStack item) {
        //Check if item is armor
        if (armorContents == null || item == null) {
            return false;
        }
        for (ItemStack armorItem : armorContents) {
            if (armorItem != null && armorItem.isSimilar(item)) {
                return true;
            }
        }
        return false;
    }
}
