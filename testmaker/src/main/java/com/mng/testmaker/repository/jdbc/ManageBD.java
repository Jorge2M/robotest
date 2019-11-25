package com.mng.testmaker.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mng.testmaker.service.TestMaker;


public class ManageBD {

    public static void vacuum() {
        try (Connection conn = TestMaker.getRepository().getConnection();
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
