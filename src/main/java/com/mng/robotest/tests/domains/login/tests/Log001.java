package com.mng.robotest.tests.domains.login.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.login.steps.PageIdentificacionSteps;
import com.mng.robotest.tests.domains.login.steps.PageRecuperaPasswdSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.data.Constantes;

public class Log001 extends TestBase {

	private final PageIdentificacionSteps pageIdentificacionSteps = new PageIdentificacionSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		checkIncorrectLogins();
		restorePassword();		
	}

	private void checkIncorrectLogins() {
		var accesoSteps = new AccesoSteps();
		accesoSteps.inicioSesionDatosKO("usuarioKeNoExiste@mango.com", "chuflapassw");
		accesoSteps.inicioSesionDatosKO(Constantes.MAIL_PERSONAL, "chuflapassw");
	}
	
	private void restorePassword() {
		pageIdentificacionSteps.selectHasOlvidadoTuContrasenya();
		String emailQA = "eqp.ecommerce.qamango@mango.com";
		new PageRecuperaPasswdSteps().inputMailAndClickEnviar(emailQA);
	}

}
