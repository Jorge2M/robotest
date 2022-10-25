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
	
	private static final String TAG_NOMBRE_PAIS = "@TagNombrePais";
	private static final String TAG_CODIGO_PAIS = "@TagCodigoPais";
	private static final String TAG_LITERAL_IDIOMA = "@TagLiteralIdioma";
	
	@Step (
		description="Cambiamos al país <b>" + TAG_NOMBRE_PAIS + "</b> (" + TAG_CODIGO_PAIS + "), idioma <b>" + TAG_LITERAL_IDIOMA + "</b>", 
		expected="Se accede a la shop de " + TAG_NOMBRE_PAIS + " en " + TAG_LITERAL_IDIOMA)
	public void cambioPais(Pais newPais, IdiomaPais newIdioma) {
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_NOMBRE_PAIS, newPais.getNombre_pais());
		step.replaceInExpected(TAG_NOMBRE_PAIS, newPais.getNombre_pais());
		step.replaceInDescription(TAG_CODIGO_PAIS, newPais.getCodigo_pais());
		step.replaceInDescription(TAG_LITERAL_IDIOMA, newIdioma.getCodigo().getLiteral());
		step.replaceInExpected(TAG_LITERAL_IDIOMA, newIdioma.getCodigo().getLiteral());
		
		dataTest.setPais(newPais);
		dataTest.setIdioma(newIdioma);
		new PagePrehome().selecPaisIdiomaYAccede();
		new PageHomeMarcasSteps().validateIsPageWithCorrectLineas();
	}
}
