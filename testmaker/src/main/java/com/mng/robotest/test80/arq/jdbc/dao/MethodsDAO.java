package com.mng.robotest.test80.arq.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.testng.ITestContext;
import org.testng.ITestResult;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.arq.jdbc.to.ResultMethod;
import com.mng.robotest.test80.arq.jdbc.to.ResultTestRun;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.data.TestMakerContext;


public class MethodsDAO {
    public static String SQLSelectMethodsTestRun =
        "SELECT IDEXECSUITE, SUITE, TEST, METHOD, DESCRIPTION, RESULT_SCRIPT, RESULT_TNG, INICIO, FIN, TIME_MS, NUMBER_STEPS, CLASS_SIGNATURE, INSTANCIA " + 
        "  FROM METHODS  " + 
        " WHERE IDEXECSUITE = ? AND SUITE = ? AND TEST = ? " +
        " ORDER BY TEST, INICIO, METHOD";
    
    public static String SQLInsertMethod = 
        "INSERT INTO METHODS " + 
        "(IDEXECSUITE, SUITE, TEST, METHOD, DESCRIPTION, RESULT_SCRIPT, RESULT_TNG, INICIO, FIN, TIME_MS, NUMBER_STEPS, CLASS_SIGNATURE, INSTANCIA) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    public static String SQLDeleteHistorical = 
        "DELETE FROM METHODS " +
        "WHERE INICIO < ?;";
    
    /**
     * Se obtiene la lista de métodos asociados a un TestRun
     * @param context de ejecución del TestRun (contiene también información de la Suite)
     * @return lista de métodos asociados a un TestRun
     */
    public static Vector<HashMap<String, Object>> listMethodsTestRun(indexSuite suiteId, String testRun) {
        Vector<HashMap<String, Object>> vectorList = new Vector<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectMethodsTestRun)) {
            select.setString(1, suiteId.getidExecSuite());
            select.setString(2, suiteId.getSuite());
            select.setString(3, testRun);
            try (ResultSet resultado = select.executeQuery()) {
                HashMap<String, Object> tmpHash;
                while (resultado.next()) {
                    vectorList.add(new HashMap<>());
                    tmpHash = vectorList.lastElement();
                    tmpHash.put("IDEXECSUITE", resultado.getString("IDEXECSUITE"));
                    tmpHash.put("SUITE", resultado.getString("SUITE"));
                    tmpHash.put("TEST", resultado.getString("TEST"));
                    tmpHash.put("METHOD", resultado.getString("METHOD"));
                    tmpHash.put("DESCRIPTION", resultado.getString("DESCRIPTION"));
                    tmpHash.put("RESULT_SCRIPT", resultado.getString("RESULT_SCRIPT"));
                    tmpHash.put("RESULT_TNG", resultado.getString("RESULT_TNG"));
                    tmpHash.put("INICIO", resultado.getString("INICIO"));
                    tmpHash.put("FIN", resultado.getString("FIN"));
                    tmpHash.put("TIME_MS", resultado.getString("TIME_MS"));
                    tmpHash.put("NUMBER_STEPS", resultado.getString("NUMBER_STEPS"));
                    tmpHash.put("CLASS_SIGNATURE", resultado.getString("CLASS_SIGNATURE"));
                    tmpHash.put("INSTANCIA", resultado.getString("INSTANCIA"));
                }
            }

            return vectorList;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }    
    
    public static void inserMethod(ResultMethod resultMethod, ITestResult tr) {
        ITestContext context = tr.getTestContext();
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(context);
        try (Connection conn = Connector.getConnection();
            PreparedStatement insert = conn.prepareStatement (SQLInsertMethod )) {
            insert.setString(1, testMakerCtx.getIdSuiteExecution());
            insert.setString(2, testMakerCtx.getInputData().getNameSuite());
            insert.setString(3, context.getName());
            insert.setString(4, fmwkTest.getMethodWithFactory(tr.getMethod().getMethod(), context));
            insert.setString(5, tr.getMethod().getDescription());
            insert.setInt(6, utils.getEstadoMethod(resultMethod.maxResultadoStep.getIdNumerid(), tr.getStatus()).getIdNumerid());
            insert.setInt(7, tr.getStatus());
            insert.setDate(8, new Date(tr.getStartMillis())); 
            insert.setDate(9, new Date(tr.getEndMillis()));
            insert.setLong(10, tr.getEndMillis() - tr.getStartMillis());
            insert.setInt(11, resultMethod.numberSteps);
            insert.setString(12, tr.getTestClass().toString()); 
            int posArroba = tr.getInstance().toString().lastIndexOf("@");
            
            if (posArroba>=0) {
                insert.setString(13, tr.getInstance().toString().substring(posArroba));
            }
            else {
                insert.setString(13, "");
            }
            insert.executeUpdate();
         } 
         catch (SQLException ex) {
             throw new RuntimeException(ex);
         } 
         catch (ClassNotFoundException ex) {
             throw new RuntimeException(ex);
         }    
    }
    
    public static ResultTestRun getResultTestRunAccordingMethods(indexSuite suiteId, String testRun) {
        ResultTestRun resultTestRun = new ResultTestRun();
        resultTestRun.numberMethods = 0;
        resultTestRun.maxResultScript = State.Ok;
        resultTestRun.maxresultTNG = State.Ok;
        Vector<HashMap<String, Object>> listMethods = listMethodsTestRun(suiteId, testRun);
        
        for (HashMap<String, Object> methodHash : listMethods) {
            resultTestRun.numberMethods+=1;
            int maxResultScript = Integer.valueOf((String)methodHash.get("RESULT_SCRIPT")).intValue();
            int maxresultTNG = Integer.valueOf((String)methodHash.get("RESULT_TNG")).intValue();
            if (maxResultScript > resultTestRun.maxResultScript.getCriticity()) {
                resultTestRun.maxResultScript = State.getState(maxResultScript);
            }
            if (maxresultTNG > resultTestRun.maxresultTNG.getCriticity()) {
                resultTestRun.maxresultTNG = State.getState(maxresultTNG);
            }
        }
        
        return resultTestRun;
    }
    
    /**
     * Devuelve el número de métodos de una suite en un determinado estado
     */
    public static int numMethdsInState(final String fechaSuite, final String nombreSuite, final State estado) {
        int numero = 0;
        String queryFiltr = "";
        switch (estado.getLevel()) {
        case OK:
        case INFO:
        case Warn:
        case Defect:            
            queryFiltr = " AND RESULT_TNG = 1 AND RESULT_SCRIPT = " + estado.getIdNumerid() + " ";
            break;            
        case NOK:
            queryFiltr = " AND (RESULT_TNG = 2 OR RESULT_SCRIPT = " + estado.getIdNumerid() + ") ";
            break;
        case SKIP:
            queryFiltr = " AND RESULT_TNG = " + estado.getIdNumerid() + " ";
            break;
        default:
            break;
        }
        
        String SQLCountMethods = 
            "SELECT COUNT(*) " + 
            "  FROM METHODS  " + 
            " WHERE IDEXECSUITE = ? AND SUITE = ? " + queryFiltr + 
            " ORDER BY TEST, INICIO, METHOD";

        // Obtenemos el número de métodos en un estado determinado
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLCountMethods)) {
            select.setString(1, fechaSuite);
            select.setString(2, nombreSuite);
            try (ResultSet resultado = select.executeQuery()) {
                numero = resultado.getInt("COUNT(*)");
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return numero;
    }    
    
    public static void deleteHistorical(final Date fechaDesde) {
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement (SQLDeleteHistorical)) {
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
