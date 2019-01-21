package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.identificacion.PageIdentificacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.identificacion.PageRecuperaPasswdStpV;

import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
public class IniciarSesion extends GestorWebDriver {

    public IniciarSesion() {}

    @BeforeMethod(groups={"IniciarSesion", "Canal:all_App:all"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        Integer codEspanya = Integer.valueOf(1);
        List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        this.clonePerThreadCtx();
        
        createDriverInThread(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod (groups={"IniciarSesion", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getDriver().driver;
        super.quitWebDriver(driver, context);
    }       

    @Test (
        groups={"IniciarSesion", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, /*dependsOnMethods = {"SES002_Registro_OK"},*/ alwaysRun=true, 
        description="Verificar inicio sesión con usuario incorrecto")
    public void SES001_IniciarSesion_NOK(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.userRegistered = false;
                    
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        PageIdentificacionStpV.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw", dCtxSh.channel, dCtxSh.appE, dFTest);
        PageIdentificacionStpV.inicioSesionDatosKO(Constantes.mail_standard, "chuflapassw", dCtxSh.channel, dCtxSh.appE, dFTest);
        PageIdentificacionStpV.selectHasOlvidadoTuContrasenya(dFTest);
        String emailQA = "eqp.ecommerce.qamango@mango.com";
        PageRecuperaPasswdStpV.inputMailAndClickEnviar(emailQA, dFTest);
    }

    @Test (
        groups={"IniciarSesion", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, /*dependsOnMethods = {"SES003_IniciarSesion_NOK"},*/ alwaysRun=true, 
        description="[Usuario registrado] Verificar inicio sesión")
    public void SES002_IniciarSesion_OK(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
            
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, dFTest);
    }
}