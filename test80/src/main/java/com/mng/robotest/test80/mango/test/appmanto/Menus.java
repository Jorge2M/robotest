package com.mng.robotest.test80.mango.test.appmanto;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.domain.suitetree.TestRunTM;
import com.mng.testmaker.service.TestMaker;
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
		this.index_fact = " (" + cabeceraName + ")";
	}

	public DataMantoAccess getDataMantoAccess() {
		DataMantoAccess dMantoAcc = new DataMantoAccess();
		TestCaseTM testCase = TestMaker.getTestCase();
		TestRunTM testRun = testCase.getTestRunParent();
		InputParamsTM inputParams = testCase.getInputParamsSuite();
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
		TestMaker.getTestCase().setRefineDataName(this.index_fact);
		DataMantoAccess dMantoAcc = getDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		String codigoEspanya = "001";
		String codigoAlmacenEspanya = "001";
		PageSelTdaMantoStpV.selectTienda(codigoAlmacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.comprobarMenusManto(cabeceraName, cabeceraNameNext, driver);
	}
}
