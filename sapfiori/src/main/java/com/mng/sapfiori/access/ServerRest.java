package com.mng.sapfiori.access;

import com.mng.sapfiori.access.datatmaker.Apps;
import com.mng.sapfiori.access.datatmaker.Suites;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class ServerRest {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunSapFiori.getNew();
		ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class).
					portHttp(81).
					portHttps(444).
					certificate(
						ServerRestTM.class.getResource("/robotest.pro.mango.com.pfx").toExternalForm(), 
						"yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=").
					build();
		serverRest.start();
	}
}
