package net.landania.homesystem.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HomeAPI {

    /**
     * Checks whether a home with the same name already exists for the given player.
     *
     * @param connection a connection to the mysql database
     * @param uuid       the uuid of the given player
     * @param homeName   the name of the home to look for
     * @return true if a dataset has been found and false otherwise
     */

    public static boolean isHomeExisting(Connection connection, UUID uuid, String homeName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(Uuid) as count FROM userhome WHERE Uuid = ? AND HomeName = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (Integer.parseInt(resultSet.getString("count")) != 0);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    /**
     * @param connection
     * @param uuid
     * @param homeName
     * @param x
     * @param y
     * @param z
     * @param worldName
     */

    public static void addHome(Connection connection, UUID uuid, String homeName, int x, int y, int z, String worldName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userhome (Uuid, HomeName, WorldX, WorldY, WorldZ, WorldName) VALUES (?,?,?,?,?,?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
            preparedStatement.setInt(3, x);
            preparedStatement.setInt(4, y);
            preparedStatement.setInt(5, z);
            preparedStatement.setString(6, worldName);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 
     */
    public static void deleteHome(Connection connection, UUID uuid, String homeName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userhome WHERE Uuid = ? AND HomeName = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static Location getHomeLocation(Connection connection, UUID uuid, String homeName) {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT WorldX, WorldY, WorldZ, WorldName FROM userhome WHERE Uuid = ? AND HomeName = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }

        if (resultSet.next()){
            int x = resultSet.getInteger("WorldX");
            int y = resultSet.getInteger("WorldY");
            int z = resultSet.getInteger("WorldZ");
            String worldName = resultSet.getString("WorldName");
        }
    }

}