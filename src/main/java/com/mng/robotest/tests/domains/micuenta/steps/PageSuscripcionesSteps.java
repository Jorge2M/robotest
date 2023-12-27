package com.mng.robotest.tests.domains.micuenta.steps;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageSuscripciones;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageSuscripciones.NewsLetter;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroPersonalizacionShop;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageSuscripcionesSteps extends StepBase {

	private final PageSuscripciones pageSuscripciones = new PageSuscripciones();
	
	@Validation (description="1) Aparece la página de \"Suscripciones\"", level=WARN)
	public boolean validaIsPage () {
		return pageSuscripciones.isPage();
	}

	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(Map<String,String> datosRegOk) {
		int numLineasTotales = Integer.parseInt(datosRegOk.get("numlineas"));
		String lineasUnchecked = datosRegOk.get("clicklineas");
		var tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
		int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();

		var checks = ChecksTM.getNew();
		checks.add(
			"Aparecen "  + numLineasTotales + " Newsletter",
			pageSuscripciones.getNumNewsletters()==numLineasTotales, WARN);
		
		checks.add(
			"Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas",
			pageSuscripciones.getNumNewslettersDesmarcadas()==numLinDesmarcadas, WARN);
		
		while (tokensLinDesmarcadas.hasMoreElements()) {
			String lineaStr = tokensLinDesmarcadas.nextToken();
			checks.add(
				"Aparecen desmarcadas las suscripciones de: " + lineasUnchecked,
				pageSuscripciones.isNewsletterDesmarcada(lineaStr), WARN);
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(List<LineaType> linesMarked) {
		var checks = ChecksTM.getNew();
		
		var linesAll = PageRegistroPersonalizacionShop.getAllLineas();
		int numLineasTotales = linesAll.size();
		checks.add(
				"Aparecen "  + numLineasTotales + " Newsletter",
				pageSuscripciones.getNumNewsletters()==numLineasTotales, WARN);
		
		for (var linea : linesAll) {
			if (linesMarked.contains(linea)) {
				checks.add(
				    "Aparecen marcada la suscripción de <b>" + linea + "</b>",
				    pageSuscripciones.isNewsletterMarcada(linea.name()));
			} else {
				checks.add(
					"Aparecen desmarcada la suscripción de <b>" + linea + "</b>",
					pageSuscripciones.isNewsletterDesmarcada(linea.name()), WARN);				
			}
		}
		return checks;
	}

	@Step(
		description = "Seleccionar los checkbox de las Newsletter <b>#{listNewsletters.toString()}</b> + Botón \"Guardar Cambios\"",
		expected = "parece la confirmación que los datos se han modificado")
	public void selectNewslettersAndGuarda(List<NewsLetter> listNewsletters) {
		for (var idNewsletter : listNewsletters) {
			pageSuscripciones.selectRadioNewsletter(idNewsletter);
		}
		pageSuscripciones.clickGuardarCambios();
		validateIsPageResult(5);
	}

	@Validation (description="Aparece una pantalla de resultado OK " + SECONDS_WAIT)
	private boolean validateIsPageResult (int seconds) {
		return pageSuscripciones.isPageResOKUntil(seconds);
	}
	
}
