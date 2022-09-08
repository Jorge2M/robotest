package com.mng.robotest.domains.identification.tests;

import com.mng.robotest.domains.identification.steps.PageIdentificacionSteps;
import com.mng.robotest.domains.identification.steps.PageRecuperaPasswdSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.Constantes;

public class Ses001 extends TestBase {

	private final PageIdentificacionSteps pageIdentificacionSteps = new PageIdentificacionSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		checkIncorrectLogins();
		restorePassword();		
	}

	private void checkIncorrectLogins() throws Exception {
		pageIdentificacionSteps.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw");
		pageIdentificacionSteps.inicioSesionDatosKO(Constantes.MAIL_PERSONAL, "chuflapassw");
	}
	
	private void restorePassword() {
		pageIdentificacionSteps.selectHasOlvidadoTuContrasenya();
		String emailQA = "eqp.ecommerce.qamango@mango.com";
		new PageRecuperaPasswdSteps().inputMailAndClickEnviar(emailQA);
	}

}
