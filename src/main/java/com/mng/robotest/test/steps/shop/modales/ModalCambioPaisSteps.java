package com.mng.robotest.test.steps.shop.modales;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.steps.shop.home.PageHomeMarcasSteps;

public class ModalCambioPaisSteps extends StepBase {
	
	private final ModalCambioPais modalCambioPais = new ModalCambioPais();
	
	@Validation (
		description="Aparece el modal de selección de país (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean validateIsVisible(int seconds) {
		return modalCambioPais.isVisibleModalUntil(seconds);
	}
	
	static final String tagNombrePais = "@TagNombrePais";
	static final String tagCodigoPais = "@TagCodigoPais";
	static final String tagLiteralIdioma = "@TagLiteralIdioma";
	@Step (
		description="Cambiamos al país <b>" + tagNombrePais + "</b> (" + tagCodigoPais + "), idioma <b>" + tagLiteralIdioma + "</b>", 
		expected="Se accede a la shop de " + tagNombrePais + " en " + tagLiteralIdioma)
	public void cambioPais(Pais newPais, IdiomaPais newIdioma) throws Exception {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagNombrePais, newPais.getNombre_pais());
		step.replaceInExpected(tagNombrePais, newPais.getNombre_pais());
		step.replaceInDescription(tagCodigoPais, newPais.getCodigo_pais());
		step.replaceInDescription(tagLiteralIdioma, newIdioma.getCodigo().getLiteral());
		step.replaceInExpected(tagLiteralIdioma, newIdioma.getCodigo().getLiteral());
		
		dataTest.setPais(newPais);
		dataTest.setIdioma(newIdioma);
		new PagePrehome().selecPaisIdiomaYAccede();
		new PageHomeMarcasSteps().validateIsPageWithCorrectLineas();
	}
}
