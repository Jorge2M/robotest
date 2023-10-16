package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.tests.domains.ficha.pageobjects.SecModalPersonalizacion.ModalElement;

import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class SecModalPersonalizacionSteps extends StepBase {

	public boolean checkArticleCustomizable() {
		return checkArticleCustomizable(Defect);
	}
	
	public boolean checkArticleCustomizable(State levelError) {
		var checks = checkAreArticleCustomizable(levelError);
		for (var check : checks.getListChecks()) {
			if (!check.isOvercomed()) {
				return false;
			}
		}
		return true;
	}
	
	@Validation
	private ChecksTM checkAreArticleCustomizable(State levelError) {
		var checks = ChecksTM.getNew();
		checks.add(
			"El artículo es personalizable (aparece el link \"Añadir bordado\")",
			state(Present, ModalElement.ANADIR_BORDADO_LINK.getBy(channel)).wait(1).check(), 
			levelError);
		return checks;
	}

	@Step(
		description="Seleccionamos el link <b>Añadir bordado</b>",
		expected="Aparece el modal para la personalización de la prenda")
	public void selectLinkPersonalizacion () {
		click(ModalElement.ANADIR_BORDADO_LINK.getBy(channel)).type(javascript).exec();
		validateModal();
		checksDefault();
	}
	
	@Validation
	private ChecksTM validateModal() {
		int seconds = 3;
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el modal de personalización con el botón <b>Siguiente</b> " + getLitSecondsWait(seconds),
			isBotonSiguienteVisible(seconds), 
			Warn);
		
		checks.add(
			"Aparece la opción <b>Un icono</b> " + getLitSecondsWait(seconds),
			state(Visible, ModalElement.BUTTON_UN_ICONO.getBy(channel)).wait(seconds).check(), 
			Warn);
	
		return checks;
	}

	@Validation(
		description="1) Aparece la cabecera correspondiente a la personalizacion de la prenda",
		level=Warn)
	private boolean validationInitMblCustomization(int seconds, ModalElement element) {
		return (state(Visible, element.getBy(channel)).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos la opción <b>Un icono</b>",
		expected="Aparece la lista de iconos")
	public void selectIconCustomization() {
		click(ModalElement.BUTTON_UN_ICONO.getBy(channel)).type(javascript).exec();
		validationIconSelection(2);
	}

	@Validation(
		description="1) Aparece la lista de iconos seleccionables",
		level=Warn)
	public boolean validationIconSelection(int seconds) {
		return (state(Visible, ModalElement.ICON_SELECTION.getBy(channel)).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos el primer icono",
		expected="La selección es correcta")
	public void selectFirstIcon() {
		if (channel == Channel.desktop) {
			click(ModalElement.ICON_SELECTION.getBy(channel)).exec();
		} else {
			click(ModalElement.ICON_SELECTION.getBy(channel)).type(javascript).exec();
		}
	}

	@Validation
	public ChecksTM validateIconSelectedDesktop() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparece seleccionado el primer icono",
			state(Visible, ModalElement.ICON_SELECTION.getBy()).wait(seconds).check(), 
			Warn);
		checks.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(seconds),
			Warn);
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
			state(Visible, ModalElement.POSITION_BUTTON.getBy()).wait(seconds).check(),
			Warn);
		checks.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(seconds),
			//state(Visible, ModalElement.Siguiente.getBy()).wait(seconds).check(),
			Warn);
		return checks;
	}

	@Validation
	public ChecksTM validateSelectionColor() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparecen los botones correspondientes a los colores",
			state(Visible, ModalElement.COLORS_CONTAINER.getBy()).wait(seconds).check(),
			Warn);
		checks.add(
			"Aparece el botón de \"Confirmar\"",
			state(Present, ModalElement.SIGUIENTE.getBy()).wait(seconds).check(),
			Warn);
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
		level=Warn)
	private boolean validateSizeList(int seconds) {
		return (state(Visible, ModalElement.SIZE_CONTAINER.getBy(channel)).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Aparece el modal con las opciones para ver la bolsa o seguir comprando")
	public void confirmCustomization() {
		click(getBotonSiguienteVisible()).exec();
		validateAddBag(2);
	}

	@Validation (description="1) Aparece el botón para añadir a la bolsa", level=Warn)
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
		return (state(Visible, ModalElement.BOLSA_PROOF.getBy(channel)).wait(seconds).check());
	}

	@Validation(
		description="Es visible el apartado <b>#{level}</b>",
		level=Warn)
	public boolean validateCabeceraStep(int level) {
		switch (level) {
		case 1:
			return (state(Visible, ModalElement.STEP1_PROOF.getBy()).wait(4).check());
		case 2:
			return (state(Visible, ModalElement.STEP2_PROOF.getBy()).wait(4).check());
		case 3:
			return (state(Visible, ModalElement.STEP3_PROOF.getBy()).wait(4).check());
		case 4:
			return (state(Visible, ModalElement.STEP4_PROOF.getBy()).wait(4).check());
		default:
			return false;
		}
	}
}
