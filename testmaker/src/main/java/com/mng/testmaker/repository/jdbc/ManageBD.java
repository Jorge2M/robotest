package com.mng.testmaker.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mng.testmaker.conf.defaultstorer.StorerResultSQLite;


public class ManageBD {

    public static void vacuum() {
        try (Connection conn = StorerResultSQLite.getConnectionNew();
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
