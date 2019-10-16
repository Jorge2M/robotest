package com.mng.testmaker.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.mng.testmaker.jdbc.Connector;
import com.mng.testmaker.utils.conf.Log4jConfig;


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
        try (Connection conn = Connector.getConnection(true);
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
    
    public static boolean isNeededPurgeHistoricalData(int numDiasToMaintain) {
        String dateLastPurgeAAAAMMDD = ParamsDAO.getParam("PURGED_DATA_FROM");
        if (dateLastPurgeAAAAMMDD!=null && "".compareTo(dateLastPurgeAAAAMMDD)!=0) {
            java.util.Date dateLastPurge;
            String dataFormat = "yyyy-MM-dd";
            try {
                dateLastPurge = new SimpleDateFormat(dataFormat).parse(dateLastPurgeAAAAMMDD);
            }
            catch (ParseException e) {
                Log4jConfig.pLogger.warn("Data with no data format " + dataFormat, e);
                return false;
            }
            
            Calendar calLastPurge = Calendar.getInstance();
            calLastPurge.setTime(dateLastPurge);
            Calendar calToPurge = Calendar.getInstance();
            calToPurge.add(Calendar.DATE, numDiasToMaintain * -1);
            calToPurge.set(Calendar.HOUR_OF_DAY, 0);
            calToPurge.set(Calendar.MINUTE, 0);
            calToPurge.set(Calendar.SECOND, 0);
            calToPurge.set(Calendar.MILLISECOND, 0);
            if (calLastPurge.before(calToPurge)) {
                return true;
            }
        }
        
        return false;
    }
}
