package com.mng.robotest.test80.mango.test.appmanto;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;

public class Menus {

	String cabeceraName = "";
	String cabeceraNameNext = "";
	int prioridad = 1;
	String index_fact = "";
	
	/**
	 * Constructor para invocación desde @Factory
	 */
	public Menus(String cabeceraName, String cabeceraNameNext, int prioridad) {
	    this.cabeceraName = cabeceraName;
	    this.cabeceraNameNext = cabeceraNameNext;
	    this.index_fact = cabeceraName;
	    this.prioridad = prioridad;
	}

	public DataMantoAccess getDataMantoAccess() {
		DataMantoAccess dMantoAcc = new DataMantoAccess();
		TestCaseTestMaker testCase = TestMaker.getTestCase();
		TestRunTestMaker testRun = testCase.getTestRunParent();
        InputParamsTestMaker inputParams = testCase.getInputParamsSuite();
		dMantoAcc.urlManto = inputParams.getUrlBase();
		dMantoAcc.userManto = testRun.getParameter(Constantes.paramUsrmanto);
		dMantoAcc.passManto = testRun.getParameter(Constantes.paramPasmanto);
		dMantoAcc.channel = Channel.desktop;
		dMantoAcc.appE = AppEcom.shop;
		return dMantoAcc;
	}

	@Test(
		groups={"Menus", "Canal:desktop_App:all"},
		description="Consulta de menús")
	public void MAN005_ConsultaMenus() throws Exception {
		DataMantoAccess dMantoAcc = getDataMantoAccess();
    	WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		String codigoEspanya = "001";
		String codigoAlmacenEspanya = "001";
		PageSelTdaMantoStpV.selectTienda(codigoAlmacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.comprobarMenusManto(cabeceraName, cabeceraNameNext, driver);
	}
}
