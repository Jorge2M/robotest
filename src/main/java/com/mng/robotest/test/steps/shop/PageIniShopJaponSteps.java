package com.mng.robotest.test.steps.shop;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.PageIniShopJapon;

public class PageIniShopJaponSteps extends StepBase {

	@Validation 
	public ChecksTM validaPageIniJapon(int seconds) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página inicial de la shop de Japón (la esperamos hasta " + seconds + " segundos):<br>" +
			"   - El título es \"" + PageIniShopJapon.TITLE + "\"<br>" +		
			"   - La URL contiene \"" + PageIniShopJapon.URL + "\"",
			new PageIniShopJapon().isPageUntil(seconds), State.Warn);
		
		return checks;
	}
}
