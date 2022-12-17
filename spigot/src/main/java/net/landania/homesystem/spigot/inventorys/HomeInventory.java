package net.landania.homesystem.spigot.inventorys;

import dev.dbassett.skullcreator.SkullCreator;
import net.landania.homesystem.api.ConfigAPI;
import net.landania.homesystem.api.HomeAPI;
import net.landania.homesystem.configuration.ConfigurationFile;
import net.landania.homesystem.spigot.itembuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class HomeInventory {
    private static final File configuration = ConfigurationFile.getConfigFile();

    private static Inventory createInventory(Connection connection, UUID uuid) {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, ConfigAPI.getGuiText(configuration, "title"));
        List<String> homes = HomeAPI.getHomes(connection, uuid, 45);
        for (String home : homes) {
            inventory.addItem(new ItemBuilder(Material.PAPER).
                    setDisplayName(ChatColor.YELLOW + home).
                    setLore(ConfigAPI.getHomeItemLore(configuration)).
                    build());
        }
        for (int i = 45; i >= 45 && i <= 53; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).
                    setDisplayName(ChatColor.GREEN + "").
                    build());
        }

        ItemStack itemStack = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1NDgzNjJhMjRjMGZhODQ1M2U0ZDkzZTY4YzU5NjlkZGJkZTU3YmY2NjY2YzAzMTljMWVkMWU4NGQ4OTA2NSJ9fX0=");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ConfigAPI.getGuiText(configuration, "deleteItemName"));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(49, itemStack);

        return inventory;
    }

    public static Inventory getInventory(Connection connection, Player player) {
        return createInventory(connection, player.getUniqueId());
    }
}
