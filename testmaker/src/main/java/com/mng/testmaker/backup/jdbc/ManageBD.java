package com.mng.robotest.test80.arq.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ManageBD {

    public static void vacuum() {
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("VACUUM")) {
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }    
    }
}
