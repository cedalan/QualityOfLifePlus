package com.elias.qualityoflifeplus;

import org.bukkit.plugin.java.JavaPlugin;

import com.elias.qualityoflifeplus.blb.BlbCommandHandler;
import com.elias.qualityoflifeplus.blb.BlbListener;

public class QualityOfLifePlusPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getLogger().info("QualityOfLifePlus has been enabled!");

        BlbListener listener = new BlbListener(this);
        getServer().getPluginManager().registerEvents(listener, this);

        this.getCommand("blb").setExecutor(new BlbCommandHandler(this, listener));
    }

    @Override
    public void onDisable() {
        getLogger().info("QualityOfLifePlus has been disabled.");
    }
}
