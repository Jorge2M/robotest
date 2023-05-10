package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.test.beans.Pais;

public class LTPageAyudaContact extends LegalTextsPage {

	private static final LegalTextGroup TEXTO_COMUN_RGPD = LegalTextGroup.from(
		"AYUDA CONTACT (RGPD)",
		Arrays.asList(
			new LegalText(
				"contactForm.securityMessage.bodycopy",
				"Al contactarnos para solicitar ayuda, confirmas que has leído la Política de privacidad",
				"//p[@class[contains(.,'text-body-s')]]"),
			
			new LegalText(
				"contactForm.rgpd.bodycopy",
				"¿CÓMO TRATAMOS Y PROTEGEMOS TUS DATOS? RESPONSABLE: Punto Fa, S.L. FINALIDAD: Gestión y respuesta de consultas relacionadas con tu",
				"//p[@class[contains(.,'text-body-s')]]")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		return TEXTO_COMUN_RGPD;
	}
	
}
