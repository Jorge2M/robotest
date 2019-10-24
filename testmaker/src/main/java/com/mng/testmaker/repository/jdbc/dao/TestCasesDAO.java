package com.mng.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.repository.jdbc.Connector;


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
        "INSERT INTO TESTCASES (" +
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
	        "INSTANCIA) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    
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

    
    public static void insertTestCase(TestCaseData testCase) {
        try (Connection conn = Connector.getConnection()) {
            try (PreparedStatement insert = conn.prepareStatement(SQLInsertMethod)) {
            	insert.setString(1, testCase.getIdExecSuite());
            	insert.setString(2, testCase.getSuiteName()); 
            	insert.setString(3, testCase.getTestRunName()); 
            	insert.setString(4, testCase.getName()); 
            	insert.setString(5, testCase.getDescription()); 
            	insert.setString(6, testCase.getResult().name()); 
    	        insert.setString(7, getDateFormat().format(testCase.getInicioDate()));
    	        insert.setString(8, getDateFormat().format(testCase.getFinDate()));
    	        insert.setFloat(9, testCase.getDurationMillis());
    	        insert.setInt(10,  testCase.getNumberSteps());
    	        insert.setString(11,  testCase.getClassSignature());
    	        insert.setString(12,  testCase.getInstance());
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
    
    public static void deleteTestCasesBefore(String idSuite) {
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteHistorical)) {
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
