package com.mng.robotest.test.appmanto;

import java.io.Serializable;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test.stpv.manto.PageSelTdaMantoStpV;
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
		TestCaseTM testCase = TestMaker.getTestCase().get();
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
		TestCaseTM.addNameSufix(this.index_fact);
		DataMantoAccess dMantoAcc = getDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		String codigoEspanya = "001";
		String codigoAlmacenEspanya = "001";
		PageSelTdaMantoStpV.selectTienda(codigoAlmacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.comprobarMenusManto(cabeceraName, cabeceraNameNext, driver);
	}
}