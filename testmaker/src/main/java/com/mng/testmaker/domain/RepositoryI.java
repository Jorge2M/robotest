package com.mng.testmaker.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.domain.data.TestCaseData;

public interface RepositoryI {
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	public void storeAll(SuiteTM suite);
	public void storeSuite(SuiteTM suite);
	public SuiteData getSuite(String idExecution) throws Exception;
	public List<SuiteData> getListSuitesAfter(Date fechaDesde) throws Exception;
	public List<SuiteData> getListSuites() throws Exception;
	public List<TestCaseData> getListTestCases(String suiteExecId) throws Exception;
}
