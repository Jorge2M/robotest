package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;

import static com.mng.robotest.test.data.PaisShop.*;

public class LTPage2IdentCheckout extends LegalTextsPage {

	private static final String XPATH_BLOQUE_POLITICA = "//micro-frontend[@id='legalPolicyGuestCheckoutRegistry']";
	
	private static final List<LegalText> RGPD = Arrays.asList(
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
	);
	
	private static final List<LegalText> CHINA_USA = Arrays.asList(
		new LegalText(
			"form.step2.politica1",
			"Al hacer clic en 'Continuar', aceptas nuestra Política de Privacidad",
			"//span")
	);
	
	private static final List<LegalText> TURQUIA_LT = Arrays.asList(
		new LegalText(
			"express_ckeckout.legal_checkbox.privacy_policy",
			"Acepto la Política de Privacidad", 
			"?")
	);
	
	@Override
	public List<LegalText> getLegalTexts(Pais pais) {
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
