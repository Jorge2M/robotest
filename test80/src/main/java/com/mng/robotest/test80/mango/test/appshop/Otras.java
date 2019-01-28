package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.*;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.otras.GoogleStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PageIniShopJaponStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;

import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
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
        //testResources.skipTestsIfSuiteStoped(context);
        
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        this.clonePerThreadCtx();
        
        //Creamos el WebDriver con el que ejecutaremos el Test
        createDriverInThread(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
        
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
        WebDriver driver = getDriver().driver;
        super.quitWebDriver(driver, context);
    }		
	
    
    @Test (
        groups={"Otras", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, 
        description="Comprobar acceso url desde email")
    public void OTR001_check_Redirects(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
	    
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
    public void OTR002_check_Busqueda_Google(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        GoogleStpV.accessGoogleAndSearchMango(dFTest);
        GoogleStpV.selectFirstLinkSinPublicidad(dFTest);
    }
    
    @Test (
        groups={"Otras", "Canal:desktop_App:all"}, 
        description="Verificar el cambio de país a través del modal")
    public void OTR003_cambioPais(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
	    
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
    public void OTR004_cambioPaisURL(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
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
        SecFooterStpV.cambioPais(dCtxSh, dFTest);
    }

    
    /**
    /* Acceso a la prehome, selección de Japón/Japonés y validaciones de que aparece la portada de la shop específica de Japón
     */
    @Test (
        groups={"Otras", "Canal:desktop_App:shop"}, 
        description="Acceso al país Japón desde la preHome y comprobación de que redirige a la shop específica de este país")
    public void OTR005_accesoJapon(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        String urlBaseTest = (String)context.getAttribute("appPath");

        dCtxSh.pais = this.japon;
        dCtxSh.idioma = this.japones;
        PagePrehomeStpV.seleccionPaisIdioma(urlBaseTest, dCtxSh, dFTest);
        DatosStep datosStep = PagePrehomeStpV.entradaShopGivenPaisSeleccionado(this.japon, this.japones, dCtxSh.channel, dFTest);
        PageIniShopJaponStpV.validaPageIniJapon(datosStep, dFTest);
    }	
}