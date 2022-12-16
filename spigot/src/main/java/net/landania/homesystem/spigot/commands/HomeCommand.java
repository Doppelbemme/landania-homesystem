package net.landania.homesystem.spigot.commands;

public class HomeCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        File configuration = ConfigurationFile.getConfigFile();
        Location playerLocation = player.getLocation();

        if (!player.hasPermission("homesystem.command.home")) {
            player.sendMessage(ConfigAPI.getMessage(configuration, "noPermission"));
            player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
            return false;
        }

        switch(args.length){
            case 1:
                String homeName = args[0].toLowerCase(Locale.ROOT);
                if (!HomeAPI.isHomeExisting(MySQL.getConnection(), player.getUniqueId(), homeName)) {
                    player.sendMessage(ConfigAPI.getMessage(configuration, "homeNotExisting"));
                    player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
                    return false;
                }
                
                //TODO: \/
                HomeAPI.deleteHome(MySQL.getConnection(), player.getUniqueId(), homeName);
                player.sendMessage(ConfigAPI.getMessage(configuration, "homeTeleport"));
                player.playSound(playerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                return true;
                break;
            default:
                player.sendMessage(ConfigAPI.getMessage(configuration, "homeUsage"));
                player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 5, 0);
                return false;
                break;
        }
        
        return false;
    }
}