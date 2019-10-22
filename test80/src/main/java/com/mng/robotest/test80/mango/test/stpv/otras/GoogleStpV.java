package com.mng.robotest.test80.mango.test.stpv.otras;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.otras.PageGoogle;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;

public class GoogleStpV {

	@Step (
		description="Accedemos a la URL de Google (http://www.google.es\") y buscamos \"MANGO\"", 
        expected="Aparecen los links de Mango con contenido correcto",
        saveHtmlPage=SaveWhen.IfProblem)
    public static void accessGoogleAndSearchMango(WebDriver driver) throws Exception {
        PageGoogle.accessViaURL(driver);
        PageGoogle.searchTextAndWait(driver, "MANGO");       
        checkLinksMango(driver);
    }
	
	@Validation
	private static ChecksResult checkLinksMango(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 3;
    	validations.add(
    		"El 1er link no-anuncio contiene \"MANGO\" (lo esperamos " + maxSecondsWait + " segundos)",
    		PageGoogle.validaFirstLinkContainsUntil("MANGO", maxSecondsWait, driver) ||
    		PageGoogle.validaFirstLinkContains("Mango", driver), State.Defect);		
    	validations.add(
    		"El 1er link no-anuncion no contiene \"robots.txt\"",
    		!PageGoogle.validaFirstLinkContainsUntil("robots.txt", 0, driver), State.Warn);
    	return validations;
	}

	@Step (
		description="Seleccionamos el 1er link normal (sin publicidad)", 
        expected="Aparece la página inicial de la shop de Mango")
    public static void selectFirstLinkSinPublicidad(WebDriver driver) throws Exception { 
		PageGoogle.clickFirstLinkNoPubli(driver);
    
        //Validaciones
		checkInitialPageShop(driver);
    }
	
	@Validation (
		description="Aparece la página de <b>Landing</b> o <b>Prehome</b>",
		level=State.Defect)	
	private static boolean checkInitialPageShop(WebDriver driver) throws Exception {
        boolean isPageLanding = PageLanding.isPage(driver);
        boolean isPagePrehome = PagePrehome.isPage(driver);
        return (isPageLanding || isPagePrehome);
	}
}
