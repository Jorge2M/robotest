package com.mng.robotest.test80.arq.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.data.TestMakerContext;

public class CorreosDAO {
    
    public static String SQLInsertCorreoEnviado = 
        "INSERT INTO CORREOS_ENVIADOS VALUES (?,?,?,?,?,?)";
    
    public static String SQLSelectCorreosEnviados = 
        "SELECT COUNT(*) " +
        "  FROM CORREOS_ENVIADOS  " +
        " WHERE SUITE = ? AND TEST = ? AND DATE_ENVIO > ? ";
    
    public static void grabarCorreoEnviado (ITestContext context) {
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(context);
        try (Connection conn = Connector.getConnection();
            PreparedStatement insert = conn.prepareStatement(SQLInsertCorreoEnviado)) {
            insert.setString(1, testMakerCtx.getIdSuiteExecution()) ;
            insert.setString(2,context.getSuite().getName());
            insert.setString(3,context.getName());
            insert.setString(4,"");
            insert.setString(5, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()));
            insert.setDate(6, new Date(System.currentTimeMillis()));
            insert.executeUpdate();
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
	
    public static int getCorreosEnviados (int lastMinutes, ITestContext context) {
        int numCorreos = 0;
        Date fechaDesde = new Date(System.currentTimeMillis() - (lastMinutes * 60000));
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectCorreosEnviados)) {            
            select.setString(1,context.getSuite().getName());
            select.setString(2,context.getName());
            select.setDate(3, fechaDesde);
            try (ResultSet resultado = select.executeQuery()) {
                numCorreos = (resultado.getInt("COUNT(*)"));
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return numCorreos;
    }
}
