package com.mng.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.testng.ITestContext;
import org.testng.ITestResult;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.domain.data.TestRunData;
import com.mng.testmaker.repository.jdbc.Connector;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;


public class TestCasesDAO {
	
    public static String SQLSelectTestCasesSuite =
        "SELECT " +
        	"IDEXECSUITE, " + 
        	"SUITE, " + 
        	"TESTRUN, " + 
        	"NAME, " + 
        	"DESCRIPTION, " + 
        	"RESULT, " + 
        	"INICIO, " + 
        	"FIN, " + 
        	"TIME_MS, " + 
        	"NUMBER_STEPS, " + 
        	"CLASS_SIGNATURE, " + 
        	"INSTANCIA " + 
        "FROM TESTCASES " + 
        "WHERE IDEXECSUITE = ?";
    
    public static String SQLInsertMethod = 
        "INSERT INTO METHODS " + 
        "(IDEXECSUITE, SUITE, TEST, METHOD, DESCRIPTION, RESULT_SCRIPT, RESULT_TNG, INICIO, FIN, TIME_MS, NUMBER_STEPS, CLASS_SIGNATURE, INSTANCIA) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    public static String SQLDeleteHistorical = 
        "DELETE FROM METHODS " +
        "WHERE INICIO < ?;";
    
    public static List<TestCaseData> getListTestCases(String idSuite) throws Exception {
    	List<TestCaseData> listTestCases = new ArrayList<>();
        try (Connection conn = Connector.getConnection(true);
            PreparedStatement select = conn.prepareStatement(SQLSelectTestCasesSuite)) {
            select.setString(1, idSuite);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                	listTestCases.add(getTestCase(resultado));
                }
            }
            return listTestCases;
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }    
    
    private static TestCaseData getTestCase(ResultSet rowTestRun) throws Exception {
    	TestCaseData testCaseData = new TestCaseData();
    	
    	testCaseData.setIdExecSuite(rowTestRun.getString("IDEXECSUITE"));
    	testCaseData.setSuiteName(rowTestRun.getString("SUITE")); 
    	testCaseData.setTestRunName(rowTestRun.getString("SUITE"));  
    	testCaseData.setName(rowTestRun.getString("NAME"));
    	testCaseData.setDescription(rowTestRun.getString("DESCRIPTION"));  
    	testCaseData.setResult(State.valueOf(rowTestRun.getString("RESULT")));
    	
        String inicioDate = rowTestRun.getString("INICIO");
        testCaseData.setInicioDate(getDateFormat().parse(inicioDate));
        String finDate = rowTestRun.getString("FIN");
        testCaseData.setInicioDate(getDateFormat().parse(finDate));
        testCaseData.setDurationMillis(rowTestRun.getFloat("TIME_MS"));
    	
    	testCaseData.setNumberSteps(rowTestRun.getInt("NUMBER_STEPS"));  
    	testCaseData.setClassSignature(rowTestRun.getString("CLASS_SIGNATURE"));  
    	testCaseData.setInstance(rowTestRun.getString("INSTANCIA"));  

    	return testCaseData;
    }
    
    private static SimpleDateFormat getDateFormat() {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    public static void inserMethod(ResultMethod resultMethod, ITestResult tr) {
        ITestContext context = tr.getTestContext();
        try (Connection conn = Connector.getConnection();
            PreparedStatement insert = conn.prepareStatement (SQLInsertMethod )) {
            insert.setString(1, context.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
            insert.setString(2, context.getSuite().getName());
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
