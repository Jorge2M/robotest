package com.mng.robotest.test.appshop.sesion;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.identificacion.PageIdentificacionSteps;
import com.mng.robotest.test.steps.shop.identificacion.PageRecuperaPasswdSteps;

public class Ses001 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.userRegistered = false;
		new AccesoSteps().oneStep(false);
		
		PageIdentificacionSteps pageIdentificacionSteps = new PageIdentificacionSteps();
		pageIdentificacionSteps.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw");
		pageIdentificacionSteps.inicioSesionDatosKO(Constantes.MAIL_PERSONAL, "chuflapassw");
		pageIdentificacionSteps.selectHasOlvidadoTuContrasenya();
		
		String emailQA = "eqp.ecommerce.qamango@mango.com";
		new PageRecuperaPasswdSteps().inputMailAndClickEnviar(emailQA);		
	}

}
