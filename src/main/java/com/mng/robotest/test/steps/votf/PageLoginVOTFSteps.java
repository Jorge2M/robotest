package com.mng.robotest.test.steps.votf;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.votf.PageLoginVOTF;

public class PageLoginVOTFSteps extends StepBase {

	private static final String TAG_NOMBRE_PAIS = "@TagNombrePais";
	private static final String TAG_USUARIO_VOTF = "@TagUsuarioVotf";
	private static final String TAG_PASSWORD_VOTF = "@TagPasswordVotf";
	
	private final PageLoginVOTF pageLoginVOTF = new PageLoginVOTF();
	
	@Step (
		description=
			"Acceder a la página de Login e identificarnos con un usuario de " + 
			TAG_NOMBRE_PAIS + " (" + TAG_USUARIO_VOTF + " / " + TAG_PASSWORD_VOTF + ")") 
	public void goToAndLogin(String urlAcceso, Pais pais) throws Exception {
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(pais));
		String usuarioVOTF = accesoVOTF.getUsuario();
		String passwordVOTF = accesoVOTF.getPassword();
		
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_NOMBRE_PAIS, pais.getNombre_pais());
		step.replaceInDescription(TAG_USUARIO_VOTF, usuarioVOTF);
		step.replaceInDescription(TAG_PASSWORD_VOTF, TAG_PASSWORD_VOTF);

		int numIdiomas = pais.getListIdiomas().size();
		if (numIdiomas > 1) {
			step.setResExpected("Aparece la página de selección del idioma");
		} else {
			step.setResExpected("Aparece la página de selección de la línea");
		}
		
		pageLoginVOTF.goToFromUrlAndSetTestABs();
		pageLoginVOTF.inputUsuario(usuarioVOTF);
		pageLoginVOTF.inputPassword(passwordVOTF);
		pageLoginVOTF.clickButtonContinue();
	}
}