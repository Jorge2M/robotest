package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.*;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageIniShopJapon;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.stpv.otras.GoogleStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PageIniShopJaponStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;

import org.openqa.selenium.WebDriver;


public class Otras extends GestorWebDriver {

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();

    Pais españa = null;
    Pais suecia = null;
    Pais francia = null;
    Pais irlanda = null;
    Pais USA = null;
    Pais japon = null;
    IdiomaPais castellano = null;
    IdiomaPais francia_frances = null;
    IdiomaPais suecia_ingles = null;
    IdiomaPais japones = null;
	
    @BeforeMethod (groups={"Otras", "Canal:all_App:all"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {

        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
        
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            Integer codFrancia = Integer.valueOf(11);
            Integer codSuecia = Integer.valueOf(30);
            Integer codIrlanda = Integer.valueOf(7);
            Integer codUSA = Integer.valueOf(400);
            Integer codJapon = Integer.valueOf(732);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya, codFrancia, codSuecia, codIrlanda, codUSA, codJapon)));
            this.españa  = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.francia = UtilsMangoTest.getPaisFromCodigo("011", listaPaises);
            this.suecia  = UtilsMangoTest.getPaisFromCodigo("030", listaPaises);
            this.irlanda = UtilsMangoTest.getPaisFromCodigo("007", listaPaises);
            this.USA     = UtilsMangoTest.getPaisFromCodigo("400", listaPaises);
            this.japon   = UtilsMangoTest.getPaisFromCodigo("732", listaPaises);

