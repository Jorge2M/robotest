package com.mng.robotest.domains.micuenta.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.micuenta.pageobjects.PageSuscripciones;
import com.mng.robotest.domains.micuenta.pageobjects.PageSuscripciones.NewsLetter;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Linea.LineaType;

public class PageSuscripcionesSteps extends StepBase {

	private final PageSuscripciones pageSuscripciones = new PageSuscripciones();
	
	@Validation(
		description="1) Aparece la página de \"Suscripciones\"",
		level=State.Warn)
	public boolean validaIsPage () {
		return pageSuscripciones.isPage();
	}

	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(Map<String,String> datosRegOk) {
		int numLineasTotales = Integer.valueOf(datosRegOk.get("numlineas")).intValue();
		String lineasUnchecked = datosRegOk.get("clicklineas");
		StringTokenizer tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
		int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();

		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparecen "  + numLineasTotales + " Newsletter",
			pageSuscripciones.getNumNewsletters()==numLineasTotales, State.Warn);
		
		checks.add(
			"Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas",
			pageSuscripciones.getNumNewslettersDesmarcadas()==numLinDesmarcadas, State.Warn);
		
		while (tokensLinDesmarcadas.hasMoreElements()) {
			String lineaStr = tokensLinDesmarcadas.nextToken();
			checks.add(
				"Aparecen desmarcadas las suscripciones de: " + lineasUnchecked,
				pageSuscripciones.isNewsletterDesmarcada(lineaStr), State.Warn);
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validaIsDataAssociatedToRegister(List<LineaType> linesMarked) {
		ChecksTM checks = ChecksTM.getNew();
		
		List<LineaType> linesAll = PageRegistroPersonalizacionShop.ALL_LINEAS;
		int numLineasTotales = PageRegistroPersonalizacionShop.ALL_LINEAS.size();
		checks.add(
				"Aparecen "  + PageRegistroPersonalizacionShop.ALL_LINEAS.size() + " Newsletter",
				pageSuscripciones.getNumNewsletters()==numLineasTotales, State.Warn);
		
		for (LineaType linea : linesAll) {
			if (linesMarked.contains(linea)) {
				checks.add(
				    "Aparecen marcada la suscripción de <b>" + linea + "</b>",
				    pageSuscripciones.isNewsletterMarcada(linea.name()), State.Defect);
			} else {
				checks.add(
					"Aparecen desmarcada la suscripción de <b>" + linea + "</b>",
					pageSuscripciones.isNewsletterDesmarcada(linea.name()), State.Warn);				
			}
		}
		return checks;
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
	private boolean validateIsPageResult (int seconds) {
		return pageSuscripciones.isPageResOKUntil(seconds);
	}
}
