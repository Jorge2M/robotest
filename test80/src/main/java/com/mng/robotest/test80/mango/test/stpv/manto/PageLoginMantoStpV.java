package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageJCAS;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Login de Manto
 * @author jorge.munoz
 *
 */
public class PageLoginMantoStpV {

	public static void login(String urlManto, String usrManto, String passManto, WebDriver driver) throws Exception {
		goToMantoIfNotYet(urlManto, driver);
		if (!PageSelTda.isPage(driver)) {
			identFromJasigCasPage(usrManto, passManto, driver);
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
	public static void goToMantoIfNotYet(String urlManto, WebDriver driver) throws Exception {
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
	public static void identFromJasigCasPage(String usrManto, String passManto, WebDriver driver) throws Exception {
		PageJCAS.identication(driver, usrManto, passManto);
	}

	@Validation (
		description="Aparece la página de selección de la tienda (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private static boolean checkIsPageSelectTienda(int maxSeconds, WebDriver driver) {
		return (PageSelTda.isPage(maxSeconds, driver));
	}
}
