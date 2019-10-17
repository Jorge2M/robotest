package com.mng.robotest.test80.mango.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageLoginVOTF;

public class PageLoginVOTFStpV {

	final static String tagNombrePais = "@TagNombrePais";
	final static String tagUsuarioVotf = "@TagUsuarioVotf";
	final static String tagPasswordVotf = "@TagPasswordVotf";
	@Step (
		description=
			"Acceder a la página de Login e identificarnos con un usuario de " + 
			tagNombrePais + " (" + tagUsuarioVotf + " / " + tagPasswordVotf + ")") 
    public static void goToAndLogin(String urlAcceso, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        String usuarioVOTF = dCtxSh.pais.getAccesoVOTF().getUsuario();
        String passwordVOTF = dCtxSh.pais.getAccesoVOTF().getPassword();
        StepTestMaker step = TestMaker.getCurrentStep();
        step.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
        step.replaceInDescription(tagUsuarioVotf, usuarioVOTF);
        step.replaceInDescription(tagPasswordVotf, tagPasswordVotf);

        int numIdiomas = dCtxSh.pais.getListIdiomas().size();
        if (numIdiomas > 1) {
            step.setResExpected("Aparece la página de selección del idioma");
        } else {
        	step.setResExpected("Aparece la página de selección de la línea");
        }
        
        PageLoginVOTF.goToFromUrlAndSetTestABs(urlAcceso, dCtxSh, driver);
        PageLoginVOTF.inputUsuario(usuarioVOTF, driver);
        PageLoginVOTF.inputPassword(passwordVOTF, driver);
        PageLoginVOTF.clickButtonContinue(driver);
    }
}
