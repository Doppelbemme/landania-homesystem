package net.landania.homesystem.spigot;

import net.landania.homesystem.mysql.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        MySQL.connect();
        MySQL.setupTable();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }
}
