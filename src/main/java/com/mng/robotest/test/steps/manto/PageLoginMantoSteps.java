package com.mng.robotest.test.steps.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test.pageobject.shop.PageJCAS;


public class PageLoginMantoSteps {

	public static void login(String urlManto, String usrManto, String passManto, WebDriver driver) {
		goToMantoIfNotYet(urlManto, driver);
		if (!PageSelTda.isPage(driver)) {
			identFromJasigCasPage(usrManto, passManto);
		}
		checkIsPageSelectTienda(25, driver);
	}
	
	@Step (
		description=
			"<b style=\"color:brown;\">Acceder a Manto</b> si todavía no estamos allí (#{urlManto})" +
			"Aparece la página de selección de login o selección de tienda",
		expected=
			"Aparece la página de selección de login o selección de tienda",
		saveErrorData=SaveWhen.Never)
	public static void goToMantoIfNotYet(String urlManto, WebDriver driver) {
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
	public static void identFromJasigCasPage(String usrManto, String passManto) {
		new PageJCAS().identication(usrManto, passManto);
	}

	@Validation (
		description="Aparece la página de selección de la tienda (la esperamos hasta #{seconds} segundos)",
		level=State.Warn)
	private static boolean checkIsPageSelectTienda(int seconds, WebDriver driver) {
		return (PageSelTda.isPage(seconds, driver));
	}
}
