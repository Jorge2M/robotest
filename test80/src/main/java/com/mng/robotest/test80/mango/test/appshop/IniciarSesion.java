package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.access.InputParamsTestMaker;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.identificacion.PageIdentificacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.identificacion.PageRecuperaPasswdStpV;



public class IniciarSesion extends GestorWebDriver {

    public IniciarSesion() {}

    @BeforeMethod(groups={"IniciarSesion", "Canal:all_App:all"})
    public void login(ITestContext context, Method method) 
    throws Exception {
        InputParamsTestMaker inputData = TestCaseData.getInputDataTestMaker(context);
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputData.getApp());
        dCtxSh.setChannel(inputData.getChannel());
        dCtxSh.urlAcceso = inputData.getUrlBase();
        
        Integer codEspanya = Integer.valueOf(1);
        List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codEspanya)));
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
        
        Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), "", dCtxSh, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod (groups={"IniciarSesion", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @Test (
        groups={"IniciarSesion", "Canal:desktop_App:shop,outlet"}, /*dependsOnMethods = {"SES002_Registro_OK"},*/ alwaysRun=true, 
        description="Verificar inicio sesión con usuario incorrecto")
    public void SES001_IniciarSesion_NOK() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;
                    
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        PageIdentificacionStpV.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw", dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        PageIdentificacionStpV.inicioSesionDatosKO(Constantes.mail_standard, "chuflapassw", dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        PageIdentificacionStpV.selectHasOlvidadoTuContrasenya(dFTest.driver);
        String emailQA = "eqp.ecommerce.qamango@mango.com";
        PageRecuperaPasswdStpV.inputMailAndClickEnviar(emailQA, dFTest.driver);
    }

    @Test (
        groups={"IniciarSesion", "Canal:desktop_App:shop,outlet"}, /*dependsOnMethods = {"SES003_IniciarSesion_NOK"},*/ alwaysRun=true, 
        description="[Usuario registrado] Verificar inicio sesión")
    public void SES002_IniciarSesion_OK() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
            
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, dFTest.driver);
    }
}