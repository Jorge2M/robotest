package com.mng.robotest.test80.access.cmd;

import com.mng.robotest.test80.access.CreatorSuiteRunMango;
import com.mng.robotest.test80.access.rest.RestApiMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class StartRestServer {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunMango.getNew();
		ServerRestTM serverRest = ServerRestTM.getInstance(8887, creatorSuiteRun, RestApiMango.class, Suites.class, AppEcom.class);
		serverRest.start();
	}
}
