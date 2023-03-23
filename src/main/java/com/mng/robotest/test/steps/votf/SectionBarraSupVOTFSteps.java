package com.mng.robotest.test.steps.votf;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.votf.SectionBarraSupVOTF;

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
