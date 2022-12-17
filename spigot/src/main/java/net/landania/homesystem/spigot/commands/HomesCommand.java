package net.landania.homesystem.spigot.commands;

import net.landania.homesystem.api.ConfigAPI;
import net.landania.homesystem.configuration.ConfigurationFile;
import net.landania.homesystem.mysql.MySQL;
import net.landania.homesystem.spigot.inventorys.HomeInventory;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File configuration = ConfigurationFile.getConfigFile();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigAPI.getMessage(configuration, "notAPlayer"));
            return false;
        }

        Player player = (Player) sender;
        Location playerLocation = player.getLocation();

        if (!player.hasPermission("homesystem.command.homes")) {
            player.sendMessage(ConfigAPI.getMessage(configuration, "noPermission"));
            player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
            return false;
        }

        switch (args.length) {
            case 0:
                player.openInventory(HomeInventory.getInventory(MySQL.getConnection(), player));
                return true;
            default:
                player.sendMessage(ConfigAPI.getMessage(configuration, "homesUsage"));
                player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
                return false;
        }
    }
}
