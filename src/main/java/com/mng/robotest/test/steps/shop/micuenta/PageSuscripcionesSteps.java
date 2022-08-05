package com.mng.robotest.test.steps.shop.micuenta;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.micuenta.PageSuscripciones;
import com.mng.robotest.test.pageobject.shop.micuenta.PageSuscripciones.NewsLetter;


public class PageSuscripcionesSteps {

	private final PageSuscripciones pageSuscripciones;
	
	private PageSuscripcionesSteps(WebDriver driver) { 
		this.pageSuscripciones = new PageSuscripciones(driver);
	}
	
	public static PageSuscripcionesSteps create(WebDriver driver) {
		return new PageSuscripcionesSteps(driver);
	}
	
	@Validation(
		description="1) Aparece la página de \"Suscripciones\"",
		level=State.Warn)
	public boolean validaIsPage () {
		return (pageSuscripciones.isPage());
	}

	@Validation
	public ChecksTM validaIsDataAssociatedToRegister (Map<String,String> datosRegOk) {
		int numLineasTotales = Integer.valueOf(datosRegOk.get("numlineas")).intValue();
		String lineasUnchecked = datosRegOk.get("clicklineas");
		StringTokenizer tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
		int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();

		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparecen "  + numLineasTotales + " Newsletter",
			pageSuscripciones.getNumNewsletters()==numLineasTotales, State.Warn);
		validations.add(
			"Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas",
			pageSuscripciones.getNumNewslettersDesmarcadas()==numLinDesmarcadas, State.Warn);
		while (tokensLinDesmarcadas.hasMoreElements()) {
			String lineaStr = tokensLinDesmarcadas.nextToken();
			validations.add(
				"Aparecen desmarcadas las suscripciones de: " + lineasUnchecked,
				pageSuscripciones.isNewsletterDesmarcada(lineaStr), State.Warn);
		}
		return validations;
	}

	@Step(
		description = "Seleccionar los checkbox de las Newsletter <b>#{listNewsletters.toString()}</b> + Botón \"Guardar Cambios\"",
		expected = "parece la confirmación que los datos se han modificado")
	public void selectNewslettersAndGuarda(ArrayList<NewsLetter> listNewsletters) {
		for (NewsLetter idNewsletter : listNewsletters) {
			pageSuscripciones.selectRadioNewsletter(idNewsletter);
		}

		pageSuscripciones.clickGuardarCambios();
		validateIsPageResult(5);
	}

	@Validation(
		description="1) Aparece una pantalla de resultado OK (la esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	private boolean validateIsPageResult (int maxSecondsToWait) {
		return (pageSuscripciones.isPageResOKUntil(maxSecondsToWait));
	}
}
