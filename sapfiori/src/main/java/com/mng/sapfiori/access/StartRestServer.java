package com.mng.sapfiori.access;

import com.mng.sapfiori.access.datatmaker.Apps;
import com.mng.sapfiori.access.datatmaker.Suites;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class StartRestServer {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunSapFiori.getNew();
		ServerRestTM serverRest = ServerRestTM.getInstance(8888, creatorSuiteRun, Suites.class, Apps.class);
		serverRest.start();
	}
}
