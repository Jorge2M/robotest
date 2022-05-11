package com.mng.robotest.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.ficha.ModEnvioYdevolNew;

public class ModEnvioYdevolNewStpV {

	private final ModEnvioYdevolNew modEnvioYdev;
	
	public ModEnvioYdevolNewStpV(WebDriver driver) {
		modEnvioYdev = new ModEnvioYdevolNew(driver);
	}
	
	@Validation (
		description="Aparece el modal con los datos a nivel de envío y devolución",
		level=State.Defect)
	public boolean checkIsVisible() {
		int maxSecondsToWait = 1;
		return (modEnvioYdev.isVisibleUntil(maxSecondsToWait));
	}

	@Step (
		description="Seleccionar el aspa para cerrar el modal de \"Envío y devolución\"",
		expected="Desaparece el modal")
	public void clickAspaForClose() throws Exception {
		modEnvioYdev.clickAspaForClose();
		checkIsVisibleModalDatosEnvio();
	}
	
	@Validation (
		description="No es visible el modal con los datos a nivel de envío y devolución",
		level=State.Warn)
	private boolean checkIsVisibleModalDatosEnvio() {
		int maxSecondsToWait = 1;
		return (!modEnvioYdev.isVisibleUntil(maxSecondsToWait));
	}
}
