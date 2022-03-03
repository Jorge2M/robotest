package com.mng.robotest.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.votf.SectionBarraSupVOTF;

public class SectionBarraSupVOTFStpV {

	@Validation (
		description="En la barra superior figura un \"" + SectionBarraSupVOTF.titleUserName + "#{usuarioVOTF}" + "\"",
		level=State.Warn)
	public static boolean validate(String usuarioVOTF, WebDriver driver) {
		return (SectionBarraSupVOTF.isPresentUsuario(usuarioVOTF, driver));
	}	
}
