package com.mng.robotest.domains.ayuda.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;

import com.mng.robotest.domains.ayuda.pageobjects.PagesAyuda;

import org.openqa.selenium.WebDriver;


public class AyudaSteps {
	
	private final PagesAyuda pageAyuda; 
	
	public AyudaSteps(WebDriver driver) {
		this.pageAyuda = new PagesAyuda(driver);
	}

	@Step(
		description = "Seleccionamos el icono de <b>#{textIcon}</b>",
		expected = "Aparecen las preguntas asociadas")
	public void clickIcon(String textIcon) throws Exception {
		pageAyuda.selectIcon(textIcon);
	}
	
	@Validation(
		description="Está presente la pregunta <b>#{questionText}</b>",
		level= State.Defect)
	public boolean checkIsQuestionVisible(String questionText) {
		return pageAyuda.isQuestionVisible(questionText);
	}

	@Step(
		description = "Seleccionamos la pregunta <b>#{questionText}</b>",
		expected = "Aparecen la respuesta correcta")
	public void clickQuestion(String questionText) throws Exception {
		pageAyuda.clickQuestion(questionText);
	}

	@Validation(
		description="Está presente el texto <b>#{text}</b>",
		level= State.Defect)
	public boolean checkIsTextVisible(String text) {
		return pageAyuda.isTextVisible(text);
	}

	
}