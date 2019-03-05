package com.mng.robotest.test80.mango.test.stpv.votf;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.votf.SectionBarraSupVOTF;

public class SectionBarraSupVOTFStpV {

	@Validation (
		description="En la barra superior figura un \"" + SectionBarraSupVOTF.titleUserName + "#{usuarioVOTF}" + "\"",
		level=State.Warn)
    public static boolean validate(String usuarioVOTF, WebDriver driver) {
        return (SectionBarraSupVOTF.isPresentUsuario(usuarioVOTF, driver));
    }    
}
