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

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        File configuration = ConfigurationFile.getConfigFile();
        Location playerLocation = player.getLocation();

        if (!player.hasPermission("homesystem.command.sethome")) {
            player.sendMessage(ConfigAPI.getMessage(configuration, "noPermission"));
            player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
            return false;
        }

        //TODO: Add check for correct amount of arguments

        String homeName = args[0].toLowerCase(Locale.ROOT);
        if (HomeAPI.isHomeExisting(MySQL.getConnection(), player.getUniqueId(), homeName)) {
            player.sendMessage(ConfigAPI.getMessage(configuration, "homeAlreadyExists"));
            player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
            return false;
        }

        HomeAPI.addHome(MySQL.getConnection(), player.getUniqueId(), homeName, playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ(), playerLocation.getWorld().getName());
        player.sendMessage(ConfigAPI.getMessage(configuration, "homeCreated"));
        player.playSound(playerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
        return true;
    }
}
