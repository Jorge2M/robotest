package com.mng.robotest.test.steps.shop;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;

public class SecCabeceraSteps extends StepBase {

	private final SecCabecera secCabecera = new SecCabeceraMostFrequent();
	
	public SecCabecera getSecCabecera() {
		return secCabecera;
	}

	@Step (
		description="Seleccionar el logo de Mango", 
		expected="Se accede a la página principal de la línea")
	public void selecLogo() {
		secCabecera.clickLogoMango();
	}

	@Validation
	public ChecksTM validateIconoBolsa() {
		var checks = ChecksTM.getNew();
		boolean isVisibleIconoBolsa = secCabecera.isInStateIconoBolsa(Visible, 2);
		if (dataTest.getPais().isVentaOnline()) {
			checks.add(
				"<b>Sí</b> es posible comprar (aparece la capa relacionada con la bolsa)",
				isVisibleIconoBolsa, State.Warn);
		} else {
			checks.add(
				"<b>No</b> es posible comprar (aparece la capa relacionada con la bolsa)",
				!isVisibleIconoBolsa, State.Warn);
		}
		return checks;
	}
	
	@Step (
		description="Establecer con visibilidad #{setVisible} el menú izquierdo de móvil",
		expected="El menú lateral se establece con visibilidad #{setVisible}")
	public void setVisibilityLeftMenuMobil(boolean setVisible) {
		secCabecera.clickIconoMenuHamburguerMobil(setVisible);
	}
}
