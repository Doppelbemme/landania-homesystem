package net.landania.homesystem.spigot.commands;

import net.landania.homesystem.api.ConfigAPI;
import net.landania.homesystem.api.HomeAPI;
import net.landania.homesystem.configuration.ConfigurationFile;
import net.landania.homesystem.mysql.MySQL;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

public class DelHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File configuration = ConfigurationFile.getConfigFile();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigAPI.getMessage(configuration, "notAPlayer"));
            return false;
        }

        Player player = (Player) sender;
        Location playerLocation = player.getLocation();

        if (!player.hasPermission("homesystem.command.delhome")) {
            player.sendMessage(ConfigAPI.getMessage(configuration, "noPermission"));
            player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
            return false;
        }

        switch (args.length) {
            case 1:
                String homeName = args[0].toLowerCase(Locale.ROOT);
                if (!HomeAPI.isHomeExisting(MySQL.getConnection(), player.getUniqueId(), homeName)) {
                    player.sendMessage(ConfigAPI.getMessage(configuration, "homeNotExisting"));
                    player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
                    return false;
                }

                HomeAPI.deleteHome(MySQL.getConnection(), player.getUniqueId(), homeName);
                player.sendMessage(ConfigAPI.getMessage(configuration, "homeDeleted"));
                player.playSound(playerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                return true;
            default:
                player.sendMessage(ConfigAPI.getMessage(configuration, "delhomeUsage"));
                player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
                return false;
        }
    }
}
