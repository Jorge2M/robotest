package com.mng.robotest.domains.login.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.login.steps.PageIdentificacionSteps;
import com.mng.robotest.domains.login.steps.PageRecuperaPasswdSteps;
import com.mng.robotest.test.data.Constantes;

public class Log001 extends TestBase {

	private final PageIdentificacionSteps pageIdentificacionSteps = new PageIdentificacionSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		checkIncorrectLogins();
		restorePassword();		
	}

	private void checkIncorrectLogins() {
		pageIdentificacionSteps.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw");
		pageIdentificacionSteps.inicioSesionDatosKO(Constantes.MAIL_PERSONAL, "chuflapassw");
	}
	
	private void restorePassword() {
		pageIdentificacionSteps.selectHasOlvidadoTuContrasenya();
		String emailQA = "eqp.ecommerce.qamango@mango.com";
		new PageRecuperaPasswdSteps().inputMailAndClickEnviar(emailQA);
	}

}
