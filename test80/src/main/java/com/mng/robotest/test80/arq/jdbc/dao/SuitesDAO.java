package com.mng.robotest.test80.arq.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.testng.ISuite;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.arq.jdbc.to.ResultTestRun;
import com.mng.robotest.test80.arq.jdbc.to.Suite;
import com.mng.robotest.test80.arq.utils.StateSuite;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.arq.utils.otras.Constantes;


public class SuitesDAO {
    public static String SQLSelectSuite = 
        "SELECT IDEXECSUITE, SUITE, STATE "  +
        "  FROM SUITES " +
        " WHERE IDEXECSUITE = ? ";    
    
    public static String SQLSelectSuiteByKey = 
        "SELECT IDEXECSUITE, SUITE, VERSION, BROWSER, CHANNEL, APP, RESULT_SCRIPT, RESULT_TNG, ERROR_SUITE_TNG, INICIO, FIN, TIME_MS, NUMBER_METHODS, COUNTRYS, URLBASE, PATH_REPORT, URL_REPORT, STATE " + 
        "  FROM SUITES  " + 
        " WHERE IDEXECSUITE = ? AND SUITE = ? " + 
        " ORDER BY INICIO";
    
    public static String SQLSelectSuitesByChannel =
        "SELECT IDEXECSUITE, SUITE, VERSION, BROWSER, CHANNEL, APP, RESULT_SCRIPT, RESULT_TNG, ERROR_SUITE_TNG, INICIO, FIN, TIME_MS, NUMBER_METHODS, COUNTRYS, URLBASE, PATH_REPORT, URL_REPORT, STATE " + 
        "  FROM SUITES  " + 
        " WHERE SUITE = ? AND CHANNEL = ? " + 
        " ORDER BY INICIO DESC";
    
    public static String SQLSelectSuitesDesdeFecha = 
            "SELECT IDEXECSUITE, SUITE, VERSION, BROWSER, CHANNEL, APP, RESULT_SCRIPT, RESULT_TNG, ERROR_SUITE_TNG, INICIO, FIN, TIME_MS, NUMBER_METHODS, COUNTRYS, URLBASE, PATH_REPORT, URL_REPORT, STATE " + 
            "  FROM SUITES  " + 
            " WHERE INICIO >= ? " +
            "ORDER BY INICIO";
    
    public static String SQLSelectSuitesDesdeFechaInState = 
        "SELECT IDEXECSUITE, SUITE, VERSION, BROWSER, CHANNEL, APP, RESULT_SCRIPT, RESULT_TNG, ERROR_SUITE_TNG, INICIO, FIN, TIME_MS, NUMBER_METHODS, COUNTRYS, URLBASE, PATH_REPORT, URL_REPORT, STATE " + 
        "  FROM SUITES  " + 
        " WHERE INICIO >= ? AND STATE = ? " +
        "ORDER BY INICIO";
        
    public static String SQLSelectMaxTNGIndex = 
        "SELECT MAX(TESTNG_INDEX) " + "FROM SUITES  ";
    
    public static String SQLInsertSuiteInit = 
        "INSERT INTO SUITES " + 
        "(IDEXECSUITE, SUITE, VERSION, CHANNEL, APP, BROWSER, INICIO, NUMBER_METHODS, URLBASE, COUNTRYS, PATH_REPORT, URL_REPORT, STATE) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
    public static String SQLUpdateEndSuite = 
        "UPDATE SUITES " +
        "SET " +
        "    RESULT_SCRIPT = ?, " +
        "    RESULT_TNG = ?, " +
        "    ERROR_SUITE_TNG = ?, " +
        "    FIN = ?, " +
        "    TIME_MS = ?, " +
        "    NUMBER_METHODS = ?, " + 
        "    TESTNG_INDEX = ?," +
        "    STATE = ? " +
        "WHERE SUITE = ? AND IDEXECSUITE = ?";
    
    public static String SQLUpdateStateSuite = 
        "UPDATE SUITES " +
        "SET " +
        "    STATE = ? " +
        "WHERE IDEXECSUITE = ?";
    
    public static String SQLDeleteHistorical = 
        "DELETE FROM SUITES " +
        "WHERE INICIO < ?;";    

