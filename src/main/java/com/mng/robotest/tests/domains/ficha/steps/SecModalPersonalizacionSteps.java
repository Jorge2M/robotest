package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecModalPersonalizacion.ModalElement;

import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class SecModalPersonalizacionSteps extends StepBase {

	@Validation
	public ChecksTM checkModal() {
		int seconds = 3;
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el modal de personalización con el botón <b>Siguiente</b> " + getLitSecondsWait(seconds),
			isBotonSiguienteVisible(seconds), 
			WARN);
		
		checks.add(
			"Aparece la opción <b>Un icono</b> " + getLitSecondsWait(seconds),
			state(VISIBLE, ModalElement.BUTTON_UN_ICONO.getBy(channel)).wait(seconds).check(), 
			WARN);
	
		return checks;
	}

	@Validation(
		description="1) Aparece la cabecera correspondiente a la personalizacion de la prenda",
		level=WARN)
	private boolean validationInitMblCustomization(int seconds, ModalElement element) {
		return (state(VISIBLE, element.getBy(channel)).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos la opción <b>Un icono</b>",
		expected="Aparece la lista de iconos")
	public void selectIconCustomization() {
		click(ModalElement.BUTTON_UN_ICONO.getBy(channel)).type(JAVASCRIPT).exec();
		validationIconSelection(2);
	}

	@Validation(
		description="1) Aparece la lista de iconos seleccionables",
		level=WARN)
	public boolean validationIconSelection(int seconds) {
		return (state(VISIBLE, ModalElement.ICON_SELECTION.getBy(channel)).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos el primer icono",
		expected="La selección es correcta")
	public void selectFirstIcon() {
		if (channel == Channel.desktop) {
			click(ModalElement.ICON_SELECTION.getBy(channel)).exec();
		} else {
			click(ModalElement.ICON_SELECTION.getBy(channel)).type(JAVASCRIPT).exec();
		}
	}

	@Validation
	public ChecksTM validateIconSelectedDesktop() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparece seleccionado el primer icono",
			state(VISIBLE, ModalElement.ICON_SELECTION.getBy()).wait(seconds).check(), 
			WARN);
		checks.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(seconds),
			WARN);
		return checks;
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Se hace visible el paso-2")
	public void selectConfirmarButton () {
		click(getBotonSiguienteVisible()).exec();
	}
	
	private WebElement getBotonSiguienteVisible() {
		return getElementVisible(driver, ModalElement.SIGUIENTE.getBy(channel));
	}
	private boolean isBotonSiguienteVisible(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (getBotonSiguienteVisible()!=null) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	@Validation
	public ChecksTM validateWhereDesktop() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparecen las opciones correspondientes a la ubicación del bordado",
			state(VISIBLE, ModalElement.POSITION_BUTTON.getBy()).wait(seconds).check(),
			WARN);
		checks.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(seconds),
			//state(VISIBLE, ModalElement.Siguiente.getBy()).wait(seconds).check(),
			WARN);
		return checks;
	}

	@Validation
	public ChecksTM validateSelectionColor() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparecen los botones correspondientes a los colores",
			state(VISIBLE, ModalElement.COLORS_CONTAINER.getBy()).wait(seconds).check(),
			WARN);
		
		checks.add(
			"Aparece el botón de \"Confirmar\"",
			state(PRESENT, ModalElement.SIGUIENTE.getBy()).wait(seconds).check(),
			WARN);
		
		return checks;
	}

	@Step(
		description="Seleccionamos el botón \"Confirmar\"",
		expected="Aparece el apartado 4 de la personalización")
	public void selectSize () {
		click(getBotonSiguienteVisible()).exec();
		validateSizeList(2);
	}

	@Validation(
		description="1) Aparecen los botones con los posibles tamaños del bordado",
		level=WARN)
	private boolean validateSizeList(int seconds) {
		return (state(VISIBLE, ModalElement.SIZE_CONTAINER.getBy(channel)).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Aparece el modal con las opciones para ver la bolsa o seguir comprando")
	public void confirmCustomization() {
		click(getBotonSiguienteVisible()).exec();
		validateAddBag(2);
	}

	@Validation (description="1) Aparece el botón para añadir a la bolsa", level=WARN)
	private boolean validateAddBag(int seconds) {
		return isBotonSiguienteVisible(seconds);
	}

	@Step(
		description="Seleccionar botón <b>Añadir a la bolsa</b>",
		expected="El artículo se da de alta correctamente en la bolsa"	)
	public void checkCustomizationProof () {
		click(getBotonSiguienteVisible()).waitLoadPage(2).exec();
		if (channel.isDevice()) {
			new SecBolsa().setBolsaToStateIfNotYet(StateBolsa.OPEN);
		}
		validateCustomizationProof(2);
	}

	@Validation(
		description="En la bolsa aparece el apartado correspondiente a la personalización " + SECONDS_WAIT)
	private boolean validateCustomizationProof(int seconds) {
		return (state(VISIBLE, ModalElement.BOLSA_PROOF.getBy(channel)).wait(seconds).check());
	}

	@Validation(
		description="Es visible el apartado <b>#{level}</b>",
		level=WARN)
	public boolean validateCabeceraStep(int level) {
		switch (level) {
		case 1:
			return (state(VISIBLE, ModalElement.STEP1_PROOF.getBy()).wait(4).check());
		case 2:
			return (state(VISIBLE, ModalElement.STEP2_PROOF.getBy()).wait(4).check());
		case 3:
			return (state(VISIBLE, ModalElement.STEP3_PROOF.getBy()).wait(4).check());
		case 4:
			return (state(VISIBLE, ModalElement.STEP4_PROOF.getBy()).wait(4).check());
		default:
			return false;
		}
	}
}
