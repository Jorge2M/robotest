package com.mng.robotest.test80.mango.test.stpv.shop.acceptcookies;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies.ModalSetCookies;
import com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies.ModalSetCookies.SectionConfCookies;

public class ModalSetCookiesStpV {

	private final ModalSetCookies modalSetCookies;
	
	public ModalSetCookiesStpV(WebDriver driver) {
		modalSetCookies = new ModalSetCookies(driver);
	}
	
	@Validation (
		description="Es visible el modal para el seteo de Cookies (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
    public boolean isVisible(int maxSeconds) {
		return (modalSetCookies.isVisible(maxSeconds));
    }
	
	@Validation (
		description="No es visible el modal para el seteo de Cookies (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
    public boolean isInvisible(int maxSeconds) {
		return (modalSetCookies.isInvisible(maxSeconds));
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
		level=State.Warn)
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
		level=State.Warn)
	public boolean checkSwitchEnabled() {
		return modalSetCookies.isSwitchEnabled();
	}
	
	@Validation (
		description="El switch de las cookies está desactivado",
		level=State.Warn)
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