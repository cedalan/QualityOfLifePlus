package com.elias.qualityoflifeplus.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.apache.commons.io.FilenameUtils;

public class PlayerDataManager {
    
    private static File dataFolder;
    
        public PlayerDataManager(JavaPlugin plugin) {
    
            PlayerDataManager.dataFolder = new File(plugin.getDataFolder(), "data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
        }

    public static void savePlayerData(Player player, Map<String, Integer> playerData, String toolType) {
        UUID player_uuid = player.getUniqueId();
        File playerFile = new File(dataFolder, player_uuid + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        for (String key : playerData.keySet()) {
            Integer value = playerData.get(key);
            config.set(key, value);
        }

        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getPlayerData(JavaPlugin plugin, Player player) {
        Map<String, Integer> playerData = new HashMap<>();

        File dubplicate_datafolder = new File(plugin.getDataFolder(), "data");
        if (!dubplicate_datafolder.exists()) {
            return playerData;
        } else if (dubplicate_datafolder.listFiles().length == 0) {
            return playerData;
        } else {
            File playerFile = new File(dataFolder, player.getUniqueId().toString() + ".yml");
            if (!playerFile.exists()) {
                return playerData;
            } else {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
                for (String key : config.getKeys(false)) {
                    playerData.put(key, config.getInt(key));
                }
            }
        }

        return playerData;
    }

    public static Map<UUID, Map<String, Integer>> getAllPlayerData(JavaPlugin plugin) {
        Map<UUID, Map<String, Integer>> allPlayerData = new HashMap<>();

        File duplicate_dataFolder = new File(plugin.getDataFolder(), "data");

        if (!duplicate_dataFolder.exists()) {
            return allPlayerData;
        } else if (duplicate_dataFolder.listFiles().length == 0) {
            return allPlayerData;
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
                allPlayerData.put(player_uuid, currentPlayerData);
            }

            return allPlayerData;
        }
    }

    public static Map<String, Integer> getAllToolData(JavaPlugin plugin, String toolName) {
        Map<String, Integer> allToolData = new HashMap<>();

        File duplicate_dataFolder = new File(plugin.getDataFolder(), "data");

        if (!duplicate_dataFolder.exists() || duplicate_dataFolder.listFiles().length == 0) {
            return allToolData;
        } else {
            for (File playerFile : duplicate_dataFolder.listFiles()) {
                String player_string_uuid = FilenameUtils.removeExtension(playerFile.getName());
                UUID player_uuid = UUID.fromString(player_string_uuid);
                String playerName = Bukkit.getOfflinePlayer(player_uuid).getName();

                YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

                if (config.contains(toolName)) {
                    allToolData.put(playerName, config.getInt(toolName));
                } else {
                    allToolData.put(playerName, 0);
                }
            }
        }

        return allToolData;
    }

    public static Integer xpToLevel(Integer experience) {
        // If the experience is less than 10, return level 1
        if (experience == null) {
            return 1;
        } else {
            // Otherwise, calculate the level using the logarithm base 10 of the experience
            return (int) Math.floor(Math.log10(experience));
        }
    }
}

