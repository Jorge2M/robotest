package com.mng.testmaker.conf.defaultstorer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.LockingMode;

import com.mng.testmaker.conf.ConstantesTM;
import com.mng.testmaker.domain.RepositoryI;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.domain.data.TestRunData;
import com.mng.testmaker.repository.jdbc.dao.ConnectorBD;
import com.mng.testmaker.repository.jdbc.dao.SuitesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestCasesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestRunsDAO;
import com.mng.testmaker.testreports.html.ResourcesExtractor;

public class RepositorySQLite implements RepositoryI {
	
	private boolean sqliteBdGrabed = false;
	private final ConnectorBD connector = getConnectorBD(); 
	private final SuitesDAO suitesDAO = new SuitesDAO(connector);
	private final TestRunsDAO testRunsDAO = new TestRunsDAO(connector);
	private final TestCasesDAO testCasesDAO = new TestCasesDAO(connector);
	
	@Override
	public void storeAll(SuiteTM suite) {
		storeSuiteAndChildren(suite);
	}
	
	@Override
	public void storeSuite(SuiteTM suite) {
		storeOnlySuite(suite);
	}
	
	@Override
	public SuiteData getSuite(String suiteExecId) throws Exception {
		return (suitesDAO.getSuite(suiteExecId));
	}
	
	@Override
	public synchronized List<SuiteData> getListSuitesAfter(Date fechaDesde) throws Exception {
		return (suitesDAO.getListSuitesAfter(fechaDesde));
	}
	
	@Override
	public synchronized List<SuiteData> getListSuites() throws Exception {
		return (suitesDAO.getListSuitesIdDesc());
	}
	
	@Override
	public synchronized List<TestCaseData> getListTestCases(String suiteExecId) throws Exception {
		return (testCasesDAO.getListTestCases(suiteExecId));
	}
	
	private ConnectorBD getConnectorBD() {
		return ( 
			new ConnectorBD() {
				@Override
				public Connection getConnection() throws ClassNotFoundException, SQLException {
					grabSqliteBDifNotExists();
					Connection conn;
					Class.forName("org.sqlite.JDBC");
					System.getProperty("user.dir");
					SQLiteConfig config = new SQLiteConfig();
					//config.setReadOnly(forReadOnly); 
					config.setBusyTimeout(30000);
					config.setLockingMode(LockingMode.NORMAL);
					
					conn = DriverManager.getConnection(
							"jdbc:sqlite:" + 
							getSQLiteFilePathAutomaticTestingSchema(), 
							config.toProperties());
					return conn;
				}
			}
		);
	}
	
	private synchronized void storeOnlySuite(SuiteTM suite) {
		suitesDAO.insertOrReplaceSuite(SuiteData.from(suite));
	}
	
	private synchronized void storeSuiteAndChildren(SuiteTM suite) {
		suitesDAO.insertOrReplaceSuite(SuiteData.from(suite));
		for (TestRunTM testRun : suite.getListTestRuns()) {
			testRunsDAO.insertTestRun(TestRunData.from(testRun));
			for (TestCaseTM testCase : testRun.getListTestCases()) {
				testCasesDAO.insertTestCase(TestCaseData.from(testCase));
			}
		}
	}

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
    	return connector.getConnection();
    }
    
    private void grabSqliteBDifNotExists() {
    	if (!sqliteBdGrabed) {
	        File fileSqliteBD = new File(getSQLiteFilePathAutomaticTestingSchema());
	        if (!fileSqliteBD.exists()) {
	            ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
		        resExtractor.copyDirectoryResources(
		        	"sqlite/", 
		            getSQLitePathDirectory());
		        
		        sqliteBdGrabed = true;
	        }
    	}
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
