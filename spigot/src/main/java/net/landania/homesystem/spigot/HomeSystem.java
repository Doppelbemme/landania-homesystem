package net.landania.homesystem.spigot;

import net.landania.homesystem.configuration.ConfigurationFile;
import net.landania.homesystem.mysql.MySQL;
import net.landania.homesystem.spigot.commands.DelHomeCommand;
import net.landania.homesystem.spigot.commands.HomeCommand;
import net.landania.homesystem.spigot.commands.HomesCommand;
import net.landania.homesystem.spigot.commands.SetHomeCommand;
import net.landania.homesystem.spigot.listener.InventoryInteractListener;
import net.landania.homesystem.spigot.tabcompletions.DelHomeTabCompleter;
import net.landania.homesystem.spigot.tabcompletions.HomeTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationFile.setupConfigurationFile();
        MySQL.connect();
        MySQL.setupTable();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }

    private void registerCommands() {
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("delhome").setTabCompleter(new DelHomeTabCompleter());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new HomeTabCompleter());
        getCommand("homes").setExecutor(new HomesCommand());
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryInteractListener(), this);
    }
}
