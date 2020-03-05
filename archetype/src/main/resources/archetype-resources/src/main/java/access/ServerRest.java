package ${package}.access;

import ${package}.access.datatmaker.Apps;
import ${package}.access.datatmaker.Suites;

import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

public class ServerRest {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunTestGoogle.getNew();
		ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class).
					portHttp(80).
					portHttps(443).
					certificate(
						ServerRestTM.class.getResource("/robotest.pro.mango.com.pfx").toExternalForm(), 
						"yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=").
					build();
		serverRest.start();
	}
}