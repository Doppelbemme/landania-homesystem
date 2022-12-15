package net.landania.homesystem.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class MySQL {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "homesystem";
    private static final String USERNAME = "homesystem";
    private static final String PASSWORD = "homesystem";

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection Connection) {
        connection = Connection;
    }

    public static void connect() {
        if (isConnected()) return;
        try {
            setConnection(DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true", USERNAME, PASSWORD));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void disconnect() {
        if (!isConnected()) return;
        try {
            getConnection().close();
            setConnection(null);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void setupTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS userhome (`Uuid` CHAR(36) NOT NULL , `HomeName` VARCHAR(255) NOT NULL , `WorldX` INT NOT NULL , `WorldY` INT NOT NULL , `WorldZ` INT NOT NULL , `WorldName` VARCHAR(255) NOT NULL ) ENGINE = InnoDB;")) {
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        try (PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE UNIQUE INDEX IF NOT EXISTS uuid_homename_index ON userhome (Uuid, HomeName);")) {
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    private static boolean isConnected() {
        try {
            return Objects.nonNull(getConnection()) && getConnection().isValid(2);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }
}
