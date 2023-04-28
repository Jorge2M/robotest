package com.mng.robotest.domains.ayuda.steps;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.ayuda.pageobjects.PageAyudaContact;
import com.mng.robotest.domains.ayuda.pageobjects.PageFormularioAyuda;
import com.mng.robotest.domains.ayuda.pageobjects.PagesAyuda;
import com.mng.robotest.domains.base.StepBase;

public class AyudaSteps extends StepBase {
	
	private final PagesAyuda pageAyuda = new PagesAyuda();
	private final PageAyudaContact pageAyudaContact = new PageAyudaContact();
	
	@Step(
		description = "Seleccionamos el icono de <b>#{textIcon}</b>",
		expected = "Aparecen las preguntas asociadas")
	public void clickIcon(String textIcon) {
		pageAyuda.selectIcon(textIcon);
	}
	
	@Validation(
		description="Está presente la pregunta <b>#{questionText}</b>")
	public boolean checkIsQuestionVisible(String questionText) {
		return pageAyuda.isQuestionVisible(questionText);
	}

	@Step(
		description = "Seleccionamos la pregunta <b>#{questionText}</b>",
		expected = "Aparecen la respuesta correcta")
	public void clickQuestion(String questionText) {
		pageAyuda.clickQuestion(questionText);
	}

	@Validation(description="Está presente el texto <b>#{text}</b>")
	public boolean checkIsTextVisible(String text) {
		return pageAyuda.isTextVisible(text);
	}
	
	@Step(
		description = "Seleccionar el botón <b>Contactar</b>",
		expected = "Aparece la página de ayuda de Contáctanos")
	public void clickContactarButton() {
		pageAyuda.clickContactarButton();
		checkPageAyudaContactVisible();
	}
	
	@Validation(description="Aparece la página para contactar") 
	public boolean checkPageAyudaContactVisible() {
		return pageAyudaContact.isPage();
	}
	
	@Step(
		description = "Seleccionar el apartado de <b>Escríbenos un mensaje</b>",
		expected = "Aparece en una nueva página el formulario para el envío de un mensaje")
	public void clickEscribenosUnMensaje() {
		pageAyudaContact.clickEscribenosUnMensaje();
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