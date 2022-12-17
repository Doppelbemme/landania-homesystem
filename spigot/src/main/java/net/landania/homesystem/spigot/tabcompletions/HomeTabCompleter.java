package net.landania.homesystem.spigot.tabcompletions;

import net.landania.homesystem.api.HomeAPI;
import net.landania.homesystem.mysql.MySQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions = HomeAPI.getHomes(MySQL.getConnection(), player.getUniqueId());
        }
        return completions;
    }
}
