package com.mng.robotest.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageSelTda;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PageJCAS;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageLoginMantoSteps extends StepMantoBase {

	public void login() {
		goToMantoIfNotYet(dataMantoTest.getUrlManto());
		if (!new PageSelTda().isPage()) {
			identFromJasigCasPage(dataMantoTest.getUserManto(), dataMantoTest.getPassManto());
		}
		checkIsPageSelectTienda(25);
	}
	
	@Step (
		description=
			"<b style=\"color:brown;\">Acceder a Manto</b> si todavía no estamos allí (#{urlManto})" +
			"Aparece la página de selección de login o selección de tienda",
		expected=
			"Aparece la página de selección de login o selección de tienda",
		saveErrorData=SaveWhen.Never)
	public void goToMantoIfNotYet(String urlManto) {
		String currentURL = driver.getCurrentUrl();
		if (currentURL.compareTo(urlManto)!=0) {
			driver.manage().deleteAllCookies();
			driver.get(urlManto);
		}
	}

	@Step (
		description="Identificarse desde la página de Jasig CAS con #{usrManto}",
		expected="Aparece la página de selección de la tienda",
		saveErrorData=SaveWhen.Never)
	public void identFromJasigCasPage(String usrManto, String passManto) {
		new PageJCAS().identication(usrManto, passManto);
	}

	@Validation (
		description="Aparece la página de selección de la tienda (la esperamos hasta #{seconds} segundos)",
		level=Warn)
	private boolean checkIsPageSelectTienda(int seconds) {
		return (new PageSelTda().isPage(seconds));
	}
}
