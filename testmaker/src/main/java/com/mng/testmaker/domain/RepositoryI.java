package com.mng.testmaker.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.mng.testmaker.domain.suitetree.SuiteBean;
import com.mng.testmaker.domain.suitetree.SuiteTM;
import com.mng.testmaker.domain.suitetree.TestCaseBean;

public interface RepositoryI {
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	public void storeAll(SuiteTM suite);
	public void storeSuite(SuiteTM suite);
	public SuiteBean getSuite(String idExecution) throws Exception;
	public List<SuiteBean> getListSuitesAfter(Date fechaDesde) throws Exception;
	public List<SuiteBean> getListSuites() throws Exception;
	public List<TestCaseBean> getListTestCases(String suiteExecId) throws Exception;
}
