package com.mng.robotest.test.appshop;

import java.io.Serializable;
import java.util.ArrayList;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.micuenta.PageSuscripciones.NewsLetter;
import com.mng.robotest.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test.stpv.shop.micuenta.PageDevolucionesStpV;
import com.mng.robotest.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test.stpv.shop.micuenta.PageMisDatosStpV;
import com.mng.robotest.test.stpv.shop.micuenta.PageSuscripcionesStpV;
import com.mng.robotest.test.stpv.shop.miscompras.PageMisComprasStpV;
import com.mng.robotest.test.utils.PaisGetter;

public class MiCuenta implements Serializable {
	
	private static final long serialVersionUID = 2188911402476562105L;
	
	public int prioridad;
	private String index_fact = "";
	private Pais paisFactory = null;
	private IdiomaPais idiomaFactory = null;
	private final static Pais espana = PaisGetter.get(PaisShop.Espana);
	private final static IdiomaPais castellano = espana.getListIdiomas().get(0);

	public MiCuenta() {}

	/**
	 * Constructor para invocación desde @Factory
	 */
	public MiCuenta(Pais pais, IdiomaPais idioma, int prioridad) {
		this.paisFactory = pais;
		this.idiomaFactory = idioma;
		this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
	}
	
	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());

		//Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
		if (this.paisFactory==null) {
			dCtxSh.pais = espana;
			dCtxSh.idioma = castellano;
		} else {
			dCtxSh.pais = this.paisFactory;
			dCtxSh.idioma = this.idiomaFactory;
		}
		return dCtxSh;
	}

	@Test (
		groups={"Canal:desktop_App:shop,outlet", "Micuenta", "CI"}, alwaysRun=true, 
		description="Verificar opciones de 'mi cuenta'")
	@Parameters({"userConDevolucionPeroSoloEnPRO", "passwordUserConDevolucion"})
	public void MIC001_Opciones_Mi_Cuenta(String userConDevolucionPeroNoEnPRO, String passwordUserConDevolucion) 
	throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		TestCaseTM.addNameSufix(this.index_fact);
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userConnected = userConDevolucionPeroNoEnPRO;
		dCtxSh.passwordUser = passwordUserConDevolucion;
			
		new PagePrehomeStpV(dCtxSh, driver).seleccionPaisIdiomaAndEnter();
		dCtxSh.userRegistered = false;
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.seleccionLinea(LineaType.she, null, dCtxSh);
		dCtxSh.userRegistered = true;
		AccesoStpV.identificacionEnMango(dCtxSh, driver);
		
		PageMiCuentaStpV pageMiCuentaStpV = PageMiCuentaStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		pageMiCuentaStpV.goToMisDatos(dCtxSh.userConnected);
		
		PageMisDatosStpV pageMisDatosStpV = new PageMisDatosStpV(driver);
		String nombreActual = pageMisDatosStpV.modificaNombreYGuarda();
		pageMiCuentaStpV.goToMisDatos(dCtxSh.userConnected);
		pageMisDatosStpV.validaContenidoNombre(nombreActual);
		pageMiCuentaStpV.goToMisComprasFromMenu(dCtxSh.pais);
			
		pageMiCuentaStpV.goToSuscripciones();
		ArrayList<NewsLetter> listNewsletters = new ArrayList<>();
		listNewsletters.add(NewsLetter.she);
		PageSuscripcionesStpV.create(driver).selectNewslettersAndGuarda(listNewsletters);
		if (dCtxSh.appE!=AppEcom.outlet) {
			pageMiCuentaStpV.goToDevoluciones();
			PageDevolucionesStpV.solicitarRegogidaGratuitaADomicilio(driver);
			pageMiCuentaStpV.goToReembolsos();
		}
	}
	
	/**
	 * @param userConCompras Usuario con compras de ambos tipos (tienda, online) en PRE pero sólo de tipo online en PRO
	 */
	@Test (
		groups={"Micuenta", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="Consulta de mis compras con un usuario con datos a nivel de Tienda y Online")
	@Parameters({"userWithOnlinePurchases", "userWithStorePurchases", "passUserWithOnlinePurchases", "passUserWithStorePurchases"})
	public void MIC002_CheckConsultaMisCompras(
			String userWithOnlinePurchases, String userWithStorePurchases, 
			String passUserWithOnlinePurchases, String passUserWithStorePurchases) throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		
		//Test Compras Online
		dCtxSh.userConnected = userWithOnlinePurchases;
		dCtxSh.passwordUser = passUserWithOnlinePurchases;
		dCtxSh.userRegistered = true;
		new PagePrehomeStpV(dCtxSh, driver).seleccionPaisIdiomaAndEnter();
		AccesoStpV.identificacionEnMango(dCtxSh, driver);
		
		PageMiCuentaStpV pageMiCuentaStpV = PageMiCuentaStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		pageMiCuentaStpV.goToMisComprasFromMenu(dCtxSh.pais);
		PageMisComprasStpV pageMisComprasStpV = PageMisComprasStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		pageMisComprasStpV.validateIsCompraOfType(TypeTicket.Online, 3);
		pageMisComprasStpV.selectCompraOnline(1, dCtxSh.pais.getCodigo_pais());
		pageMisComprasStpV.clickDetalleArticulo(1);
		pageMisComprasStpV.gotoMisComprasFromDetalleCompra();
		
		//Test Compras en Tienda
		dCtxSh.userConnected = userWithStorePurchases;
		dCtxSh.passwordUser = passUserWithStorePurchases;
		SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		userMenusStpV.logoff();
		
		//Existe un problema en por el cual si te vuelves a loginar manteniendo el navegador
		//se muestran las compras del anterior usuario
		driver = TestMaker.renewDriverTestCase();
		pageMiCuentaStpV = PageMiCuentaStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		new PagePrehomeStpV(dCtxSh, driver).seleccionPaisIdiomaAndEnter();
		AccesoStpV.identificacionEnMango(dCtxSh, driver);
		
		pageMiCuentaStpV.goToMisComprasFromMenu(dCtxSh.pais);
		pageMisComprasStpV = PageMisComprasStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		pageMisComprasStpV.validateIsCompraOfType(TypeTicket.Tienda, 3);
		pageMisComprasStpV.selectCompraTienda(1);
		pageMisComprasStpV.clickDetalleArticulo(1);
	}
}