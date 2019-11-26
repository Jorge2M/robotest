package com.mng.sapfiori;

import com.mng.sapfiori.datatmaker.Apps;
import com.mng.sapfiori.datatmaker.Suites;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class RestApiRunTests {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunSapFiori.getNew();
		ServerRestTM serverRest = ServerRestTM.getInstance(8888, creatorSuiteRun, Suites.class, Apps.class);
		serverRest.start();
	}
	
}
