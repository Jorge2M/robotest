package com.mng.robotest.test80.mango.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.test80.mango.test.beans.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageAlertaVOTF;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectIdiomaVOTF;

public class PageSelectIdiomaVOTFStpV {

	@Step (
		description="Seleccionar el idioma <b>#{idioma.getLiteral()}</b> y pulsar \"Aceptar\" (si aparece una página de alerta la aceptamos)",
		expected="Aparece la página de selección de la línea")
	public static void selectIdiomaAndContinue(IdiomaPais idioma, WebDriver driver) {
		PageSelectIdiomaVOTF.selectIdioma(idioma.getCodigo(), driver);
		PageSelectIdiomaVOTF.clickButtonAceptar(driver);
		if (PageAlertaVOTF.isPage(driver)) {
			PageAlertaVOTF.clickButtonContinuar(driver);
		}
	}
}
