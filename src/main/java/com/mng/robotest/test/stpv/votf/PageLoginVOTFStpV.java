package com.mng.robotest.test.stpv.votf;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.votf.PageLoginVOTF;

public class PageLoginVOTFStpV {

	private static final String TAG_NOMBRE_PAIS = "@TagNombrePais";
	private static final String TAG_USUARIO_VOTF = "@TagUsuarioVotf";
	private static final String TAG_PASSWORD_VOTF = "@TagPasswordVotf";
	
	private final PageLoginVOTF pageLoginVOTF;
	
	public PageLoginVOTFStpV(WebDriver driver) {
		pageLoginVOTF = new PageLoginVOTF(driver);
	}
	
	@Step (
		description=
			"Acceder a la página de Login e identificarnos con un usuario de " + 
			TAG_NOMBRE_PAIS + " (" + TAG_USUARIO_VOTF + " / " + TAG_PASSWORD_VOTF + ")") 
	public void goToAndLogin(String urlAcceso, DataCtxShop dCtxSh) throws Exception {
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dCtxSh.pais));
		String usuarioVOTF = accesoVOTF.getUsuario();
		String passwordVOTF = accesoVOTF.getPassword();
		
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_NOMBRE_PAIS, dCtxSh.pais.getNombre_pais());
		step.replaceInDescription(TAG_USUARIO_VOTF, usuarioVOTF);
		step.replaceInDescription(TAG_PASSWORD_VOTF, TAG_PASSWORD_VOTF);

		int numIdiomas = dCtxSh.pais.getListIdiomas().size();
		if (numIdiomas > 1) {
			step.setResExpected("Aparece la página de selección del idioma");
		} else {
			step.setResExpected("Aparece la página de selección de la línea");
		}
		
		pageLoginVOTF.goToFromUrlAndSetTestABs(dCtxSh);
		pageLoginVOTF.inputUsuario(usuarioVOTF);
		pageLoginVOTF.inputPassword(passwordVOTF);
		pageLoginVOTF.clickButtonContinue();
	}
}
