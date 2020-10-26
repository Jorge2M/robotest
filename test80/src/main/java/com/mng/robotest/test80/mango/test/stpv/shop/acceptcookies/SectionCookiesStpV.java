package com.mng.robotest.test80.mango.test.stpv.shop.acceptcookies;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies.SectionCookies;

public class SectionCookiesStpV {

	private final SectionCookies sectionCookies;
	private final ModalSetCookiesStpV modalSetCookiesStpV;
	
	public SectionCookiesStpV(WebDriver driver) {
		sectionCookies = new SectionCookies(driver);
		modalSetCookiesStpV = new ModalSetCookiesStpV(driver);
	}
	
	@Step (
		description="Seleccionamos el botón \"Aceptar\" de la sección inferior de configuración de cookies",
		expected="Desaparece la sección de configuración de cookies")
	public void accept() {
		sectionCookies.accept();
		checkSectionInvisible();
	}
	
	@Step (
		description="Seleccionamos el botón \"Configurar cookies\" de la sección inferior de configuración de cookies",
		expected="Aparece el modal para la configuración de las cookies")
	public ModalSetCookiesStpV setCookies() {
		sectionCookies.setCookies();
		modalSetCookiesStpV.isVisible(2);
		return modalSetCookiesStpV;
	}
	
	@Validation (
		description="No es visible la sección inferior para la configuración de las cookies",
		level=State.Defect)
    public boolean checkSectionInvisible() {
		return (sectionCookies.isInvisible(2));
    }
	
}
