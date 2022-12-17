package net.landania.homesystem.spigot.listener;

import net.landania.homesystem.api.ConfigAPI;
import net.landania.homesystem.api.HomeAPI;
import net.landania.homesystem.configuration.ConfigurationFile;
import net.landania.homesystem.mysql.MySQL;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.Connection;

public class InventoryInteractListener implements Listener {

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        File configFile = ConfigurationFile.getConfigFile();
        Connection connection = MySQL.getConnection();

        Player player = (Player) event.getWhoClicked();
        if (event.getRawSlot() == -999) {
            return;
        }

        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (!event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ConfigAPI.getGuiText(ConfigurationFile.getConfigFile(), "title")))) {
            return;
        }

        if (event.getClick() != ClickType.LEFT) {
            event.setCancelled(true);
        }

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }

        if (!currentItem.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = currentItem.getItemMeta();
        Material material = currentItem.getType();
        String displayName;

        switch (material) {
            case PAPER:
                displayName = itemMeta.getDisplayName().replaceAll(String.valueOf(ChatColor.YELLOW), "");
                Location location = HomeAPI.getHomeLocation(connection, player.getUniqueId(), displayName);
                if (location != null) {
                    player.closeInventory();
                    player.teleport(location);
                    player.sendMessage(ConfigAPI.getMessage(configFile, "homeTeleport"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                }
                break;
            case PLAYER_HEAD:
                displayName = itemMeta.getDisplayName();
                if (displayName.equalsIgnoreCase(ConfigAPI.getGuiText(configFile, "deleteItemName"))) {
                    player.closeInventory();
                    HomeAPI.deleteAllHomes(connection, player.getUniqueId());
                    player.sendMessage(ConfigAPI.getMessage(configFile, "allHomesDeleted"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                }
                break;
            default:
                break;
        }

        event.setCancelled(true);
    }
}
