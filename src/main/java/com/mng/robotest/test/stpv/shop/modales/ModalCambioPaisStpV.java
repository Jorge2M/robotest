package com.mng.robotest.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.stpv.shop.home.PageHomeMarcasStpV;

public class ModalCambioPaisStpV {
	
	@Validation (
		description="Aparece el modal de selección de país (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public static boolean validateIsVisible(int maxSeconds, WebDriver driver) {
		return (ModalCambioPais.isVisibleModalUntil(driver, maxSeconds));
	}
	
	static final String tagNombrePais = "@TagNombrePais";
	static final String tagCodigoPais = "@TagCodigoPais";
	static final String tagLiteralIdioma = "@TagLiteralIdioma";
	@Step (
		description="Cambiamos al país <b>" + tagNombrePais + "</b> (" + tagCodigoPais + "), idioma <b>" + tagLiteralIdioma + "</b>", 
		expected="Se accede a la shop de " + tagNombrePais + " en " + tagLiteralIdioma)
	public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
		step.replaceInExpected(tagNombrePais, dCtxSh.pais.getNombre_pais());
		step.replaceInDescription(tagCodigoPais, dCtxSh.pais.getCodigo_pais());
		step.replaceInDescription(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
		step.replaceInExpected(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
		
		new PagePrehome(dCtxSh, driver).selecPaisIdiomaYAccede();
		(new PageHomeMarcasStpV(dCtxSh.channel, dCtxSh.appE, driver))
			.validateIsPageWithCorrectLineas(dCtxSh.pais);
	}
}
