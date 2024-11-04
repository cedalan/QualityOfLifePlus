package com.elias.qualityoflifeplus.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.elias.qualityoflifeplus.blb.BlbListener;

import org.apache.commons.io.FilenameUtils;

public class PlayerDataManager {
    
    private final JavaPlugin plugin;
    private final File dataFolder;

    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;

        this.dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public void savePlayerData(Player player) {
        UUID player_uuid = player.getUniqueId();
        File playerFile = new File(dataFolder, player_uuid + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        Map<String, Integer> playerData = BlbListener.getBlocksBrokenWithToolForPlayer(player_uuid);

        for (String key : playerData.keySet()) {
            Integer value = playerData.get(key);
            Integer currentSaved = config.getInt(key, 0);

            currentSaved += value;

            config.set(key, currentSaved);
        }

        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, Map<String, Integer>> getAllPlayerData(JavaPlugin plugin) {
        Map<UUID, Map<String, Integer>> blocksBrokenWithTool = new HashMap<>();

        File duplicate_dataFolder = new File(plugin.getDataFolder(), "data");

        if (!duplicate_dataFolder.exists()) {
            return blocksBrokenWithTool;
        } else if (duplicate_dataFolder.listFiles().length == 0) {
            return blocksBrokenWithTool;
        } else {
            for (File playerFile : duplicate_dataFolder.listFiles()) {
                String player_string_uuid = FilenameUtils.removeExtension(playerFile.getName());
                UUID player_uuid = UUID.fromString(player_string_uuid);

                //Get yamlconfig and create new map for player data
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
                Map<String, Integer> currentPlayerData = new HashMap<>();
                
                //Put daat in the map
                for (String key : config.getKeys(false)) {
                    currentPlayerData.put(key, config.getInt(key));
                }

                //Put small map in big map:)
                blocksBrokenWithTool.put(player_uuid, currentPlayerData);
            }

            return blocksBrokenWithTool;
        }
    }

    public static Integer xpToLevel(Integer experience) {
        /*
        // If the experience is less than 10, return level 1
        if (experience < 10) {
            return 1;
        } else {
            // Otherwise, calculate the level using the logarithm base 10 of the experience
            return (int) Math.log10(experience);
        }
        */
        if (experience == null || experience < 10) {
            return 1;
        }
        return experience / 10;
    }
}
