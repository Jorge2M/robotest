package org.mng.testgoogle.access;

import org.mng.testgoogle.access.datatmaker.Apps;
import org.mng.testgoogle.access.datatmaker.Suites;

import com.mng.testmaker.boundary.access.ServerCmdLine;
import com.mng.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class ServerRest {

	public static void main(String[] args) throws Exception {
		ResultCmdServer result = ServerCmdLine.parse(args);
		if (result!=null && result.isOk()) {
			CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunTestGoogle.getNew();
			ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class)
				.portHttp(result.getPort())
				.portHttps(result.getSecurePort())
				/*
				.certificate(
					ServerRestTM.class.getResource("/robotest.pro.mango.com.pfx").toExternalForm(), 
					"yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=")*/
				.build();
			serverRest.start();
		}
	}
}
