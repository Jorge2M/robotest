package com.mng.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.PersistorDataI;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;


public class SuitesDAO {
	
	private final PersistorDataI persistor;
	
	private static final String ListFieldsSuiteTable = 
		"IDEXECSUITE, " +
        "SUITE, " + 
        "VERSION, " + 
        "BROWSER, " + 
        "CHANNEL, " + 
        "APP, " + 
        "RESULT, " + 
        "INICIO, " + 
        "FIN, " + 
        "TIME_MS, " + 
        "NUMBER_METHODS, " + 
        "PATH_REPORT, " + 
        "URL_REPORT, " + 
        "MORE_INFO, " + 
        "URLBASE, " + 
        "STATE_EXECUTION ";
	
    private static final String SQLInsertSuiteInit = 
        "INSERT INTO SUITES (" + ListFieldsSuiteTable + ")" +
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    private static final String SQLSelectSuite = 
        "SELECT " + ListFieldsSuiteTable  +
        "  FROM SUITES " +
        "WHERE IDEXECSUITE = ? " +
        "ORDER BY IDEXECSUITE DESC";    
    
    private static final String SQLSelectSuitesHigherId = 
    	"SELECT " + ListFieldsSuiteTable + 
    	"WHERE IDEXECSUITE > ?";
    
    private static final String SQLDeleteSuitesBeforeId = 
        "DELETE FROM SUITES " +
        "WHERE IDEXECSUITE <= ? ";
    
    private static final String SQLSelectSuitesIdDesc = 
    	"SELECT " + ListFieldsSuiteTable + 
    	"FROM SUITES  " + 
    	"ORDER BY IDEXECSUITE DESC";

    public SuitesDAO(PersistorDataI persistor) {
    	this.persistor = persistor;
    }
    
    public List<SuiteData> getListSuitesIdDesc() throws Exception {
        List<SuiteData> listSuites = new ArrayList<>();
        try (Connection conn = persistor.getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectSuitesIdDesc)) {
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                	listSuites.add(getSuite(resultado));
                }
            }
            return listSuites;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SuiteData getSuite(String suiteExecId) throws Exception {
        SuiteData suiteData = new SuiteData();
        try (Connection conn = persistor.getConnection()) {
            try (PreparedStatement select = conn.prepareStatement(SQLSelectSuite)) {
                select.setString(1, suiteExecId);
                try (ResultSet resultado = select.executeQuery()) {
                    if (resultado.next()) {
                        suiteData = getSuite(resultado);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return suiteData;
    }    
    
    private SuiteData getSuite(ResultSet rowSuite) throws Exception {
        SuiteData suiteData = new SuiteData();
        suiteData.setIdExecSuite(rowSuite.getString("IDEXECSUITE"));
        suiteData.setName(rowSuite.getString("SUITE"));
        suiteData.setVersion(rowSuite.getString("VERSION"));
        suiteData.setChannel(Channel.valueOf(rowSuite.getString("CHANNEL")));
        suiteData.setApp(rowSuite.getString("APP"));
        suiteData.setWebDriverType(WebDriverType.valueOf(rowSuite.getString("BROWSER")));
        suiteData.setResult(State.valueOf(rowSuite.getString("RESULT")));
        
        String inicioDate = rowSuite.getString("INICIO");
        suiteData.setInicioDate(getDateFormat().parse(inicioDate));
        String finDate = rowSuite.getString("FIN");
        suiteData.setInicioDate(getDateFormat().parse(finDate));
        
        suiteData.setDurationMillis(rowSuite.getFloat("TIME_MS"));
        suiteData.setNumberTestCases(rowSuite.getInt("NUMBER_METHODS"));
        suiteData.setMoreInfo(rowSuite.getString("MORE_INFO"));
        suiteData.setUrlBase(rowSuite.getString("URLBASE"));
        suiteData.setPathReportHtml(rowSuite.getString("PATH_REPORT"));
        suiteData.setUrlReportHtml(rowSuite.getString("URL_REPORT"));
        suiteData.setStateExecution(StateExecution.valueOf(rowSuite.getString("STATE_EXECUTION")));
        return suiteData;
    }
    
    public void insertSuite(SuiteData suiteData) {
        try (Connection conn = persistor.getConnection()) {
            try (PreparedStatement insert = conn.prepareStatement(SQLInsertSuiteInit)) {
    	        insert.setString(1, suiteData.getIdExecSuite());
    	        insert.setString(2, suiteData.getName()); 
    	        insert.setString(3, suiteData.getVersion()); 
    	        insert.setString(4, suiteData.getWebDriverType().name()); 
    	        insert.setString(5, suiteData.getChannel().name()); 
    	        insert.setString(6, suiteData.getApp());
    	        insert.setString(7, suiteData.getResult().name());
    	        insert.setString(8, getDateFormat().format(suiteData.getInicioDate()));
    	        insert.setString(9, getDateFormat().format(suiteData.getFinDate()));
    	        insert.setFloat(10, suiteData.getDurationMillis());
    	        insert.setInt(11, suiteData.getNumberTestCases());
    	        insert.setString(12, suiteData.getPathReportHtml());
    	        insert.setString(13,  suiteData.getUrlReportHtml());
    	        insert.setString(14, suiteData.getMoreInfo());
    	        insert.setString(15, suiteData.getUrlBase());
    	        insert.setString(16, suiteData.getStateExecution().name());
                insert.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SuiteData getSuiteBefore(Date fechaHasta) throws Exception {
    	for (SuiteData suite : getListSuitesIdDesc()) {
    		if (suite.getInicioDate().before(fechaHasta)) {
    			return suite;
    		}
    	}
    	return null;
    }    
    
    public SuiteData getSuiteAfter(Date fechaDesde) throws Exception {
    	for (SuiteData suite : getListSuitesIdDesc()) {
    		if (suite.getInicioDate().after(fechaDesde)) {
    			return suite;
    		}
    	}
    	return null;
    } 
    
    public List<SuiteData> getListSuitesAfter(Date fechaDesde) throws Exception {
        List<SuiteData> listSuites = new ArrayList<>();
    	SuiteData suite = getSuiteAfter(fechaDesde);
        try (Connection conn = persistor.getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectSuitesHigherId)) {
        	select.setString(1, suite.getIdExecSuite());
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                	listSuites.add(getSuite(resultado));
                }
            }
            return listSuites;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void deleteSuitesBefore(String idSuite) {
        try (Connection conn = persistor.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteSuitesBeforeId)) {
            delete.setString(1, idSuite);
            delete.executeUpdate();
         } 
         catch (SQLException ex) {
             throw new RuntimeException(ex);
         } 
         catch (ClassNotFoundException ex) {
             throw new RuntimeException(ex);
         }    
    }    
    
    private static SimpleDateFormat getDateFormat() {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
