package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageJCAS;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Login de Manto
 * @author jorge.munoz
 *
 */
public class PageLoginMantoStpV {

	public static void login(String urlManto, String usrManto, String passManto, WebDriver driver) throws Exception {
		goToManto(urlManto, driver);
		if (!PageSelTda.isPage(driver)) {
			identFromJasigCasPage(usrManto, passManto, driver);
		}
		
		checkIsPageSelectTienda(driver);
	}
	
    @Step (
    	description=
    		"<b style=\"color:brown;\">ACCEDER A MANTO</b> (#{urlManto})" +
    		"Aparece la página de selección de login o selección de tienda",
    	expected=
    		"Aparece la página de selección de login o selección de tienda",
    	saveErrorData=SaveWhen.Never)
    public static void goToManto(String urlManto, WebDriver driver) throws Exception {
    	driver.manage().deleteAllCookies();
        driver.get(urlManto);
    }
    
    @Step (
    	description="Identificarse desde la página de Jasig CAS con #{usrManto}",
    	expected="Aparece la página de selección de la tienda",
    	saveErrorData=SaveWhen.Never)
    public static void identFromJasigCasPage(String usrManto, String passManto, WebDriver driver) throws Exception {
    	PageJCAS.identication(driver, usrManto, passManto);
    }
    
    @Validation (
    	description="Aparece la página de selección de la tienda",
    	level=State.Warn)
    private static boolean checkIsPageSelectTienda(WebDriver driver) {
    	return (PageSelTda.isPage(driver));
    }
}
