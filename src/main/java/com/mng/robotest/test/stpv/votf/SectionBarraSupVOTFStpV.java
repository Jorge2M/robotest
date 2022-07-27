package com.mng.robotest.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.votf.SectionBarraSupVOTF;

public class SectionBarraSupVOTFStpV {

	private final SectionBarraSupVOTF sectionBarraSupVOTF;
	
	public SectionBarraSupVOTFStpV(WebDriver driver) {
		sectionBarraSupVOTF = new SectionBarraSupVOTF(driver);
	}
	
	@Validation (
		description="En la barra superior figura un \"" + SectionBarraSupVOTF.TITLE_USERNAME + "#{usuarioVOTF}" + "\"",
		level=State.Warn)
	public boolean validate(String usuarioVOTF) {
		return (sectionBarraSupVOTF.isPresentUsuario(usuarioVOTF));
	}	
}
