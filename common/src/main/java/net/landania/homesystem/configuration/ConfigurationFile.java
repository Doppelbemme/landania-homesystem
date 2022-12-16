package net.landania.homesystem.configuration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

public class ConfigurationFile {
    private static File configFile = new File("plugins/home-system/config.json");

    public static File getConfigFile(){
        return configFile;
    }

    public static void setupConfigurationFile() {
        File pluginsDir = new File("plugins");
        if (!pluginsDir.exists()) {
            pluginsDir.mkdirs();
        }

        File pluginDir = new File("plugins/home-system");
        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
        }

        try {
            configFile.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        if (configFile.length() <= 0) {
            Configuration configuration = new Configuration();
            configuration.database = new Configuration.Database();
            configuration.database.host = "localhost";
            configuration.database.port = 3306;
            configuration.database.database = "database";
            configuration.database.username = "username";
            configuration.database.password = "password";
            configuration.messages = new Configuration.Messages();
            configuration.messages.noPermission = "&cDazu hast du keine Rechte!";
            configuration.messages.notAPlayer = "&cNur ein Spieler kann diesen Befehl benutzen!";
            configuration.messages.homeCreated = "&aDein Home wurde erfolgreich erstellt.";
            configuration.messages.homeDeleted = "&cDein Home wurde erfolgreich gelöscht.";
            configuration.messages.homeAlreadyExists = "&cEin Home mit diesem Name existiert bereits.";
            configuration.messages.homeNotExisting = "&cDu hast kein Home mit diesem Namen.";
            configuration.messages.homeTeleport = "&aDu wurdest zu deinem Home teleportiert.";
            configuration.messages.sethomeUsage = "&cNutze: /sethome <Name>";
            configuration.messages.delhomeUsage = "&cNutze: /delhome <Name>";
            configuration.messages.homeUsage = "&cNutze: /home <Name>";

            JsonFactory factory = new JsonFactory();
            try {
                JsonGenerator generator = factory.createGenerator(configFile, JsonEncoding.UTF8);
                ObjectMapper mapper = new ObjectMapper();
                ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
                writer.writeValue(generator, configuration);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }
    }
}
