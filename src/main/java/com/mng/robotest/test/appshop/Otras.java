package com.mng.robotest.test.appshop;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.*;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.steps.otras.GoogleSteps;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.PageIniShopJaponSteps;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusDesktopSteps;
import com.mng.robotest.test.steps.shop.modales.ModalChatBotSteps;
import com.mng.robotest.test.steps.shop.modales.ModalNewsletterSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

import org.openqa.selenium.WebDriver;

public class Otras {
	
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final Pais francia = PaisGetter.get(PaisShop.FRANCE);
	private static final Pais suecia = PaisGetter.get(PaisShop.SWEDEN);
	private static final Pais irlanda = PaisGetter.get(PaisShop.IRELAND);
	private static final Pais USA = PaisGetter.get(PaisShop.USA);
	private static final Pais japon = PaisGetter.get(PaisShop.JAPON);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);
	private static final IdiomaPais francia_frances = francia.getListIdiomas().get(0);
	private static final IdiomaPais japones = japon.getListIdiomas().get(0);

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		return dCtxSh;
	}
	
	@Test (
		groups={"Otras", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar acceso url desde email")
	public void OTR001_check_Redirects() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.pais = espana;
		dCtxSh.idioma = castellano;
		dCtxSh.userRegistered = false;
		AccesoSteps.oneStep(dCtxSh, false, driver);
		
		SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps(dCtxSh.pais, dCtxSh.appE, dCtxSh.channel);
		secMenusDesktopSteps.checkURLRedirectParkasHeEspanya();
		
		dCtxSh.pais = francia;
		dCtxSh.idioma = francia_frances;
		AccesoSteps.goToInitialURL(driver);
		AccesoSteps.oneStep(dCtxSh, false, driver);	  
		SecMenusDesktopSteps.checkURLRedirectFicha(francia, dCtxSh, driver);
	}
	
	
	@Test (
		groups={"Otras", "Canal:desktop_App:shop"}, 
		description="Verificar en google la existencia de referencia Mango")
	public void OTR002_check_Busqueda_Google() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		GoogleSteps googleSteps = new GoogleSteps(driver);
		googleSteps.accessGoogleAndSearchMango();
		googleSteps.selectFirstLinkSinPublicidad();
	}
	
	@Test (
		groups={"Otras", "Canal:desktop_App:all"}, 
		description="Verificar el cambio de país a través del modal")
	public void OTR003_cambioPais() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();

		dCtxSh.pais = espana;
		dCtxSh.idioma = castellano;
		dCtxSh.userRegistered = false;
		AccesoSteps.accesoPRYCambioPais(dCtxSh, francia, francia_frances, driver);
	}

	/**
	 * Caso de prueba que realiza el flujo de accesos vía URL/confirmaciones de país para certificar el correcto funcionamiento del modal de confirmación de país.
	 */
	@Test (
		enabled=false, //Desactivado hasta que se corrija la incidencia https://jira.mangodev.net/jira/browse/GPS-975
		groups={"Otras", "Canal:desktop_App:shop,outlet"}, 
		description="Verificar el cambio de país a través de url")
	public void OTR004_cambioPaisURL() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		String urlBaseTest = inputParamsSuite.getUrlBase();

		//Definimos la lista de los 3 países que pueden estar asociados a la IP del usuario
		List<Pais> listPaisAsocIP = new ArrayList<>();
		listPaisAsocIP.add(espana);
		listPaisAsocIP.add(irlanda);
		listPaisAsocIP.add(USA);
			
		//Acceso vía URL con:
		//   país de acceso: suecia (no asociado a la IP del usuario)
		//   país de acceso previo: ninguno (null)
		//   país previamente confirmado: ninguno (null)
		Pais paisAsocIP = AccesoSteps.accesoConURLPaisNoIP(urlBaseTest, suecia, null, null, 0, listPaisAsocIP, driver);
				
		//Acceso vía URL con:
		//   país de acceso: francia (no asociado a la IP del usuario)
		//   país de acceso previo: suecia
		//   país previamente confirmado: ninguno (null)
		AccesoSteps.accesoConURLPaisNoIP(urlBaseTest, francia, suecia, null, 0, listPaisAsocIP, driver);
					
		//Step. Confirmamos el país del modal (España, Irlanda o USA... el de paisAsocIP)
		AccesoSteps.selectConfirmPaisModal(driver);

		//Acceso vía URL con:
		//   país de acceso: francia (no asociado a la IP del usuario)
		//   país de acceso previo: suecia
		//   país previamente confirmado: paisAsocIp (España, Irlanda o USA)
		//   número de veces confirmado: 1
		AccesoSteps.accesoConURLPaisNoIP(urlBaseTest, francia, suecia, paisAsocIP, 1, listPaisAsocIP, driver);
			
		//Step. Confirmamos el país del modal (España, Irlanda o USA... el de paisAsocIP)
		AccesoSteps.selectConfirmPaisModal(driver);
			
		//Acceso vía URL con:
		//   país de acceso: suecia (no asociado a la IP del usuario)
		//   país de acceso previo: francia
		//   país previamente confirmado: paisAsocIp (España, Irlanda o USA)
		//   número de veces confirmado: 2
		AccesoSteps.accesoConURLPaisNoIP(urlBaseTest, suecia, francia, paisAsocIP, 2, listPaisAsocIP, driver);
				
		//Steps. Acabamos ejecutando la funcionalidad típica de cambio de país desde el footer
		dCtxSh.pais = francia;
		dCtxSh.idioma = francia_frances;
		(new SecFooterSteps(dCtxSh.channel, dCtxSh.appE, driver)).cambioPais(dCtxSh);
	}

	
	/**
	/* Acceso a la prehome, selección de Japón/Japonés y validaciones de que aparece la portada de la shop específica de Japón
	 */
	@Test (
		groups={"Otras", "Canal:desktop_App:shop"}, 
		description="Acceso al país Japón desde la preHome y comprobación de que redirige a la shop específica de este país")
	public void OTR005_accesoJapon() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();

		dCtxSh.pais = japon;
		dCtxSh.idioma = japones;
		PagePrehomeSteps pagePrehomeSteps = new PagePrehomeSteps(dCtxSh, driver);
		pagePrehomeSteps.getPageObject().previousAccessShopSteps(true);
		pagePrehomeSteps.seleccionPaisIdioma();
		pagePrehomeSteps.entradaShopGivenPaisSeleccionado();
		PageIniShopJaponSteps.validaPageIniJapon(2, driver);
	}	
	
	//TODO cuando lo activen en Tablet añadir ese canal
	@Test (
		groups={"Otras", "Canal:desktop,mobile_App:shop"}, 
		description="Chequear el ChatBot")
	public void OTR006_chatBot() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.pais = espana;
		dCtxSh.idioma = castellano;
		dCtxSh.userRegistered = false;
		AccesoSteps.oneStep(dCtxSh, false, driver);
		ModalNewsletterSteps.closeIfVisible(driver);
		
		ModalChatBotSteps chatBotSteps = new ModalChatBotSteps(driver);
		if (!chatBotSteps.checkIconVisible()) {
			return;
		}
		if (!chatBotSteps.clickIcon()) {
			return;
		}
		
		String option1 = "Estado de mi pedido";
		chatBotSteps.isVisibleOption(option1, 5);
		chatBotSteps.selectOption(option1);
		
		String option2 = "Retraso de mi pedido";
		chatBotSteps.isVisibleOption(option2, 5);
		chatBotSteps.selectOption(option2);
		
		chatBotSteps.checkResponseVisible("Si has recibido un e-mail de retraso de tu pedido no te preocupes", 3);
		chatBotSteps.isVisibleButton("¡Sí, gracias!", 3);
	}
}