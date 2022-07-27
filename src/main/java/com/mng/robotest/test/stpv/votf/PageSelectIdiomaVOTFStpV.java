package com.mng.robotest.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.pageobject.votf.PageAlertaVOTF;
import com.mng.robotest.test.pageobject.votf.PageSelectIdiomaVOTF;

public class PageSelectIdiomaVOTFStpV {

	private final PageSelectIdiomaVOTF pageSelectIdiomaVOTF;
	private final PageAlertaVOTF pageAlertaVOTF;
	
	public PageSelectIdiomaVOTFStpV(WebDriver driver) {
		pageSelectIdiomaVOTF = new PageSelectIdiomaVOTF(driver);
		pageAlertaVOTF = new PageAlertaVOTF(driver);
	}
	
	@Step (
		description="Seleccionar el idioma <b>#{idioma.getLiteral()}</b> y pulsar \"Aceptar\" (si aparece una página de alerta la aceptamos)",
		expected="Aparece la página de selección de la línea")
	public void selectIdiomaAndContinue(IdiomaPais idioma) {
		pageSelectIdiomaVOTF.selectIdioma(idioma.getCodigo());
		pageSelectIdiomaVOTF.clickButtonAceptar();
		if (pageAlertaVOTF.isPage()) {
			pageAlertaVOTF.clickButtonContinuar();
		}
	}
}
