package net.landania.homesystem.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class ConfigAPI {

    public static String getMessage(File configFile, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(configFile);
            JsonNode messagesNode = rootNode.get("messages");
            return ChatColor.translateAlternateColorCodes('&', messagesNode.get(message).asText());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "messages."+message;
    }

}
