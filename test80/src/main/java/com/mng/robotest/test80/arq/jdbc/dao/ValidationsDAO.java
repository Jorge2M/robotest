package com.mng.robotest.test80.arq.jdbc.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.arq.utils.otras.Constantes;

@SuppressWarnings("javadoc")
public class ValidationsDAO {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static String SQLSelectValidationsStep = 
        "SELECT IDEXECSUITE, SUITE, TEST, METHOD, STEP_NUMBER, VALIDATION_NUMBER, DESCRIPTION, RESULTADO, LIST_RESULTS " + 
        "  FROM VALIDATIONS  " + 
        " WHERE IDEXECSUITE = ? AND SUITE = ? AND TEST = ? AND METHOD = ? AND STEP_NUMBER = ? " + 
        " ORDER BY TEST, METHOD, STEP_NUMBER, VALIDATION_NUMBER";
    
    public static String SQLSelectMaxValidation = 
        "SELECT MAX(VALIDATION_NUMBER) " + 
        "FROM VALIDATIONS  " + 
        "WHERE IDEXECSUITE = ? AND SUITE = ? AND TEST = ? AND METHOD = ? AND STEP_NUMBER = ? ";
    
    public static String SQLInsertValidation =
        "INSERT INTO VALIDATIONS VALUES (?,?,?,?,?,?,?,?,?)";

    public static String SQLUpdateStep = 
        "UPDATE STEPS " + 
        "SET    RESULTADO = MAX (RESULTADO, ?), " + 
        "       NUM_VALIDATIONS = (NUM_VALIDATIONS + 1) " + 
        "WHERE  IDEXECSUITE = ? " + "AND SUITE = ? " + "AND TEST = ? " + "AND METHOD = ? " + "AND STEP_NUMBER = ? ";    
    
    public static String SQLDeleteHistorical = 
        "DELETE FROM VALIDATIONS " +
        "WHERE IDEXECSUITE NOT IN (SELECT IDEXECSUITE FROM SUITES);";
    
    /**
     * Se obtienen la lista de validations de un step
     */
    public static Vector<HashMap<String, Object>> listValidationsStep(final indexSuite suiteId, final String testRun, final String method, final String step) {
        Vector<HashMap<String, Object>> vectorList = new Vector<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectValidationsStep)) {
            select.setString(1, suiteId.getidExecSuite());
            select.setString(2, suiteId.getSuite());
            select.setString(3, testRun);
            select.setString(4, method);
            select.setString(5, step);
            try (ResultSet resultado = select.executeQuery()) {
                HashMap<String, Object> tmpHash = null;
                while (resultado.next()) {
                    vectorList.add(new HashMap<>());
                    tmpHash = vectorList.lastElement();
                    tmpHash.put("IDEXECSUITE", resultado.getString("IDEXECSUITE"));
                    tmpHash.put("SUITE", resultado.getString("SUITE"));
                    tmpHash.put("TEST", resultado.getString("TEST"));
                    tmpHash.put("METHOD", resultado.getString("METHOD"));
                    tmpHash.put("STEP_NUMBER", resultado.getString("STEP_NUMBER"));
                    tmpHash.put("VALIDATION_NUMBER", resultado.getString("VALIDATION_NUMBER"));
                    tmpHash.put("DESCRIPTION", resultado.getString("DESCRIPTION"));
                    tmpHash.put("RESULTADO", resultado.getString("RESULTADO"));
                    tmpHash.put("LIST_RESULTS", resultado.getString("LIST_RESULTS"));
                }
            }
        } 
        catch (SQLException ex) {
            pLogger.error("Problem in select Validations. IDEXECSUITE {}, SUITE {}, TEST {}, METHOD {}, STEP_NUMBER {}", 
                suiteId.getidExecSuite(), suiteId.getSuite(), testRun, method, step, ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } 
            
        return vectorList;
    }

    public static int selectNextValidation(Method method, int stepNumber, ITestContext ctx) {
        int nextValidation = 0;
        String methodWithFactory = fmwkTest.getMethodWithFactory(method, ctx);
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/)) {
            try (PreparedStatement select = conn.prepareStatement(SQLSelectMaxValidation)) {
                select.setString(1, ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
                select.setString(2, ctx.getSuite().getName());
                select.setString(3, ctx.getName());
                select.setString(4, methodWithFactory);
                select.setInt(5, stepNumber);
    
                try (ResultSet resultado = select.executeQuery()) {
                    nextValidation = resultado.getInt("MAX(VALIDATION_NUMBER)") + 1;
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }    
        
        return nextValidation;
    }
    
    public static void insertValidationInStep(String descripValidac, DatosStep datosStep, Method method, ITestContext ctx) {
        String methodWithFactory = fmwkTest.getMethodWithFactory(method, ctx);
        int validationNumber = ValidationsDAO.selectNextValidation(method, datosStep.getStepNumber(), ctx);
        try (Connection conn = Connector.getConnection()) {
            try (PreparedStatement insert = conn.prepareStatement(SQLInsertValidation)) {
                insert.setString(1, ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
                insert.setString(2, ctx.getSuite().getName());
                insert.setString(3, ctx.getName());
                insert.setString(4, methodWithFactory);
                insert.setInt(5, datosStep.getStepNumber());
                insert.setInt(6, validationNumber);
                insert.setString(7, descripValidac);
                if (datosStep.getExcepExists())
                    insert.setInt(8, State.Nok.getIdNumerid());
                else
                    insert.setInt(8, datosStep.getResultSteps().getIdNumerid());
    
                List<Integer> listVals = datosStep.getSimpleValidacsList();
                if (listVals!=null)
                    insert.setString(9, listVals.toString().replace("[","").replace("]",""));
                else
                    insert.setString(9, "");
    
                insert.executeUpdate();
            }
            
            try (PreparedStatement updateStep = conn.prepareStatement(SQLUpdateStep)) {
                if (datosStep.getExcepExists())
                    updateStep.setInt(1, State.Nok.getIdNumerid());
                else
                    updateStep.setInt(1, datosStep.getResultSteps().getIdNumerid());
    
                updateStep.setString(2, ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
                updateStep.setString(3, ctx.getSuite().getName());
                updateStep.setString(4, ctx.getName());
                updateStep.setString(5, methodWithFactory);
                updateStep.setInt(6, datosStep.getStepNumber());
                updateStep.executeUpdate();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }    
    }
    
    public static void deleteHistorical() {
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteHistorical)) {
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
