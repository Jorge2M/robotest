package com.mng.robotest.test.appshop;

import java.io.Serializable;
import java.util.ArrayList;
import org.testng.annotations.*;

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
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageDevolucionesSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageMiCuentaSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageMisDatosSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageSuscripcionesSteps;
import com.mng.robotest.test.steps.shop.miscompras.PageMisComprasSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class MiCuenta implements Serializable {
	
	private static final long serialVersionUID = 2188911402476562105L;
	
	public int prioridad;
	private String index_fact = "";
	private Pais paisFactory = null;
	private IdiomaPais idiomaFactory = null;
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);

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
		TestCaseTM.addNameSufix(this.index_fact);
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userConnected = userConDevolucionPeroNoEnPRO;
		dCtxSh.passwordUser = passwordUserConDevolucion;
			
		new PagePrehomeSteps(dCtxSh.pais, dCtxSh.idioma).seleccionPaisIdiomaAndEnter();
		dCtxSh.userRegistered = false;
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.seleccionLinea(LineaType.she, null, dCtxSh);
		dCtxSh.userRegistered = true;
		new AccesoSteps().identificacionEnMango(dCtxSh);
		
		PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
		pageMiCuentaSteps.goToMisDatos(dCtxSh.userConnected);
		
		PageMisDatosSteps pageMisDatosSteps = new PageMisDatosSteps();
		String nombreActual = pageMisDatosSteps.modificaNombreYGuarda("Jorge", "George");
		pageMiCuentaSteps.goToMisDatos(dCtxSh.userConnected);
		pageMisDatosSteps.validaContenidoNombre(nombreActual);
		pageMiCuentaSteps.goToMisComprasFromMenu(dCtxSh.pais);
			
		pageMiCuentaSteps.goToSuscripciones();
		ArrayList<NewsLetter> listNewsletters = new ArrayList<>();
		listNewsletters.add(NewsLetter.she);
		new PageSuscripcionesSteps().selectNewslettersAndGuarda(listNewsletters);
		if (dCtxSh.appE!=AppEcom.outlet) {
			pageMiCuentaSteps.goToDevoluciones();
			new PageDevolucionesSteps().solicitarRegogidaGratuitaADomicilio();
			pageMiCuentaSteps.goToReembolsos();
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
		DataCtxShop dCtxSh = getCtxShForTest();
		
		//Test Compras Online
		dCtxSh.userConnected = userWithOnlinePurchases;
		dCtxSh.passwordUser = passUserWithOnlinePurchases;
		dCtxSh.userRegistered = true;
		new PagePrehomeSteps(dCtxSh.pais, dCtxSh.idioma).seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango(dCtxSh);
		
		PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
		pageMiCuentaSteps.goToMisComprasFromMenu(dCtxSh.pais);
		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps(dCtxSh.channel, dCtxSh.appE);
		pageMisComprasSteps.validateIsCompraOfType(TypeTicket.Online, 3);
		pageMisComprasSteps.selectCompraOnline(1, dCtxSh.pais.getCodigo_pais());
		pageMisComprasSteps.clickDetalleArticulo(1);
		pageMisComprasSteps.gotoMisComprasFromDetalleCompra();
		
		//Test Compras en Tienda
		dCtxSh.userConnected = userWithStorePurchases;
		dCtxSh.passwordUser = passUserWithStorePurchases;
		new SecMenusUserSteps().logoff();
		
		//Existe un problema en por el cual si te vuelves a loginar manteniendo el navegador
		//se muestran las compras del anterior usuario
		TestMaker.renewDriverTestCase();
		pageMiCuentaSteps = new PageMiCuentaSteps();
		new PagePrehomeSteps(dCtxSh.pais, dCtxSh.idioma).seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango(dCtxSh);
		
		pageMiCuentaSteps.goToMisComprasFromMenu(dCtxSh.pais);
		pageMisComprasSteps = new PageMisComprasSteps(dCtxSh.channel, dCtxSh.appE);
		pageMisComprasSteps.validateIsCompraOfType(TypeTicket.Tienda, 3);
		pageMisComprasSteps.selectCompraTienda(1);
		pageMisComprasSteps.clickDetalleArticulo(1);
	}
}