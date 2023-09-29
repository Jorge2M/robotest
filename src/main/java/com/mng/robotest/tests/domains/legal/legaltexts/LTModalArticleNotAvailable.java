package com.mng.robotest.tests.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.tests.domains.legal.beans.LegalText;
import com.mng.robotest.tests.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.testslegacy.beans.Pais;

public class LTModalArticleNotAvailable extends LegalTextsPage {

	private static final LegalTextGroup TEXTO_COMUN_RGPD = LegalTextGroup.from(
		"MODAL AVISAME (RGPD)",
		Arrays.asList(
			new LegalText(
				"notifyMe.rgpd.legal.text",
				"Al solicitar un aviso de disponibilidad, confirmas que has leído la Política de privacidad",
				"//p"),
			
			new LegalText(
				"notifyMe.rgpd.legal.modal.title",
				"¿Cómo tratamos y protegemos tus datos?",
				"//p"),
			
			new LegalText(
				"notifyMe.rgpd.legal.modal.bodycopy",
				"Responsable: Punto Fa, S.L. Finalidad: Gestión del servicio de alertas de disponibilidad y/o, en su caso, servicio de envío de comunicaciones personalizadas adaptadas al perfil del interesado. Derechos: Puedes ejercer, en cualquier momento, tus derechos de acceso, rectificación, eliminación, oposición y demás derechos legalmente establecidos a través de personaldata@mango.com. Consulta más información en nuestra Política de privacidad",
				"//p")		
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) { 
		return TEXTO_COMUN_RGPD;
	}

}
