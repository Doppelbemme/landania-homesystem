package net.landania.homesystem.spigot;

import net.landania.homesystem.configuration.ConfigurationFile;
import net.landania.homesystem.mysql.MySQL;
import net.landania.homesystem.spigot.commands.SetHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationFile.setupConfigurationFile();
        MySQL.connect();
        MySQL.setupTable();
        registerCommands();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }

    private void registerCommands() {
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());
    }
}
