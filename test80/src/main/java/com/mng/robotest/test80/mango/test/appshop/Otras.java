package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.*;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.stpv.otras.GoogleStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PageIniShopJaponStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalChatBotStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

import org.openqa.selenium.WebDriver;

public class Otras {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static Pais francia = PaisGetter.get(PaisShop.France);
	private final static Pais suecia = PaisGetter.get(PaisShop.Sweden);
	private final static Pais irlanda = PaisGetter.get(PaisShop.Ireland);
	private final static Pais USA = PaisGetter.get(PaisShop.USA);
	private final static Pais japon = PaisGetter.get(PaisShop.Japón);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	private final static IdiomaPais francia_frances = francia.getListIdiomas().get(0);
	private final static IdiomaPais japones = japon.getListIdiomas().get(0);

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		return dCtxSh;
	}
	
    @Test (
        groups={"Otras", "Canal:desktop_App:shop,outlet"}, 
        description="Comprobar acceso url desde email")
    public void OTR001_check_Redirects() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.pais = españa;
        dCtxSh.idioma = castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.oneStep(dCtxSh, false, driver);
        
        SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, driver);
        secMenusDesktopStpV.checkURLRedirectParkasHeEspanya();
        
        dCtxSh.pais = francia;
        dCtxSh.idioma = francia_frances;
        AccesoStpV.goToInitialURL(driver);
        AccesoStpV.oneStep(dCtxSh, false, driver);      
        SecMenusDesktopStpV.checkURLRedirectFicha(francia, dCtxSh, driver);
    }
	
    
    @Test (
        groups={"Otras", "Canal:desktop_App:shop"}, 
        description="Verificar en google la existencia de referencia Mango")
    public void OTR002_check_Busqueda_Google() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
    	GoogleStpV googleStpV = new GoogleStpV(driver);
        googleStpV.accessGoogleAndSearchMango();
        googleStpV.selectFirstLinkSinPublicidad();
    }
    
    @Test (
        groups={"Otras", "Canal:desktop_App:all"}, 
        description="Verificar el cambio de país a través del modal")
    public void OTR003_cambioPais() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();

        dCtxSh.pais = españa;
        dCtxSh.idioma = castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.accesoPRYCambioPais(dCtxSh, francia, francia_frances, driver);
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
        InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        String urlBaseTest = inputParamsSuite.getUrlBase();

        //Definimos la lista de los 3 países que pueden estar asociados a la IP del usuario
        List<Pais> listPaisAsocIP = new ArrayList<>();
        listPaisAsocIP.add(españa);
        listPaisAsocIP.add(irlanda);
        listPaisAsocIP.add(USA);
            
        //Acceso vía URL con:
        //   país de acceso: suecia (no asociado a la IP del usuario)
        //   país de acceso previo: ninguno (null)
        //   país previamente confirmado: ninguno (null)
        Pais paisAsocIP = AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, suecia, null, null, 0, listPaisAsocIP, driver);
                
        //Acceso vía URL con:
        //   país de acceso: francia (no asociado a la IP del usuario)
        //   país de acceso previo: suecia
        //   país previamente confirmado: ninguno (null)
        AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, francia, suecia, null, 0, listPaisAsocIP, driver);
                    
        //Step. Confirmamos el país del modal (España, Irlanda o USA... el de paisAsocIP)
        AccesoStpV.selectConfirmPaisModal(driver);

        //Acceso vía URL con:
        //   país de acceso: francia (no asociado a la IP del usuario)
        //   país de acceso previo: suecia
        //   país previamente confirmado: paisAsocIp (España, Irlanda o USA)
        //   número de veces confirmado: 1
        AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, francia, suecia, paisAsocIP, 1, listPaisAsocIP, driver);
            
        //Step. Confirmamos el país del modal (España, Irlanda o USA... el de paisAsocIP)
        AccesoStpV.selectConfirmPaisModal(driver);
            
        //Acceso vía URL con:
        //   país de acceso: suecia (no asociado a la IP del usuario)
        //   país de acceso previo: francia
        //   país previamente confirmado: paisAsocIp (España, Irlanda o USA)
        //   número de veces confirmado: 2
        AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, suecia, francia, paisAsocIP, 2, listPaisAsocIP, driver);
                
        //Steps. Acabamos ejecutando la funcionalidad típica de cambio de país desde el footer
        dCtxSh.pais = francia;
        dCtxSh.idioma = francia_frances;
        (new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver)).cambioPais(dCtxSh);
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
        PagePrehome.previousAccessShopSteps(dCtxSh, driver);
        PagePrehomeStpV.seleccionPaisIdioma(dCtxSh, driver);
        PagePrehomeStpV.entradaShopGivenPaisSeleccionado(japon, japones, dCtxSh.channel, driver);
        PageIniShopJaponStpV.validaPageIniJapon(2, driver);
    }	
    
    //TODO cuando lo activen en Tablet añadir ese canal
    @Test (
        groups={"Otras", "Canal:desktop,mobile_App:shop"}, 
        description="Chequear el ChatBot")
    public void OTR006_chatBot() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.pais = españa;
        dCtxSh.idioma = castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.oneStep(dCtxSh, false, driver);
        
        ModalChatBotStpV chatBotStpV = new ModalChatBotStpV(driver);
        if (!chatBotStpV.checkIconVisible()) {
        	return;
        }
        if (!chatBotStpV.clickIcon()) {
        	return;
        }
        
        String option1 = "Estado de mi pedido";
        chatBotStpV.isVisibleOption(option1, 5);
        chatBotStpV.selectOption(option1);
        
        String option2 = "Retraso de mi pedido";
        chatBotStpV.isVisibleOption(option2, 5);
        chatBotStpV.selectOption(option2);
        
        chatBotStpV.checkResponseVisible("Si has recibido un e-mail de retraso de tu pedido no te preocupes", 3);
        chatBotStpV.isVisibleButton("¡Sí, gracias!", 1);
    }
}