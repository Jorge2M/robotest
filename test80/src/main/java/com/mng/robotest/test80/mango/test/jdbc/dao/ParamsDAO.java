package com.mng.robotest.test80.mango.test.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mng.robotest.test80.arq.jdbc.Connector;

@SuppressWarnings("javadoc")
public class ParamsDAO {
    public static String SQLSelectParam = 
        "SELECT PARAM, VALOR FROM PARAMS " +
        "WHERE PARAM = ?";

    public static String SQLUpdateParam = 
        "UPDATE PARAMS SET VALOR = ? " + 
        "WHERE PARAM = ?";
    
    public static String SQLSelectParams = 
        "SELECT PARAM, VALOR FROM PARAMS";
    
    public static String getParam(String param) {
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectParam)) {
            select.setString(1, param);
            try (ResultSet resultado = select.executeQuery()) {
                return (resultado.getString("VALOR"));
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }        
    }
    
    public static void setParam(String param, String newValue) {
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLUpdateParam)) {
            stmt.setString(1, newValue);
            stmt.setString(2, param);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static HashMap<String, String> listParams() {
        HashMap<String, String> listParams = new HashMap<>();
        if (!"ROBOTEST2".equals(System.getProperty("ROBOTEST2"))) {
            try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
                PreparedStatement select = conn.prepareStatement(SQLSelectParams)) {
                try (ResultSet resultado = select.executeQuery()) {
                    while (resultado.next())
                        listParams.put(resultado.getString("PARAM"), resultado.getString("VALOR"));
                }
                
                
            } 
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            } 
            catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        return listParams;
    }    
}
