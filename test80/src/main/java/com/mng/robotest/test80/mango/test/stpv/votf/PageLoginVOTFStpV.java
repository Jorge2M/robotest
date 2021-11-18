package com.mng.robotest.test80.mango.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.beans.AccesoVOTF;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
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
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dCtxSh.pais));
		String usuarioVOTF = accesoVOTF.getUsuario();
		String passwordVOTF = accesoVOTF.getPassword();
		
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
		step.replaceInDescription(tagUsuarioVotf, usuarioVOTF);
		step.replaceInDescription(tagPasswordVotf, tagPasswordVotf);

		int numIdiomas = dCtxSh.pais.getListIdiomas().size();
		if (numIdiomas > 1) {
			step.setResExpected("Aparece la página de selección del idioma");
		} else {
			step.setResExpected("Aparece la página de selección de la línea");
		}
		
		PageLoginVOTF.goToFromUrlAndSetTestABs(/*urlAcceso,*/ dCtxSh, driver);
		PageLoginVOTF.inputUsuario(usuarioVOTF, driver);
		PageLoginVOTF.inputPassword(passwordVOTF, driver);
		PageLoginVOTF.clickButtonContinue(driver);
	}
}
