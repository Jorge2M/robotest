package com.mng.robotest.test.stpv.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageIniShopJapon;

public class PageIniShopJaponStpV {

	@Validation 
	public static ChecksTM validaPageIniJapon(int maxSeconds, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Estamos en la página inicial de la shop de Japón (la esperamos hasta " + maxSeconds + " segundos):<br>" +
			"   - El título es \"" + PageIniShopJapon.Title + "\"<br>" +		
			"   - La URL contiene \"" + PageIniShopJapon.URL + "\"",
			PageIniShopJapon.isPageUntil(maxSeconds, driver), State.Warn);
		
		return validations;
	}
}
