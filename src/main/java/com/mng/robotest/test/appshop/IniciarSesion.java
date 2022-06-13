package com.mng.robotest.test.appshop;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.identificacion.PageIdentificacionStpV;
import com.mng.robotest.test.stpv.shop.identificacion.PageRecuperaPasswdStpV;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;


public class IniciarSesion {

	private final static Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private final static IdiomaPais castellano = espana.getListIdiomas().get(0);
	
	public IniciarSesion() {}   

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = espana;
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
					
		AccesoStpV.oneStep(dCtxSh, false, driver);
		PageIdentificacionStpV.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw", dCtxSh.channel, dCtxSh.appE, driver);
		PageIdentificacionStpV.inicioSesionDatosKO(Constantes.MAIL_PERSONAL, "chuflapassw", dCtxSh.channel, dCtxSh.appE, driver);
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
			
		AccesoStpV.manySteps(dCtxSh, driver);
	}
}