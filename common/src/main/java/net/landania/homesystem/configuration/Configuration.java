package net.landania.homesystem.configuration;

public class Configuration {
    public Database database;
    public Messages messages;

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
        public String homeAlreadyExists;
        public String homeTeleport;

    }

}