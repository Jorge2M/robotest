package com.mng.robotest.test80.arq.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.arq.jdbc.to.ResultTestRun;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.selenium.BStackDataMovil;


public class TestRunsDAO {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static String SQLSelectTestRunsSuite = 
        "SELECT IDEXECSUITE, SUITE, TEST, DEVICE, RESULT_SCRIPT, RESULT_TNG, INICIO, FIN, TIME_MS, NUMBER_METHODS, BROWSER " + 
        "  FROM TESTRUNS  " + 
        " WHERE IDEXECSUITE = ? " + 
        "   AND SUITE = ? " +
        " ORDER BY TEST, INICIO";

    public static String SQLInsertTestRun = 
        "INSERT INTO TESTRUNS " + 
        "(IDEXECSUITE, SUITE, TEST, DEVICE, RESULT_SCRIPT, RESULT_TNG, ERROR_SUITE_TNG, INICIO, FIN, TIME_MS, NUMBER_METHODS, BROWSER) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    
    public static String SQLDeleteHistorical = 
        "DELETE FROM TESTRUNS " +
        "WHERE INICIO < ?;";    
    
    /**
     * Se obtiene la lista de testruns asociados a una suite
     * @param context de ejecución del TestRun (contiene también información de la Suite)
     * @return lista de testruns asociados a una Suite
     */
    public static Vector<HashMap<String,Object>> listTestRunsSuite(final indexSuite suiteId) {
        Vector<HashMap<String, Object>> vectorList = new Vector<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectTestRunsSuite)) {
            select.setString(1, suiteId.getidExecSuite());
            select.setString(2, suiteId.getSuite());
            try (ResultSet resultado = select.executeQuery()) {
                HashMap<String, Object> tmpHash;
                while (resultado.next()) {
                    vectorList.add(new HashMap<>());
                    tmpHash = vectorList.lastElement();
                    tmpHash.put("IDEXECSUITE", resultado.getString("IDEXECSUITE"));
                    tmpHash.put("SUITE", resultado.getString("SUITE"));
                    tmpHash.put("TEST", resultado.getString("TEST"));
                    tmpHash.put("DEVICE", resultado.getString("DEVICE"));
                    tmpHash.put("RESULT_SCRIPT", resultado.getString("RESULT_SCRIPT"));
                    tmpHash.put("RESULT_TNG", resultado.getString("RESULT_TNG"));
                    tmpHash.put("INICIO", resultado.getString("INICIO"));
                    tmpHash.put("FIN", resultado.getString("FIN"));
                    tmpHash.put("TIME_MS", resultado.getString("TIME_MS"));
                    tmpHash.put("NUMBER_METHODS", resultado.getString("NUMBER_METHODS"));
                    tmpHash.put("BROWSER", resultado.getString("BROWSER"));
                }
            }

            return vectorList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }    

    public static void insertFromCtx(ResultTestRun resultStep, ITestContext ctx) {
        try (Connection conn = Connector.getConnection()) {
            String idExecSuite = ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx);
            try (PreparedStatement insert = conn.prepareStatement (SQLInsertTestRun)) {
                insert.setString(1, idExecSuite) ;
                insert.setString(2, ctx.getSuite().getName());
                insert.setString(3, ctx.getName()); 
                
                //De momento en DEVICE introducimos el dispositivo de BrowserStack
                String device = "";
                if (null != ctx.getCurrentXmlTest().getParameter(BStackDataMovil.device_paramname))
                    device = ctx.getCurrentXmlTest().getParameter(BStackDataMovil.device_paramname);
                
                insert.setString(4, device); //
                insert.setInt(5, resultStep.maxResultScript.getIdNumerid());
                insert.setInt(6, resultStep.maxresultTNG.getIdNumerid());
                insert.setBoolean(7, ctx.getSuite().getSuiteState().isFailed());
                insert.setDate(8, new Date(ctx.getStartDate().getTime()));
                insert.setDate(9, new Date(ctx.getEndDate().getTime()));
                insert.setFloat(10, ctx.getEndDate().getTime() - ctx.getStartDate().getTime());
                insert.setInt(11, resultStep.numberMethods);
                insert.setString(12, (String)ctx.getAttribute("bpath"));
                insert.executeUpdate();
            } 
            catch (SQLException ex) {
                pLogger.error("Error insert TestRun. IDEXECSUITE {}, SUITE {}, TEST {}", idExecSuite, ctx.getSuite().getName(), ctx.getName(), ex);
                throw new RuntimeException(ex);
            }          
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
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
