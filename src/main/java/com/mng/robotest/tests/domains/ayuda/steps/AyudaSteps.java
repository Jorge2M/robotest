package com.mng.robotest.tests.domains.ayuda.steps;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.ayuda.pageobjects.PageAyudaContact;
import com.mng.robotest.tests.domains.ayuda.pageobjects.PageFormularioAyuda;
import com.mng.robotest.tests.domains.ayuda.pageobjects.PagesAyuda;
import com.mng.robotest.tests.domains.base.StepBase;

public class AyudaSteps extends StepBase {
	
	private final PagesAyuda pgAyuda = new PagesAyuda();
	private final PageAyudaContact pgAyudaContact = new PageAyudaContact();
	
	@Step(
		description = "Seleccionamos el icono de <b>#{textIcon}</b>",
		expected = "Aparecen las preguntas asociadas")
	public void clickIcon(String textIcon) {
		pgAyuda.selectIcon(textIcon);
	}
	
	@Validation(
		description="Está presente la pregunta <b>#{questionText}</b>")
	public boolean checkIsQuestionVisible(String questionText) {
		return pgAyuda.isQuestionVisible(questionText);
	}

	@Step(
		description = "Seleccionamos la pregunta <b>#{questionText}</b>",
		expected = "Aparecen la respuesta correcta")
	public void clickQuestion(String questionText) {
		pgAyuda.clickQuestion(questionText);
	}

	@Validation(description="Está presente el texto <b>#{text}</b>")
	public boolean checkIsTextVisible(String text) {
		return pgAyuda.isTextVisible(text);
	}
	
	@Step(
		description = "Seleccionar el botón <b>Contactar</b>",
		expected = "Aparece la página de ayuda de Contáctanos")
	public void clickContactarButton() {
		pgAyuda.clickContactarButton();
		checkPageAyudaContactVisible();
	}
	
	@Validation(description="Aparece la página para contactar") 
	public boolean checkPageAyudaContactVisible() {
		return pgAyudaContact.isPage();
	}
	
	@Step(
		description = "Seleccionar el apartado de <b>Escríbenos un mensaje</b>",
		expected = "Aparece en una nueva página el formulario para el envío de un mensaje")
	public void clickEscribenosUnMensaje() {
		pgAyudaContact.clickEscribenosUnMensaje();
		checkNewPageWithFormularioAyuda();
	}
	
	@Validation(description="Aparece en una nueva página el formulario para el envío de un mensaje")
	private boolean checkNewPageWithFormularioAyuda() {
		String windowFatherHandle = driver.getWindowHandle();
		return (
			goToPageInNewTab(windowFatherHandle) && 
			new PageFormularioAyuda().isPage(5));
	}
	
	public void inputsInFormularioAyuda(String... inputData) {
		inputsInFormularioAyuda(Arrays.asList(inputData));
	}
	
	@Step(
		description = "Introducimos en el formulario las opciones <b>#{inputData}</b>",
		expected = "Aparece el botón Enviar y un texto legal")
	private void inputsInFormularioAyuda(List<String> inputData) {
		new PageFormularioAyuda().inputInSelectors(inputData);
	}
	
}