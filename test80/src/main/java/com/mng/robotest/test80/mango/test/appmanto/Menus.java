package com.mng.robotest.test80.mango.test.appmanto;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
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
	@Parameters({"brwsr-path", "urlBase"})
	public void login(String bpath, String urlBase, ITestContext ctx, Method method) throws Exception {
		this.dMantoAcc = new DataMantoAccess();
		this.dMantoAcc.urlManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramUrlBase);
		this.dMantoAcc.userManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramUsrmanto);
		this.dMantoAcc.passManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramPasmanto);
		this.dMantoAcc.appE = AppEcom.shop;
		TestCaseData.getAndStoreDataFmwk(bpath, urlBase, this.index_fact, Channel.desktop, ctx, method);
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
		DataFmwkTest dFTest = TestCaseData.getdFTest();
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		String codigoEspanya = "001";
		String codigoAlmacenEspanya = "001";
		PageSelTdaMantoStpV.selectTienda(codigoAlmacenEspanya, codigoEspanya, this.dMantoAcc.appE, dFTest.driver);

		//ArrayList<String> listMenuNames = PageMenusManto.getListCabecerasMenusName(dFTest.driver);
		PageMenusMantoStpV.comprobarMenusManto(this.cabeceraName, this.cabeceraNameNext, dFTest.driver);
	}
}
