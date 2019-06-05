package com.mng.robotest.test80.mango.test.appmanto;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;

public class Menus  extends GestorWebDriver {

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
	@Parameters({"brwsr-path"})
	public void login(String bpath, ITestContext ctx, Method method) throws Exception {
		this.dMantoAcc = new DataMantoAccess();
		this.dMantoAcc.urlManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramUrlBase);
		this.dMantoAcc.userManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramUsrmanto);
		this.dMantoAcc.passManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramPasmanto);
		this.dMantoAcc.channel = Channel.desktop;
		this.dMantoAcc.appE = AppEcom.shop;
		Utils.storeDataMantoForTestMaker(bpath, this.index_fact, dMantoAcc, ctx, method);
	}

	@AfterMethod (groups={"Menus", "Canal:desktop_App:all", "SupportsFactoryCountrys"}, alwaysRun = true)
	public void logout(ITestContext context, Method method) throws Exception {
		WebDriver driver = TestCaseData.getWebDriver();
		super.quitWebDriver(driver, context);
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
