package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.test.beans.Pais;

public class LTPageChequeRegaloInputDataNew extends LegalTextsPage {

	private static final LegalTextGroup RGPD = LegalTextGroup.from(
		"CHEQUE REGALO (RGPD)",
		Arrays.asList(
			new LegalText(
				"giftVoucher.useConditionsText",
				"¿CÓMO TRATAMOS Y PROTEGEMOS TUS DATOS? RESPONSABLE: Punto Fa,",
				"//p[@class[contains(.,'gc-legal')]]")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		return RGPD;
	}

}
