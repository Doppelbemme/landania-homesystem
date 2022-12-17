package net.landania.homesystem.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
     * Adds a new home to the database for the given UUID.
     *
     * @param connection     the connection to the database
     * @param uuid           the UUID of the player to add the home for
     * @param homeName       the name of the home
     * @param playerLocation the location of the player in the world
     */
    public static void addHome(Connection connection, UUID uuid, String homeName, Location playerLocation) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userhome (Uuid, HomeName, WorldX, WorldY, WorldZ, WorldYaw, WorldPitch, WorldName) VALUES (?,?,?,?,?,?,?,?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
            preparedStatement.setDouble(3, playerLocation.getX());
            preparedStatement.setDouble(4, playerLocation.getY());
            preparedStatement.setDouble(5, playerLocation.getZ());
            preparedStatement.setFloat(6, playerLocation.getYaw());
            preparedStatement.setFloat(7, playerLocation.getPitch());
            preparedStatement.setString(8, playerLocation.getWorld().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Deletes a home for a user from the database.
     *
     * @param connection the connection to the database
     * @param uuid       the UUID of the user
     * @param homeName   the name of the home to delete
     */
    public static void deleteHome(Connection connection, UUID uuid, String homeName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userhome WHERE Uuid = ? AND HomeName = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Deletes all homes for a user from the database.
     *
     * @param connection the connection to the database
     * @param uuid       the UUID of the user
     */
    public static void deleteAllHomes(Connection connection, UUID uuid) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userhome WHERE Uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Retrieves the location of a user's home from the database.
     *
     * @param connection the connection to the database
     * @param uuid       the UUID of the user
     * @param homeName   the name of the home
     * @return the location of the home, or null if the home was not found
     */
    public static Location getHomeLocation(Connection connection, UUID uuid, String homeName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT WorldX, WorldY, WorldZ, WorldYaw, WorldPitch, WorldName FROM userhome WHERE Uuid = ? AND HomeName = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, homeName.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double x = resultSet.getDouble("WorldX");
                double y = resultSet.getDouble("WorldY");
                double z = resultSet.getDouble("WorldZ");
                float yaw = resultSet.getFloat("WorldYaw");
                float pitch = resultSet.getFloat("WorldPitch");
                String worldName = resultSet.getString("WorldName");
                return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the names of all homes for a user from the database.
     *
     * @param connection the connection to the database
     * @param uuid       the UUID of the user
     * @return a list of home names, or an empty list if no homes were found
     */
    public static List<String> getHomes(Connection connection, UUID uuid) {
        List<String> homeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT HomeName FROM userhome WHERE Uuid = ? ORDER BY HomeName ASC")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                homeList.add(resultSet.getString("HomeName"));
            }
            return homeList;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves the names of a limited number of homes for a user from the database.
     *
     * @param connection the connection to the database
     * @param uuid       the UUID of the user
     * @param limit      the maximum number of homes to retrieve
     * @return a list of home names, or an empty list if no homes were found
     */
    public static List<String> getHomes(Connection connection, UUID uuid, int limit) {
        List<String> homeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT HomeName FROM userhome WHERE Uuid = ? ORDER BY HomeName ASC LIMIT ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                homeList.add(resultSet.getString("HomeName"));
            }
            return homeList;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

}