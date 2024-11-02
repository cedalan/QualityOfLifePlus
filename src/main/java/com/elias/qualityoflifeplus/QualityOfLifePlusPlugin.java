package com.elias.qualityoflifeplus;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.elias.qualityoflifeplus.blb.BlbCommandHandler;
import com.elias.qualityoflifeplus.blb.BlbListener;
import com.elias.qualityoflifeplus.events.PlayerLogoutListener;
import com.elias.qualityoflifeplus.utils.PlayerDataManager;

public class QualityOfLifePlusPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getLogger().info("QualityOfLifePlus has been enabled!");

        //Create data folder for player data:
        File dataFolder = new File(getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        //Enable listener for block breaking part of plugin
        BlbListener blbListener = new BlbListener(this);
        getServer().getPluginManager().registerEvents(blbListener, this);

        // Initialize PlayerDataManager
        PlayerDataManager playerDataManager = new PlayerDataManager(this);

        //Enable listener for data saving when player logs out:
        PlayerLogoutListener playerLogoutListener = new PlayerLogoutListener(playerDataManager);
        getServer().getPluginManager().registerEvents(playerLogoutListener, this);

        //Enable command handler for all commands related to block breaking
        this.getCommand("blb").setExecutor(new BlbCommandHandler(this, blbListener));
    }

    @Override
    public void onDisable() {
        getLogger().info("QualityOfLifePlus has been disabled.");
    }
}
