package com.mng.testmaker.conf.defaultstorer;

import java.io.File;

import com.mng.testmaker.domain.StorerResultI;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.domain.data.TestRunData;
import com.mng.testmaker.repository.jdbc.Connector;
import com.mng.testmaker.repository.jdbc.dao.SuitesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestCasesDAO;
import com.mng.testmaker.repository.jdbc.dao.TestRunsDAO;
import com.mng.testmaker.testreports.html.ResourcesExtractor;

public class StorerResultSQLite implements StorerResultI {
	
	@Override
	public void store(SuiteTM suite) {
		grabSqliteBDifNotExists();
		storeSuite(suite);
	}
	
	private synchronized void storeSuite(SuiteTM suite) {
		SuitesDAO.insertSuite(SuiteData.from(suite));
		for (TestRunTM testRun : suite.getListTestRuns()) {
			TestRunsDAO.insertTestRun(TestRunData.from(testRun));
			for (TestCaseTM testCase : testRun.getListTestCases()) {
				TestCasesDAO.insertTestCase(TestCaseData.from(testCase));
			}
		}
	}
    
    private void grabSqliteBDifNotExists() {
        ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
        File fileSqliteBD = new File(Connector.getSQLiteFilePathAutomaticTestingSchema());
        if (!fileSqliteBD.exists()) {
	        resExtractor.copyDirectoryResources(
	        	"sqlite/", 
	            Connector.getSQLitePathDirectory());
        }
    }
}
