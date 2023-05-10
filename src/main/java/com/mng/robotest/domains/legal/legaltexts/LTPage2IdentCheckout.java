package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;

import static com.mng.robotest.test.data.PaisShop.*;

public class LTPage2IdentCheckout extends LegalTextsPage {

	private static final String XPATH_BLOQUE_POLITICA = "//micro-frontend[@id='legalPolicyGuestCheckoutRegistry']";
	
	private static final LegalTextGroup RGPD = LegalTextGroup.from(
		"IDENT CHECKOUT (RGPD)",
		Arrays.asList(
			new LegalText(
				"guestCheckout.clientDetails.rgpd.legal.text",
				"Al crear una cuenta, confirmas que has leído la Política de privacidad", 
				XPATH_BLOQUE_POLITICA + "//p"),
			
			new LegalText(
				"guestCheckout.clientDetails.rgpd.legal.modal.title",			
				"¿Cómo tratamos y protegemos tus datos?",
				XPATH_BLOQUE_POLITICA + "//p"),
			
			new LegalText(
				"guestCheckout.clientDetails.rgpd.legal.modal.bodycopy",
				"Responsable: Punto Fa, S.L. y/o, en su caso, la marca asociada con Mango. Finalidad: Tramitación de tu pedido. Derechos: Puedes ejercer, en cualquier momento, tus derechos de acceso, rectificación, eliminación, oposición y demás derechos legalmente establecidos a través de personaldata@mango.com",
				XPATH_BLOQUE_POLITICA + "//p")
		)
	);
	
	private static final LegalTextGroup CHINA_USA = LegalTextGroup.from(
		"IDENT CHECKOUT (CHINA/USA)",
		Arrays.asList(
			new LegalText(
				"form.step2.politica1",
				"Al hacer clic en 'Continuar', aceptas nuestra Política de Privacidad",
				"//span")
		)
	);
	
	private static final LegalTextGroup TURQUIA_LT = LegalTextGroup.from(
		"IDENT CHECKOUT (TURQUÍA)",
		Arrays.asList(
			new LegalText(
				"express_ckeckout.legal_checkbox.privacy_policy",
				"Acepto la Política de Privacidad", 
				"?")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		if (PaisShop.getPais(pais)==CHINA ||
			PaisShop.getPais(pais)==USA) {
			return CHINA_USA;
		}
		
		if (PaisShop.getPais(pais)==TURQUIA) { 
			return TURQUIA_LT;
		}
		return RGPD;
	}

}