    public static StateSuite getStateSuite(String executionId) {
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/)) {
            try (PreparedStatement select = conn.prepareStatement(SQLSelectSuite)) {
                select.setString(1, executionId);
                try (ResultSet resultado = select.executeQuery()) {
                    if (resultado.next()) 
                        return StateSuite.valueOf(resultado.getString("STATE"));
                    
                    return StateSuite.NOTEXISTS;
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Averigua si ya está grabado el registro en SUITE
     */
    public static boolean existsSuite(String executionId) {
        StateSuite stSuite = getStateSuite(executionId);
        switch (stSuite) {
        case NOTEXISTS:
            return false;
        default:
            return true;
        }
    }    
    
    public static int getMaxIndexTNG() {
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectMaxTNGIndex);
            ResultSet resultado = select.executeQuery()) {
            return (resultado.getInt("MAX(TESTNG_INDEX)"));
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Se obtienen la lista de suites a partir de una fecha
     */
    public static ArrayList<Suite> listSuitesDesde(final Date fechaDesde) {
        ArrayList<Suite> listSuites = new ArrayList<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectSuitesDesdeFecha)) {
            select.setDate(1, fechaDesde);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next())
                    listSuites.add(mapSuiteRowToObject(resultado));
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
    
    /**
     * Se obtienen la lista de suites a partir de una fecha en un estado determinado
     */
    public static ArrayList<Suite> listSuitesDesde(final Date fechaDesde, final StateSuite state) {
        ArrayList<Suite> listSuites = new ArrayList<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectSuitesDesdeFechaInState)) {
            select.setDate(1, fechaDesde);
            select.setString(2, state.toString());
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next())
                    listSuites.add(mapSuiteRowToObject(resultado));
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
    
    /**
     * Se obtienen los datos de una suite. Se almacenan en un vector aunque realmente siempre se retornar� un único registro.
     */
    public static Suite getSuite(indexSuite suite) {
        return getSuite(suite.getidExecSuite(), suite.getSuite());
    }

    public static Suite getSuite(String suiteExecId, String suite) {
        Suite suiteObj = new Suite();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/)) {
            try (PreparedStatement select = conn.prepareStatement(SQLSelectSuiteByKey)) {
                select.setString(1, suiteExecId);
                select.setString(2, suite);
                try (ResultSet resultado = select.executeQuery()) {
                    if (resultado.next())
                        suiteObj = mapSuiteRowToObject(resultado);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return suiteObj;
    }    
    
    public static ArrayList<Suite> getSuitesByChannel(String suite, String channel) {
        ArrayList<Suite> listSuites = new ArrayList<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/)) {
            try (PreparedStatement select = conn.prepareStatement(SQLSelectSuitesByChannel)) {
                select.setString(1, suite);
                select.setString(2, channel);
                try (ResultSet resultado = select.executeQuery()) {
                    while (resultado.next())
                        listSuites.add(mapSuiteRowToObject(resultado));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return listSuites;
    }    
    
    protected static Suite mapSuiteRowToObject(ResultSet rowSuite) throws SQLException {
        Suite suiteObj = new Suite();
        suiteObj.setIdExecution(rowSuite.getString("IDEXECSUITE"));
        suiteObj.setSuiteName(rowSuite.getString("SUITE"));
        suiteObj.setVersion(rowSuite.getString("VERSION"));
        suiteObj.setChannel(rowSuite.getString("CHANNEL"));
        suiteObj.setApplication(rowSuite.getString("APP"));
        suiteObj.setBrowser(rowSuite.getString("BROWSER"));
        suiteObj.setResultScript(rowSuite.getString("RESULT_SCRIPT"));
        suiteObj.setResultTng(rowSuite.getString("RESULT_TNG"));
        suiteObj.setErrorSuiteTng(rowSuite.getString("ERROR_SUITE_TNG"));
        suiteObj.setInicio(rowSuite.getString("INICIO"));
        suiteObj.setFin(rowSuite.getString("FIN"));
        suiteObj.setTimeMs(rowSuite.getString("TIME_MS"));
        suiteObj.setNumberMethods(rowSuite.getString("NUMBER_METHODS"));
        suiteObj.setCountrys(rowSuite.getString("COUNTRYS"));
        suiteObj.setUrlBase(rowSuite.getString("URLBASE"));
        suiteObj.setPathReport(rowSuite.getString("PATH_REPORT"));
        suiteObj.setUrlReport(rowSuite.getString("URL_REPORT"));
        suiteObj.setStateSuite(StateSuite.valueOf(rowSuite.getString("STATE")));
        return suiteObj;
    }
    
    public static void insertSuiteInit(ISuite suite) {
        try (Connection conn = Connector.getConnection()) {
            try (PreparedStatement insert = conn.prepareStatement(SQLInsertSuiteInit)) {
                insert.setString(1, suite.getXmlSuite().getParameter(Constantes.paramSuiteExecInCtx)) ;
                insert.setString(2, suite.getName());
                insert.setString(3, suite.getXmlSuite().getParameter(Constantes.paramVersionSuite));
                insert.setString(4, suite.getXmlSuite().getParameter(Constantes.paramChannelSuite));
                insert.setString(5, suite.getXmlSuite().getParameter(Constantes.paramAppEcomSuite));
                insert.setString(6, suite.getXmlSuite().getParameter(Constantes.paramBrowser));
                insert.setDate(7, new java.sql.Date(System.currentTimeMillis()));
                insert.setInt(8, 0);
                insert.setString(9, suite.getXmlSuite().getParameter(Constantes.paramUrlBase));
                insert.setString(10, suite.getXmlSuite().getParameter(Constantes.paramCountrys));
                insert.setString(11, fmwkTest.getPathReportHTML(fmwkTest.getOutputDirectorySuite(suite)));

                String pathToReport = fmwkTest.getPathReportHTML(fmwkTest.getOutputDirectorySuite(suite));
                String reportTSuiteURL = utils.obtainDNSFromFile(pathToReport, suite.getXmlSuite().getParameter(Constantes.paramApplicationDNS)).replace("\\", "/");
                insert.setString(12, reportTSuiteURL);
                insert.setString(13,  StateSuite.STARTED.toString());
                
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
    
    public static void updateEndSuiteFromCtx(ResultTestRun resultTestRun, ITestContext context) {
        try (Connection conn = Connector.getConnection()) {
            String idExecSuite = context.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx);
            StateSuite stateSuite = SuitesDAO.getStateSuite(idExecSuite);
            StateSuite newStateSuite;
            switch (stateSuite) {
            case STOPPING:
                newStateSuite = StateSuite.STOPPED;
                break;
            default:
            case STARTED:
                newStateSuite = StateSuite.FINISHED;
            }
            
            try (PreparedStatement update = conn.prepareStatement(SQLUpdateEndSuite)) {
                update.setInt(1, resultTestRun.maxResultScript.getIdNumerid());
                update.setInt(2, resultTestRun.maxresultTNG.getIdNumerid());
                update.setBoolean(3, context.getSuite().getSuiteState().isFailed());
                update.setDate(4, new Date(context.getEndDate().getTime()));
                update.setFloat(5, context.getEndDate().getTime() - context.getStartDate().getTime());
                update.setInt(6, resultTestRun.numberMethods);
                update.setInt(7, SuitesDAO.getMaxIndexTNG() + 1);
                update.setString(8,  newStateSuite.toString());
                update.setString(9, context.getSuite().getName());
                update.setString(10, idExecSuite) ;
                update.executeUpdate();
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

    public static void updateStateSuite(StateSuite stateSuite, String idExecSuite) {
        try (Connection conn = Connector.getConnection()) {
            try (PreparedStatement update = conn.prepareStatement(SQLUpdateStateSuite)) {
                update.setString(1, stateSuite.toString());
                update.setString(2, idExecSuite);
                update.executeUpdate();
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
    
    public static void deleteHistorical(final Date fechaDesde) {
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteHistorical)) {
            delete.setDate(1, fechaDesde);
            delete.executeUpdate();
         } 
         catch (SQLException ex) {
             throw new RuntimeException(ex);
         } 
         catch (ClassNotFoundException ex) {
             throw new RuntimeException(ex);
         }    
    }    
}
