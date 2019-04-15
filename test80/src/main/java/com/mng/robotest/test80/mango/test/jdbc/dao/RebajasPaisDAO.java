package com.mng.robotest.test80.mango.test.jdbc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class RebajasPaisDAO {
    public static String SQLSelectRebajasPais = 
        "SELECT REBAJAS, INICIO, FIN " +
        "  FROM REBAJAS_PAISCOMPRA " +
        " WHERE CODIGOPAIS = ? AND" +
        "  REBAJAS = 1 AND " +
        "  INICIO is not null AND " +
        "  FIN is not null";
    
    public static String SQLSelectMaxRebajasPais = 
    	"SELECT MAXREBAJAS " +
        "  FROM REBAJAS_PAISCOMPRA " +
        " WHERE CODIGOPAIS = ?";
    
    public static String SQLSelectLineasInvertidasPrendas = 
        	"SELECT INVERTIDAS_PRENDAS AS \"INVERTIDAS\"" +
            "  FROM REBAJAS_PAISCOMPRA " +
            " WHERE CODIGOPAIS = ?";    
    
    public static String SQLSelectLineasInvertidasAccesorios = 
        	"SELECT INVERTIDAS_ACC AS \"INVERTIDAS\"" +
            "  FROM REBAJAS_PAISCOMPRA " +
            " WHERE CODIGOPAIS = ?";    
    
    public static String SQLSelectCountrysInRebajas = 
        "SELECT CODIGOPAIS, INICIO, FIN " +
        "  FROM REBAJAS_PAISCOMPRA " +
        " WHERE " +
        "  REBAJAS = 1 AND " +
        "  INICIO is not null AND " +
        "  FIN is not null";
        
    /**
     * @param codigoPais en formato XXX
     * @return si están activadas o no las rebajas en dicho país
     */
    public static ArrayList<String> listCountryCodesInRebajas() throws Exception {
        ArrayList<String> listCountryCodes = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaHoyDate = new Date(Calendar.getInstance().getTime().getTime());
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectCountrysInRebajas)) {
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                    Date fechaIni = dateFormat.parse(resultado.getString("INICIO"));
                    Date fechaFin = dateFormat.parse(resultado.getString("FIN"));
                    if (fechaIni.getTime() < fechaHoyDate.getTime() &&
                        fechaFin.getTime() > fechaHoyDate.getTime()) {
                        listCountryCodes.add(resultado.getString("CODIGOPAIS"));
                    }
                }
            }
            
            return listCountryCodes;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }    
    
    /**
     * @param codigoPais en formato XXX
     * @return si están activadas o no las rebajas en dicho país
     */
    public static boolean isRebajasEnabledPais(String codigoPais) throws Exception {
        boolean rebajas = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaHoyDate = new Date(Calendar.getInstance().getTime().getTime());
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectRebajasPais)) {
            select.setString(1, codigoPais);
            try (ResultSet resultado = select.executeQuery()) {
                if (resultado.next()) {
                    int rebajasInt = resultado.getInt("REBAJAS");
                    if (rebajasInt==1) {
                        Date fechaIni = dateFormat.parse(resultado.getString("INICIO"));
                        Date fechaFin = dateFormat.parse(resultado.getString("FIN"));
                        if (fechaIni.getTime() < fechaHoyDate.getTime() &&
                            fechaFin.getTime() > fechaHoyDate.getTime()) {                           
                        	return true;
                        }
                    }
                }
            }
            
            return rebajas;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * @param codigoPais en formato XXX
     * @return el máximo de % de rebajas
     */
    public static int getMaxRebajas(String codigoPais) {
        int maxrebajas = 0;
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectMaxRebajasPais)) {
            select.setString(1, codigoPais);
            try (ResultSet resultado = select.executeQuery()) {
                if (resultado.next()) {
                    maxrebajas = resultado.getInt("MAXREBAJAS");
                }
            }
            
            return maxrebajas;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * @param codigoPais en formato XXX
     * @return el máximo de % de rebajas
     */
    public static List<String> getLineasInvertidas(String codigoPais, bloqueMenu menuType) {
    	String sql;
    	switch (menuType) {
    	case prendas:
    		sql = SQLSelectLineasInvertidasPrendas;
    		break;
    	case accesorios:
    	default:
    		sql = SQLSelectLineasInvertidasAccesorios;
    	}
    	
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(sql)) {
            select.setString(1, codigoPais);
            try (ResultSet resultado = select.executeQuery()) {
                if (resultado.next()) {
                    String linInvertidasCommaSeparated = resultado.getString("INVERTIDAS");
                    if (linInvertidasCommaSeparated!=null && "".compareTo(linInvertidasCommaSeparated)!=0) {
                    	StringTokenizer tokensLin = new StringTokenizer(linInvertidasCommaSeparated, ",");
                    	List<String> listLineas = new ArrayList<String>();
                    	while (tokensLin.hasMoreElements())
                    		listLineas.add(tokensLin.nextToken());
                    	
                    	return listLineas;
                    }
                }
            }
            
            return null;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