            this.castellano = this.españa.getListIdiomas().get(0);
            this.francia_frances = this.francia.getListIdiomas().get(0);
            this.suecia_ingles = this.suecia.getListIdiomas().get(0);
            this.japones = this.japon.getListIdiomas().get(0);
       }
    }

	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"Otras", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }		
	
    @Test (
        groups={"Otras", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, 
        description="Comprobar acceso url desde email")
    public void OTR001_check_Redirects() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
	    
        //Step. Acceso a la aplicación shop/outlet/VOTF sin registrarse posteriormente
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
                            
        //Step. Valida que la URL de acceso desde correo a HE es correcta
        SecMenusDesktopStpV.checkURLRedirectZapatosHeEspanya(dCtxSh.channel, dCtxSh.appE, dFTest);
        
        //Step. Acceso a la aplicación shop/outlet/VOTF sin registrarse posteriormente
        dCtxSh.pais = this.francia;
        dCtxSh.idioma = this.francia_frances;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        
        //Step. Acceso a la redirección de Ficha        
        SecMenusDesktopStpV.checkURLRedirectFicha(this.francia, dCtxSh, dFTest);
    }
	
    
    @Test (
        groups={"Otras", "Canal:desktop_App:shop"}, 
        description="Verificar en google la existencia de referencia Mango")
    public void OTR002_check_Busqueda_Google() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        GoogleStpV.accessGoogleAndSearchMango(dFTest.driver);
        GoogleStpV.selectFirstLinkSinPublicidad(dFTest.driver);
    }
    
    @Test (
        groups={"Otras", "Canal:desktop_App:all"}, 
        description="Verificar el cambio de país a través del modal")
    public void OTR003_cambioPais() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
	    
        //Step. Acceder a la aplicación en España/Castellano y después realiza un cambio a Francia/Francés
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.accesoPRYCambioPais(dCtxSh, this.francia, this.francia_frances, dFTest);
    }

    /**
     * Caso de prueba que realiza el flujo de accesos vía URL/confirmaciones de país para certificar el correcto funcionamiento del modal de confirmación de país.
     */
    @Test (
        groups={"Otras", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, 
        description="Verificar el cambio de país a través de url")
    public void OTR004_cambioPaisURL(ITestContext context) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        String urlBaseTest = (String)context.getAttribute("appPath");

        //Definimos la lista de los 3 países que pueden estar asociados a la IP del usuario
        List<Pais> listPaisAsocIP = new ArrayList<>();
        listPaisAsocIP.add(this.españa);
        listPaisAsocIP.add(this.irlanda);
        listPaisAsocIP.add(this.USA);
            
        //Acceso vía URL con:
        //   país de acceso: suecia (no asociado a la IP del usuario)
        //   país de acceso previo: ninguno (null)
        //   país previamente confirmado: ninguno (null)
        Pais paisAsocIP = AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, this.suecia, null/*paisAccesoPrevio*/, null/*paisConfirmado*/, 0/*vecesPaisConfPrev*/, listPaisAsocIP, dFTest);
                
        //Acceso vía URL con:
        //   país de acceso: francia (no asociado a la IP del usuario)
        //   país de acceso previo: suecia
        //   país previamente confirmado: ninguno (null)
        AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, this.francia, this.suecia, null/*paisConfirmado*/, 0/*vecesPaisConfPrev*/, listPaisAsocIP, dFTest);
                    
        //Step. Confirmamos el país del modal (España, Irlanda o USA... el de paisAsocIP)
        AccesoStpV.selectConfirmPaisModal(dFTest);        

        //Acceso vía URL con:
        //   país de acceso: francia (no asociado a la IP del usuario)
        //   país de acceso previo: suecia
        //   país previamente confirmado: paisAsocIp (España, Irlanda o USA)
        //   número de veces confirmado: 1
        AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, this.francia, this.suecia, paisAsocIP/*paisConfirmado*/, 1/*vecesPaisConfPrev*/, listPaisAsocIP, dFTest);
            
        //Step. Confirmamos el país del modal (España, Irlanda o USA... el de paisAsocIP)
        AccesoStpV.selectConfirmPaisModal(dFTest);
            
        //Acceso vía URL con:
        //   país de acceso: suecia (no asociado a la IP del usuario)
        //   país de acceso previo: francia
        //   país previamente confirmado: paisAsocIp (España, Irlanda o USA)
        //   número de veces confirmado: 2
        AccesoStpV.accesoConURLPaisNoIP(urlBaseTest, this.suecia, this.francia, paisAsocIP/*paisConfirmado*/, 2/*vecesPaisConfPrev*/, listPaisAsocIP, dFTest);            
                
        //Steps. Acabamos ejecutando la funcionalidad típica de cambio de país desde el footer
        dCtxSh.pais = this.francia;
        dCtxSh.idioma = this.francia_frances;
        SecFooterStpV.cambioPais(dCtxSh, dFTest.driver);
    }

    
    /**
    /* Acceso a la prehome, selección de Japón/Japonés y validaciones de que aparece la portada de la shop específica de Japón
     */
    @Test (
        groups={"Otras", "Canal:desktop_App:shop"}, 
        description="Acceso al país Japón desde la preHome y comprobación de que redirige a la shop específica de este país")
    public void OTR005_accesoJapon(ITestContext context) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        String urlBaseTest = (String)context.getAttribute("appPath");

        dCtxSh.pais = this.japon;
        dCtxSh.idioma = this.japones;
        PagePrehomeStpV.seleccionPaisIdioma(urlBaseTest, dCtxSh, dFTest.driver);
        PagePrehomeStpV.entradaShopGivenPaisSeleccionado(this.japon, this.japones, dCtxSh.channel, dFTest.driver);
        PageIniShopJaponStpV.validaPageIniJapon(2, dFTest.driver);
    }	
    
    /**
    /* Acceso a la prehome, selección de Japón/Japonés y validaciones de que aparece la portada de la shop específica de Japón
     */
