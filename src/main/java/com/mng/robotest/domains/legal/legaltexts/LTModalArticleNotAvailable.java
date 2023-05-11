package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;

import static com.mng.robotest.test.data.PaisShop.*;

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
				"Responsable: Punto Fa, S.L. Finalidad: Gestión del servicio de alertas de disponibilidad y/o, en su caso, servicio de envío de comunicaciones personalizadas. Derechos: Puedes ejercer, en cualquier momento, tus derechos de acceso, rectificación, eliminación, oposición y demás derechos legalmente establecidos a través de personaldata@mango.com. Información adicional: Consulta más información en nuestra Política de privacidad",
				"//p")		
		)
	);
	
	private static final LegalTextGroup ARABIA = LegalTextGroup.from(
		"MODAL AVISAME (ARABIA)",
		Arrays.asList(
			new LegalText(
				"notifyMe.arabia.legal.text",
				"By subscribing, you confirm that you have read the Privacy policy and agree the cross-border transfer of your personal information to Spain and Ireland",
				"//p")			
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) { 
		if (PaisShop.getPais(pais)==SAUDI_ARABIA) {
			return ARABIA;
		}
		return TEXTO_COMUN_RGPD;
	}

}
