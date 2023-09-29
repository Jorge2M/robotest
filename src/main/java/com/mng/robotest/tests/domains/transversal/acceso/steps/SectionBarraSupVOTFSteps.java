package com.mng.robotest.tests.domains.transversal.acceso.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.SectionBarraSupVOTF;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SectionBarraSupVOTFSteps extends StepBase {

	private final SectionBarraSupVOTF sectionBarraSupVOTF = new SectionBarraSupVOTF();
	
	@Validation (
		description="En la barra superior figura un \"" + SectionBarraSupVOTF.TITLE_USERNAME + "#{usuarioVOTF}" + "\"",
		level=Warn)
	public boolean validate(String usuarioVOTF) {
		return sectionBarraSupVOTF.isPresentUsuario(usuarioVOTF);
	}	
}
