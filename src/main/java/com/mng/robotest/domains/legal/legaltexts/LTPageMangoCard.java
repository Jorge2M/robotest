package com.mng.robotest.domains.legal.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;

public class LTPageMangoCard extends LegalTextsPage {

	//TODO en pantalla no está apareciendo este texto
	private static final List<LegalText> RGPD = Arrays.asList(
		new LegalText(
			"giftVoucher.useConditionsText",
			"¿CÓMO TRATAMOS Y PROTEGEMOS TUS DATOS? RESPONSABLE: Punto Fa, S.L. " + 
			"FINALIDAD: Gestionar el envío y el uso del cheque regalo. DERECHOS: Puedes ejercer, en cualquier momento",
			"//p")
	);
	
	@Override
	public List<LegalText> getLegalTexts(Pais pais) {
		return RGPD;
	}

}
