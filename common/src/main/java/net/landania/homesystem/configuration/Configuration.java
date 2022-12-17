package net.landania.homesystem.configuration;

import java.util.List;

public class Configuration {
    public Database database;
    public Messages messages;
    public Gui gui;

    public static class Database {
        public String host;
        public int port;
        public String database;
        public String username;
        public String password;
    }

    public static class Messages {
        public String noPermission;
        public String notAPlayer;
        public String homeCreated;
        public String homeDeleted;
        public String allHomesDeleted;
        public String homeAlreadyExists;
        public String homeNotExisting;
        public String homeTeleport;
        public String sethomeUsage;
        public String delhomeUsage;
        public String homeUsage;
        public String homesUsage;
    }

    public static class Gui {
        public String title;
        public String deleteItemName;
        public List<String> lore;
    }

}