//    @Test (
//        groups={"Otras", "Canal:desktop_App:shop"}, 
//        description="Pruebas aspectos")
    public void OTR006_PruebasAspectos(ITestContext context) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        String urlBaseTest = (String)context.getAttribute("appPath");

        dCtxSh.pais = this.japon;
        dCtxSh.idioma = this.japones;
        PagePrehomeStpV.seleccionPaisIdioma(urlBaseTest, dCtxSh, dFTest.driver);
        step1("Entrar", this.japon, this.japones, dCtxSh.channel, dFTest.driver);
        //validaPageIniJapon3(dFTest.driver);
        step2withException("Entrar", this.japon, this.japones, dCtxSh.channel, dFTest.driver);
        //Ok...validaPageIniJapon1(datosStep, dFTest.driver);
        //validaPageIniJapon1_5(datosStep, 3, "nos fumamos un porro", dFTest.driver);
        //Ok... validaPageIniJapon1_6(datosStep, 3, "nos fumamos un porro", dFTest.driver);
        //validaPageIniJapon3(dFTest.driver);
        //validaPageIniJapon4(datosStep, dFTest.driver);
    }	
    
    @Step (
    	description="Pais con codigo <b>#{pais.getCodigo_pais()}</b> Si es preciso introducimos la provincia/idioma y finalmente seleccionamos el botón <b>#{litButton}</b>",
        expected="Se accede a la Shop correctamente después de seleccionar el botón #{pais.getCodigo_pais()}",
        saveImagePage=SaveWhen.Always,
        saveErrorPage=SaveWhen.Never)
    public void step1(String litButton, Pais pais, IdiomaPais idioma, Channel channel, WebDriver driver) 
    throws Exception {
    	PagePrehome.selecionProvIdiomAndEnter(pais, idioma, channel, driver);
    	validaPageIniJapon3(driver);
    }
    
    @Step (
        description="Step2 seleccionamos el botón <b>#{litButton}</b>",
        expected="Step2 seleccionar el botón #{litButton}")
    public static void step2withException(String litButton, Pais pais, IdiomaPais idioma, Channel channel, WebDriver driver) 
    throws Exception {
    	assertTrue(1==2);
    	PagePrehome.selecionProvIdiomAndEnter(pais, idioma, channel, driver);
    }
    
    @Validation (
    	description="1) Estamos en la página inicial de Japón",
        level=State.Warn)
    public boolean validaPageIniJapon1(DatosStep datosStep, WebDriver driver) {
    	int maxSecondsToWait = 2;
        return (PageIniShopJapon.isPageUntil(maxSecondsToWait, driver));
    }
    
    @Validation (
    	description="1) Estamos en la página inicial de Japón (la esperamos hasta #{maxSecondsWait} segundos y #{otraCosa})",
        level=State.Warn)
    public boolean validaPageIniJapon1_5(DatosStep datosStep, int maxSecondsWait, String otraCosa, WebDriver driver) {
        return (!PageIniShopJapon.isPageUntil(maxSecondsWait, driver));
    }
    
    @Validation (
    	description="1) Estamos en la página inicial de Japón (la esperamos hasta #{maxSecondsWait} segundos y #{otraCosa}) y se va a producir una excepción",
        level=State.Warn)
    public boolean validaPageIniJapon1_6(DatosStep datosStep, int maxSecondsWait, String otraCosa, WebDriver driver) {
    	assertTrue(2==26);
        return (!PageIniShopJapon.isPageUntil(maxSecondsWait, driver));
    }
    
    @Validation
    public ListResultValidation validaPageIniJapon3(WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
    	int maxSecondsWait = 3;
    	validations.add(
    		"Estamos en la página inicial de Japón (la esperamos hasta " + maxSecondsWait + ")<br>",
    		PageIniShopJapon.isPageUntil(maxSecondsWait, driver), State.Defect);
    	validations.add(
    		"Estamos en la página inicial de Japón (la esperamos hasta " + maxSecondsWait + ")",
    		!PageIniShopJapon.isPageUntil(maxSecondsWait, driver), State.Warn);
    	return validations;
    }
    
    public void validaPageIniJapon4(DatosStep datosStep, WebDriver driver) {
    	int maxSecondsWait = 3;
        String descripValidac = 
        	"1) Estamos en la página inicial de Japón (la esperamos hasta " + maxSecondsWait + ")<br>" +
        	"2) Estamos en la página inicial de Japón (la esperamos hasta " + maxSecondsWait + ")";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageIniShopJapon.isPageUntil(maxSecondsWait, driver)) {
                listVals.add(1, State.Defect);
            }
            if (PageIniShopJapon.isPageUntil(maxSecondsWait, driver)) {
                listVals.add(2, State.Warn);
            }            
            datosStep.setListResultValidations(listVals);
        }
        finally { 
        	listVals.checkAndStoreValidations(descripValidac); 
        }
    }
}