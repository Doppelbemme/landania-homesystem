package net.landania.homesystem.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HomeAPI {
    
    /**
     * Checks whether or not a home with the same name already exists for the given player.
     * 
     * @param   connection  a connection to the mysql database
     * @param   uuid        the uuid of the given player
     * @param   homeName    the name of the home to look for
     * @return              true if a dataset has been found and false otherwise
     */

    public static boolean isHomeExisting(Connection connection, UUID uuid, String homeName){
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(uuid) as count FROM userhome WHERE Uuid = ? AND HomeName = ?")) {
            preparedStatement.setString(0, uuid.toString());
            preparedStatement.setString(1, homeName.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getInt("count") == 0){
                    return false;
                }
                return true;
            }        
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public static void addHome(UUID uuid, String homeName){

    }

}