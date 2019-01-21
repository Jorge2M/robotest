package com.mng.robotest.test80.arq.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mng.robotest.test80.arq.jdbc.Connector;

@SuppressWarnings("javadoc")
public class CorreosGroupDAO {
    
    public static String SQLSelectCorreosGroup = 
        "SELECT EMAIL " +
        "  FROM CORREO_GROUPS  " +
        " WHERE IDGROUP = ?";
    
    public static ArrayList<String> getCorreosGroup (String group) {
        ArrayList<String> correosGroup = new ArrayList<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectCorreosGroup)) {            
            select.setString(1, group);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                    correosGroup.add(resultado.getString("EMAIL"));
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return correosGroup;
    }
}
