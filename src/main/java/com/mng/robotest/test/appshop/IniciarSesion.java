package com.mng.robotest.test.appshop;

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
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.identificacion.PageIdentificacionSteps;
import com.mng.robotest.test.steps.shop.identificacion.PageRecuperaPasswdSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

public class IniciarSesion {

	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);
	
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
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;
		new AccesoSteps().oneStep(dCtxSh, false);
		
		PageIdentificacionSteps pageIdentificacionSteps = new PageIdentificacionSteps();
		pageIdentificacionSteps.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw");
		pageIdentificacionSteps.inicioSesionDatosKO(Constantes.MAIL_PERSONAL, "chuflapassw");
		pageIdentificacionSteps.selectHasOlvidadoTuContrasenya();
		
		String emailQA = "eqp.ecommerce.qamango@mango.com";
		new PageRecuperaPasswdSteps().inputMailAndClickEnviar(emailQA);
	}

	@Test (
		groups={"IniciarSesion", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description="[Usuario registrado] Verificar inicio sesión")
	public void SES002_IniciarSesion_OK() throws Exception {
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered = true;
			
		new AccesoSteps().manySteps(dCtxSh);
	}
}