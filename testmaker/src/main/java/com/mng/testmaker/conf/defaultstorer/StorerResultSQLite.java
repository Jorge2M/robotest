package com.mng.testmaker.conf.defaultstorer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.LockingMode;

import com.mng.testmaker.conf.ConstantesTM;
import com.mng.testmaker.domain.PersistorDataI;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.domain.data.TestRunData;
import com.mng.testmaker.repository.jdbc.dao.SuitesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestCasesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestRunsDAO;
import com.mng.testmaker.testreports.html.ResourcesExtractor;

public class StorerResultSQLite implements PersistorDataI {
	
	@Override
	public void store(SuiteTM suite) {
		storeSuite(suite);
	}
	
	private synchronized void storeSuite(SuiteTM suite) {
		SuitesDAO suitesDAO = new SuitesDAO(this);
		suitesDAO.insertSuite(SuiteData.from(suite));
		TestCasesDAO testCasesDAO = new TestCasesDAO(this);
		TestRunsDAO testRunsDAO = new TestRunsDAO(this);
		for (TestRunTM testRun : suite.getListTestRuns()) {
			testRunsDAO.insertTestRun(TestRunData.from(testRun));
			for (TestCaseTM testCase : testRun.getListTestCases()) {
				testCasesDAO.insertTestCase(TestCaseData.from(testCase));
			}
		}
	}

    
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
    	grabSqliteBDifNotExists();
        return getConnection(false);
    }
    
    public static Connection getConnectionNew() throws ClassNotFoundException, SQLException {
    	PersistorDataI storer = new StorerResultSQLite();
    	return storer.getConnection();
    }
    
    private void grabSqliteBDifNotExists() {
        ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
        File fileSqliteBD = new File(getSQLiteFilePathAutomaticTestingSchema());
        if (!fileSqliteBD.exists()) {
	        resExtractor.copyDirectoryResources(
	        	"sqlite/", 
	            getSQLitePathDirectory());
        }
    }
    
    private Connection getConnection(boolean forReadOnly) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName("org.sqlite.JDBC");
        System.getProperty("user.dir");
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(forReadOnly); 
        config.setBusyTimeout(30000);
        config.setLockingMode(LockingMode.NORMAL);
        
        conn = DriverManager.getConnection(
        	"jdbc:sqlite:" + 
        	getSQLiteFilePathAutomaticTestingSchema(), 
        	config.toProperties());
        return conn;
    }
    
    private String getSQLitePathDirectory() {
    	String path = 
    		System.getProperty("user.dir") + File.separator + 
    		ConstantesTM.directoryOutputTests + File.separator + 
    		"sqlite" + File.separator;
    	return path;
    }
    
    private String getSQLiteFilePathAutomaticTestingSchema() {
    	return (
    		getSQLitePathDirectory() + 
    		ConstantesTM.SQLiteFileAutomaticTestingSchema);
    }
}
