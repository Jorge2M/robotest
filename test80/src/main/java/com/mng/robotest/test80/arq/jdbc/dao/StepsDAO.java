package com.mng.robotest.test80.arq.jdbc.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.testng.ITestContext;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.arq.jdbc.to.ResultMethod;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.arq.utils.otras.Constantes;

@SuppressWarnings("javadoc")
public class StepsDAO {

    public static String SQLSelectLastStepMethod = 
        "SELECT MAX(STEP_NUMBER) " + 
        "  FROM STEPS  " + 
        " WHERE IDEXECSUITE = ? AND SUITE = ? AND TEST = ? AND METHOD = ? ";
    
    public static String SQLSelectStepsMethod = 
        "SELECT IDEXECSUITE, SUITE, TEST, METHOD, STEP_NUMBER, DESCRIPTION, RES_EXPECTED, RESULTADO, EXCEPCION, INICIO, FIN, TIME_MS, NUM_VALIDATIONS, IMAGE, HTML, TYPE_PAGE " + 
        "  FROM STEPS  " + 
        " WHERE IDEXECSUITE = ? AND SUITE = ? AND TEST = ? AND METHOD = ? " + 
        " ORDER BY TEST, INICIO, METHOD, STEP_NUMBER";
    
    public static String SQLInsertStep = 
        "INSERT INTO STEPS " + 
        "(IDEXECSUITE, SUITE, TEST, METHOD, STEP_NUMBER, DESCRIPTION, RES_EXPECTED, RESULTADO, EXCEPCION, INICIO, FIN, TIME_MS, NUM_VALIDATIONS, IMAGE, HTML, TYPE_PAGE) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String SQLDeleteHistorical = 
        "DELETE FROM STEPS " +
        "WHERE INICIO < ?;";    
    
    public static int grabStep(DatosStep datosStep, Method method, ITestContext ctx) {
//    	if (datosStep.getStepNumber()==0) {
//	        int stepNumber = StepsDAO.getNextMethodStep(method, ctx);
//	        datosStep.setStepNumber(stepNumber);
//    	}
    	
        String methodWithFactory = fmwkTest.getMethodWithFactory(method, ctx);
        if (datosStep.getHoraFin()==null) {
            datosStep.setHoraFin(new Date(System.currentTimeMillis()));
        }
    
//        System.out.println("Inicio insert en STEP");
//        System.out.println("IDEXECSUITE: " + ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
//        System.out.println("SUITE: " + ctx.getSuite().getName());
//        System.out.println("TEST: " + ctx.getName());
//        System.out.println("METHOD: " + methodWithFactory);
//        System.out.println("NUMBER: " + datosStep.getStepNumber());
        
        try (Connection conn = Connector.getConnection();
            PreparedStatement insert = conn.prepareStatement(SQLInsertStep)) {
            insert.setString(1, ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
            insert.setString(2, ctx.getSuite().getName());
            insert.setString(3, ctx.getName());
            insert.setString(4, methodWithFactory);
            insert.setInt(5, datosStep.getStepNumber());
            insert.setString(6, datosStep.getDescripcion());
            insert.setString(7, datosStep.getResExpected());
            insert.setInt(8, datosStep.getResultSteps().getIdNumerid());
            insert.setBoolean(9, datosStep.getExcepExists());
            insert.setDate(10, datosStep.getHoraInicio());
            insert.setDate(11, datosStep.getHoraFin());
            insert.setFloat(12, datosStep.getHoraFin().getTime() - datosStep.getHoraInicio().getTime());
            insert.setInt(13, 0);
            insert.setInt(16, datosStep.getTypePage());
            insert.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }    
        
        return (datosStep.getStepNumber());
    }
    
    
    public static ResultMethod getResultMethodAccordingSteps(indexSuite suiteId, String testRun, String method) {
        ResultMethod resultMethod = new ResultMethod();
        resultMethod.numberSteps = 0;
        resultMethod.maxResultadoStep = State.Ok;
        
        Vector<HashMap<String, Object>> listStepsMethod = listStepsMethod(suiteId, testRun, method);
        for (HashMap<String, Object> stepHash : listStepsMethod) {
            resultMethod.numberSteps+=1;
            int maxResult = Integer.valueOf((String)stepHash.get("RESULTADO")).intValue();
            if (maxResult > resultMethod.maxResultadoStep.getCriticity()) 
                resultMethod.maxResultadoStep = State.getState(maxResult);
        }
        
        return resultMethod;
    }
    
    /**
     * Se obtiene el último step del método y se le suma 1
     */
    public static int getNextMethodStep(Method method, ITestContext context) {
        int stepNumber = 0;
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectLastStepMethod)) {
            select.setString(1, context.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx));
            select.setString(2, context.getSuite().getName());
            select.setString(3, context.getName());
            select.setString(4, fmwkTest.getMethodWithFactory(method, context));
            try (ResultSet resultado = select.executeQuery()) {
                stepNumber = resultado.getInt("MAX(STEP_NUMBER)") + 1;
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return stepNumber;
    }
    
    /**
     * Se obtienen la lista de steps de una suite / testrun / method
     */
    public static Vector<HashMap<String, Object>> listStepsMethod(indexSuite suiteId, String testRun, String method) {
        Vector<HashMap<String, Object>> vectorList = new Vector<>();
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectStepsMethod)) {
            select.setString(1, suiteId.getidExecSuite());
            select.setString(2, suiteId.getSuite());
            select.setString(3, testRun);
            select.setString(4, method);
            try (ResultSet resultado = select.executeQuery()) {
                HashMap<String, Object> tmpHash;
                while (resultado.next()) {
                    vectorList.add(new HashMap<>());
                    tmpHash = vectorList.lastElement();
                    tmpHash.put("IDEXECSUITE", resultado.getString("IDEXECSUITE"));
                    tmpHash.put("SUITE", resultado.getString("SUITE"));
                    tmpHash.put("TEST", resultado.getString("TEST"));
                    tmpHash.put("METHOD", resultado.getString("METHOD"));
                    tmpHash.put("STEP_NUMBER", resultado.getString("STEP_NUMBER"));
                    tmpHash.put("DESCRIPTION", resultado.getString("DESCRIPTION"));
                    tmpHash.put("RES_EXPECTED", resultado.getString("RES_EXPECTED"));
                    tmpHash.put("RESULTADO", resultado.getString("RESULTADO"));
                    tmpHash.put("EXCEPCION", resultado.getString("EXCEPCION"));
                    tmpHash.put("INICIO", resultado.getString("INICIO"));
                    tmpHash.put("FIN", resultado.getString("FIN"));
                    tmpHash.put("TIME_MS", resultado.getString("TIME_MS"));
                    tmpHash.put("NUM_VALIDATIONS", resultado.getString("NUM_VALIDATIONS"));
                    tmpHash.put("IMAGE", resultado.getString("IMAGE"));
                    tmpHash.put("HTML", resultado.getString("HTML"));
                    tmpHash.put("TYPE_PAGE", resultado.getString("TYPE_PAGE"));
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
