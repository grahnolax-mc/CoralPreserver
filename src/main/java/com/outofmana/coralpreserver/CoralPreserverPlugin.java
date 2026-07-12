package com.outofmana.coralpreserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.PluginCommand;

public final class CoralPreserverPlugin extends JavaPlugin {
    private CoralConfig settings;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        settings = CoralConfig.load(this);
        getServer().getPluginManager().registerEvents(new CoralListener(this), this);

        PluginCommand command = getCommand("coralpreserver");
        if (command != null) {
            ReloadCommand reloadCommand = new ReloadCommand(this);
            command.setExecutor(reloadCommand);
            command.setTabCompleter(reloadCommand);
        }
    }

    CoralConfig settings() {
        return settings;
    }

    void reloadSettings() {
        reloadConfig();
        settings = CoralConfig.load(this);
    }
}
