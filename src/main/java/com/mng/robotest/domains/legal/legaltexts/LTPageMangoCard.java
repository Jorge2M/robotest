package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.test.beans.Pais;

public class LTPageMangoCard extends LegalTextsPage {

	//TODO en pantalla no está apareciendo este texto
	private static final LegalTextGroup RGPD = LegalTextGroup.from(
		"MANGO CARD (RGPD)",
		Arrays.asList(
			new LegalText(
				"giftVoucher.useConditionsText",
				"¿CÓMO TRATAMOS Y PROTEGEMOS TUS DATOS? RESPONSABLE: Punto Fa, S.L. " + 
				"FINALIDAD: Gestionar el envío y el uso del cheque regalo. DERECHOS: Puedes ejercer, en cualquier momento",
				"//p")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		return RGPD;
	}

}
