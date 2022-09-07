package com.mng.robotest.test.steps.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.AllPages;

public class AllPagesSteps {

	@Validation
	public static ChecksTM validatePageWithFooter(Pais pais, AppEcom app, WebDriver driver) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece el footer",
			(new SecFooter()).isPresent(), State.Warn);
		
		if (pais!=null) {
			checks.add(
				"Aparece el div de contenido asociado al país " + pais.getCodigo_pais(),
				state(Present, By.xpath("//div[@class[contains(.,'main-content')] and @data-pais='" + pais.getCodigo_pais() + "']"), driver).check(),
				State.Warn);
		}
		return checks;
	}
	
	@Validation (
		description="Aparece el div de contenido asociado al país #{pais.getNombre_pais()} (#{pais.getCodigo_pais()})",
		level=State.Warn)
	public static boolean validateMainContentPais(Pais pais, WebDriver driver) {
		return (new AllPages().isPresentMainContent(pais));
	}

	@Step (
		description="Realizamos un <b>back</b> del navegador", 
		expected="Se vuelve a la página anterior")
	public static void backNagegador(WebDriver driver) throws Exception {
		driver.navigate().back();
		int maxSeconds = 10;
		SeleniumUtils.waitForPageLoaded(driver, maxSeconds);
	}
}
