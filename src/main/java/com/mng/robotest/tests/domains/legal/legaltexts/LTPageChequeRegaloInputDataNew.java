package com.mng.robotest.tests.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.tests.domains.legal.beans.LegalText;
import com.mng.robotest.tests.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.testslegacy.beans.Pais;

public class LTPageChequeRegaloInputDataNew extends LegalTextsPage {

	private static final LegalTextGroup RGPD = LegalTextGroup.from(
		"CHEQUE REGALO (RGPD)",
		Arrays.asList(
			new LegalText(
				"giftVoucher.useConditionsText",
				"¿CÓMO TRATAMOS Y PROTEGEMOS TUS DATOS? RESPONSABLE: Punto Fa, S.L. FINALIDAD: Gestionar el envío y el uso del cheque regalo. DERECHOS: Puedes ejercer, en cualquier momento, tus derechos de acceso, rectificación, eliminación, oposición y demás derechos legalmente establecidos a través de personaldata@mango.com. INFORMACIÓN ADICIONAL: Consulta más información en nuestra Política de privacidad",
				"//p[@class[contains(.,'gc-legal')]]")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		return RGPD;
	}

}
