package com.mng.robotest.tests.domains.transversal.acceptcookies.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects.ModalSetCookies;
import com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects.ModalSetCookies.SectionConfCookies;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalSetCookiesSteps extends StepBase {

	private final ModalSetCookies modalSetCookies = new ModalSetCookies();
	
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
		description="Seleccionamos la sección <b>#{section.getNombre()</b>}",
		expected="Se despliegua la sección")
	public void select(SectionConfCookies section) {
		modalSetCookies.clickSection(section);
		checkSectionUnfold(section);
	}
	
	@Validation (
		description="Está seleccionada la sección #{section.getNombre()}",
		level=WARN)
	public boolean checkSectionUnfold(SectionConfCookies section) {
		return modalSetCookies.isSectionUnfold(section);
	}
	
	@Step (
		description="Activamos el switch de las cookies",
		expected="El switch pasa a enabled")
	public void enableSwitchCookies() {
		modalSetCookies.enableSwitchCookies();
		checkSwitchEnabled();
	}
	
	@Step (
		description="Desactivamos el switch de las cookies",
		expected="El switch pasa a disabled")
	public void disableSwitchCookies() {
		modalSetCookies.disableSwitchCookies();
		checkSwitchNotEnabled();
	}
	
	@Validation (
		description="El switch de las cookies está activado",
		level=WARN)
	public boolean checkSwitchEnabled() {
		return modalSetCookies.isSwitchEnabled();
	}
	
	@Validation (
		description="El switch de las cookies está desactivado",
		level=WARN)
	public boolean checkSwitchNotEnabled() {
		return !modalSetCookies.isSwitchEnabled();
	}
	
	@Step (
		description="Guardamos la configuración",
		expected="Desaparece el modal de configuración de cookies")
	public void saveConfiguration() {
		modalSetCookies.saveConfiguration();
		isInvisible(2);
	}
}
