package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.identificacion.PageIdentificacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.identificacion.PageRecuperaPasswdStpV;
import com.mng.testmaker.service.TestMaker;


public class IniciarSesion {

    private final static Integer codEspanya = Integer.valueOf(1);
    private final static List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codEspanya)));
    private final static Pais españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
    private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	
    public IniciarSesion() {}   
    
    private DataCtxShop getCtxShForTest() throws Exception {
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        dCtxSh.pais = españa;
        dCtxSh.idioma = castellano;
        return dCtxSh;
    }

    @Test (
        groups={"IniciarSesion", "Canal:desktop_App:shop,outlet"}, /*dependsOnMethods = {"SES002_Registro_OK"},*/ alwaysRun=true, 
        description="Verificar inicio sesión con usuario incorrecto")
    public void SES001_IniciarSesion_NOK() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
                    
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        PageIdentificacionStpV.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw", dCtxSh.channel, dCtxSh.appE, driver);
        PageIdentificacionStpV.inicioSesionDatosKO(Constantes.mail_standard, "chuflapassw", dCtxSh.channel, dCtxSh.appE, driver);
        PageIdentificacionStpV.selectHasOlvidadoTuContrasenya(driver);
        String emailQA = "eqp.ecommerce.qamango@mango.com";
        PageRecuperaPasswdStpV.inputMailAndClickEnviar(emailQA, driver);
    }

    @Test (
        groups={"IniciarSesion", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
        description="[Usuario registrado] Verificar inicio sesión")
    public void SES002_IniciarSesion_OK() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
            
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, driver);
    }
}