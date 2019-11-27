package com.mng.robotest.test80;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class RestApiRunTests {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunMango.getNew();
		ServerRestTM serverRest = ServerRestTM.getInstance(8887, creatorSuiteRun, RestApiMango.class, Suites.class, AppEcom.class);
		serverRest.start();
	}
	
}