package com.mng.robotest.test.appmanto;

import java.io.Serializable;
import java.util.Optional;

import org.testng.annotations.Test;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.datatest.DataMantoTest;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.steps.manto.PageLoginMantoSteps;
import com.mng.robotest.test.steps.manto.PageSelTdaMantoSteps;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
//import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;

public class Menus implements Serializable {

	private static final long serialVersionUID = -5780907750259210736L;
	
	String cabeceraName = "";
	String cabeceraNameNext = "";
	int prioridad = 1;
	String indexFact = "";
	
	/**
	 * Constructor para invocación desde @Factory
	 */
	public Menus(String cabeceraName, String cabeceraNameNext, int prioridad) {
		this.cabeceraName = cabeceraName;
		this.cabeceraNameNext = cabeceraNameNext;
		this.indexFact = cabeceraName;
		this.prioridad = prioridad;
		this.indexFact = " (" + cabeceraName + ")";
	}

	public DataMantoTest getDataMantoAccess() {
		DataMantoTest dMantoAcc = DataMantoTest.make();
		TestCaseTM testCase = getTestCase();
		TestRunTM testRun = testCase.getTestRunParent();
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		dMantoAcc.setUrlManto(inputParams.getUrlBase());
		dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
		dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
		dMantoAcc.setChannel(Channel.desktop);
		dMantoAcc.setAppE(AppEcom.shop);
		return dMantoAcc;
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}

	@Test(
		groups={"Menus", "Canal:desktop_App:all"},
		description="Consulta de menús")
	public void MAN005_ConsultaMenus() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new PageLoginMantoSteps().login();
		String codigoEspanya = "001";
		String codigoAlmacenEspanya = "001";
		new PageSelTdaMantoSteps().selectTienda(codigoAlmacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().comprobarMenusManto(cabeceraName, cabeceraNameNext);
	}
}
