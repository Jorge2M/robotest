package com.mng.robotest.test80.mango.test.stpv.shop;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageIniShopJapon;

public class PageIniShopJaponStpV {

	@Validation 
    public static ChecksResult validaPageIniJapon(int maxSecondsWait, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Estamos en la página inicial de la shop de Japón (la esperamos hasta " + maxSecondsWait + " segundos):<br>" +
            "   - El título es \"" + PageIniShopJapon.Title + "\"<br>" +        
            "   - La URL contiene \"" + PageIniShopJapon.URL + "\"",
			PageIniShopJapon.isPageUntil(maxSecondsWait, driver), State.Warn);
    	
    	return validations;
    }
}
