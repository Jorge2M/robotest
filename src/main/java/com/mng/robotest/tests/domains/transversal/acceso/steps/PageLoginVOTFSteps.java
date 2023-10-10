package com.mng.robotest.tests.domains.transversal.acceso.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.PageLoginVOTF;
import com.mng.robotest.testslegacy.beans.AccesoVOTF;
import com.mng.robotest.testslegacy.data.PaisShop;

public class PageLoginVOTFSteps extends StepBase {

	private static final String TAG_NOMBRE_PAIS = "@TagNombrePais";
	private static final String TAG_USUARIO_VOTF = "@TagUsuarioVotf";
	private static final String TAG_PASSWORD_VOTF = "@TagPasswordVotf";
	
	private final PageLoginVOTF pageLoginVOTF = new PageLoginVOTF();
	
	@Step (
		description=
			"Acceder a la página de Login e identificarnos con un usuario de " + 
			TAG_NOMBRE_PAIS + " (" + TAG_USUARIO_VOTF + " / " + TAG_PASSWORD_VOTF + ")") 
	public void goToAndLogin(String urlAcceso) throws Exception {
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dataTest.getPais()));
		String usuarioVOTF = accesoVOTF.getUsuario();
		String passwordVOTF = accesoVOTF.getPassword();
		
		replaceStepDescription(TAG_NOMBRE_PAIS, dataTest.getPais().getNombrePais());
		replaceStepDescription(TAG_USUARIO_VOTF, usuarioVOTF);
		replaceStepDescription(TAG_PASSWORD_VOTF, TAG_PASSWORD_VOTF);

		int numIdiomas = dataTest.getPais().getListIdiomas().size();
		if (numIdiomas > 1) {
			setStepExpected("Aparece la página de selección del idioma");
		} else {
			setStepExpected("Aparece la página de selección de la línea");
		}
		
		pageLoginVOTF.goToFromUrlAndSetTestABs();
		pageLoginVOTF.inputUsuario(usuarioVOTF);
		pageLoginVOTF.inputPassword(passwordVOTF);
		pageLoginVOTF.clickButtonContinue();
	}
}