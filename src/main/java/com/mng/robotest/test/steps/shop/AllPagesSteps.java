package com.mng.robotest.test.steps.shop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.AllPages;

public class AllPagesSteps extends StepBase {

	@Validation
	public ChecksTM validatePageWithFooter() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el footer",
			(new SecFooter()).isPresent(), State.Warn);
		
		if (dataTest.getPais()!=null) {
			String codigoPais = dataTest.getCodigoPais();
			checks.add(
				"Aparece el div de contenido asociado al país " + codigoPais,
				state(Present, By.xpath("//div[@class[contains(.,'main-content')] and @data-pais='" + dataTest.getCodigoPais() + "']"), driver).check(),
				State.Warn);
		}
		return checks;
	}
	
	@Validation (
		description="Aparece el div de contenido asociado al país #{pais.getNombre_pais()} (#{pais.getCodigo_pais()})",
		level=State.Warn)
	public boolean validateMainContentPais() {
		return (new AllPages().isPresentMainContent());
	}

	@Step (
		description="Realizamos un <b>back</b> del navegador", 
		expected="Se vuelve a la página anterior")
	public void backNagegador() {
		driver.navigate().back();
		int seconds = 10;
		PageObjTM.waitForPageLoaded(driver, seconds);
	}
}
