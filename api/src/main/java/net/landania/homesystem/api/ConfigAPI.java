package net.landania.homesystem.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigAPI {

    /**
     * Retrieves a message from the configuration file.
     *
     * @param configFile the configuration file
     * @param message    the key of the message to retrieve
     * @return the message, with color codes translated and placeholders replaced
     */
    public static String getMessage(File configFile, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(configFile);
            JsonNode messagesNode = rootNode.get("messages");
            return ChatColor.translateAlternateColorCodes('&', messagesNode.get(message).asText());
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception) {
            return "messages." + message;
        }
        return "messages." + message;
    }

    /**
     * Retrieves a piece of text from the GUI section of the configuration file.
     *
     * @param configFile the configuration file
     * @param message    the key of the text to retrieve
     * @return the text, with color codes translated and placeholders replaced
     */
    public static String getGuiText(File configFile, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(configFile);
            JsonNode guiNode = rootNode.get("gui");
            return ChatColor.translateAlternateColorCodes('&', guiNode.get(message).asText());
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception) {
            return "gui." + message;
        }
        return "gui." + message;
    }

    /**
     * Retrieves the lore for the home item from the GUI section of the configuration file.
     *
     * @param configFile the configuration file
     * @return a list of lines of lore, with color codes translated
     */
    public static List<String> getHomeItemLore(File configFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(configFile);
            JsonNode guiNode = rootNode.get("gui");
            JsonNode loreNode = guiNode.get("lore");
            List<String> lore = new ArrayList<>();
            for (JsonNode element : loreNode) {
                lore.add(ChatColor.translateAlternateColorCodes('&', element.asText()));
            }
            return lore;
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception) {
            return Arrays.asList("gui.lore", "");
        }
        return Arrays.asList("gui.lore", "");
    }

}
