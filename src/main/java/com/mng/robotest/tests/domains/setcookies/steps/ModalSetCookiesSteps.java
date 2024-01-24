package com.mng.robotest.tests.domains.setcookies.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.setcookies.pageobjects.ModalSetCookies;
import com.mng.robotest.tests.domains.setcookies.pageobjects.ModalSetCookies.CookiesType;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalSetCookiesSteps extends StepBase {

	private final ModalSetCookies modalSetCookies = ModalSetCookies.make(channel, app);
	
	@Validation (
		description="Es visible el modal para el seteo de Cookies " + SECONDS_WAIT)
	public boolean isVisible(int seconds) {
		return modalSetCookies.isVisible(seconds);
	}
	
	@Validation (
		description="No es visible el modal para el seteo de Cookies " + SECONDS_WAIT,
		level=WARN)
	public boolean isInvisible(int seconds) {
		return modalSetCookies.isInvisible(seconds);
	}
	
	@Step (
		description="Activamos la sección <b>#{section.getNombre()}</b>",
		expected="Se despliegua la sección")
	public void enable(CookiesType section) {
		modalSetCookies.enable(section);
		checkIsEnabled(section);
	}
	
	@Step (
		description="Desactivamos el switch de las cookies <b>#{section.getNombre()}</b>",
		expected="El switch pasa a disabled")
	public void disableSwitchCookies(CookiesType section) {
		modalSetCookies.disable(section);
		checkIsDisabled(section);
	}
	
	@Validation (
		description="Las cookies <b>#{section.getNombre()}</b> están activadas",
		level=WARN)
	public boolean checkIsEnabled(CookiesType section) {
		return modalSetCookies.isEnabled(section);
	}
	
	@Validation (
		description="Las cookies <b>#{section.getNombre()</b> están desactivadas",
		level=WARN)
	public boolean checkIsDisabled(CookiesType section) {
		return !modalSetCookies.isEnabled(section);
	}
	
	@Step (
		description="Guardamos la configuración",
		expected="Desaparece el modal de configuración de cookies")
	public void saveConfiguration() {
		modalSetCookies.saveConfiguration();
		isInvisible(2);
	}
}
