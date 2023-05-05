package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;

public class LTPageChequeRegaloInputDataNew extends LegalTextsPage {

	private static final List<LegalText> RGPD = Arrays.asList(
		new LegalText(
			"giftVoucher.useConditionsText",
			"¿CÓMO TRATAMOS Y PROTEGEMOS TUS DATOS? RESPONSABLE: Punto Fa,",
			"//p[@class[contains(.,'gc-legal')]]")
	);
	
	@Override
	public List<LegalText> getLegalTexts(Pais pais) {
		return RGPD;
	}

}
