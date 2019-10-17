package com.mng.robotest.test80.mango.test.appmanto;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;

public class Menus {

	DataMantoAccess dMantoAcc;
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

	@BeforeMethod(groups={"Menus", "Canal:desktop_App:all"}, alwaysRun = true)
	public void login() throws Exception {
        InputParamsTestMaker inputParams = TestMaker.getInputParamsSuite();
		this.dMantoAcc = new DataMantoAccess();
		this.dMantoAcc.urlManto = inputParams.getUrlBase();
		this.dMantoAcc.userManto = TestMaker.getParamTestRun(Constantes.paramUsrmanto);
		this.dMantoAcc.passManto = TestMaker.getParamTestRun(Constantes.paramPasmanto);
		this.dMantoAcc.channel = Channel.desktop;
		this.dMantoAcc.appE = AppEcom.shop;
		Utils.storeDataMantoForTestMaker(inputParams.getWebDriverType(), index_fact, dMantoAcc);
	}

	@Test(
		groups={"Menus", "Canal:desktop_App:all"},
		description="Consulta de menús")
	public void MAN005_ConsultaMenus() throws Exception {
		WebDriver driver = TestCaseData.getWebDriver();
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, driver);
		String codigoEspanya = "001";
		String codigoAlmacenEspanya = "001";
		PageSelTdaMantoStpV.selectTienda(codigoAlmacenEspanya, codigoEspanya, this.dMantoAcc.appE, driver);
		PageMenusMantoStpV.comprobarMenusManto(this.cabeceraName, this.cabeceraNameNext, driver);
	}
}
