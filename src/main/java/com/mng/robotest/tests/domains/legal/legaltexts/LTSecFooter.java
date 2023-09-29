package com.mng.robotest.tests.domains.legal.legaltexts;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.util.Arrays;

import com.mng.robotest.tests.domains.legal.beans.LegalText;
import com.mng.robotest.tests.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;

public class LTSecFooter extends LegalTextsPage {

	private static final LegalTextGroup RGPD = LegalTextGroup.from(
		"FOOTER (RGPD)",
		Arrays.asList(
			new LegalText(
				"subscribe.rgpd.legal.text",
				"Al suscribirte aceptas nuestra Política de privacidad para que tratemos los datos obtenidos a través de tu interacción con Mango y te ofrezcamos un contenido personalizado",
				"//*[@data-testid='newsletterSubscription.consentgdprDesktop.container']")
		)
	);
	
	private static final LegalTextGroup TURQUIA_LT = LegalTextGroup.from(
		"FOOTER (TURQUIA)",
		Arrays.asList(
			new LegalText(
				"subscribe.noRgpd.legal.text",
				"Kayıt olarak Gizlilik Politikamızı ve Promosyon şartlarını kabul etmiş oluyorsunuz.",
				"//*[@data-testid='consent-tr']")
		)
	);
	
	private static final LegalTextGroup NO_RPGD = LegalTextGroup.from(
		"FOOTER (NO RGPD)",
		Arrays.asList(
			new LegalText(
				"subscribe.noRgpd.legal.text",
				"By subscribing, you agree to our Privacy Policy and Promotion conditions",
				"//p[@data-testid='consent-tr']")
		)
	);	
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		if (PaisShop.getPais(pais)==TURQUIA) {
			return TURQUIA_LT;
		}
		if (isRgpd(pais)) {
			return RGPD;
		}
		return NO_RPGD;
	}
	
	public boolean isRgpd(Pais pais) {
		return (pais.getRgpd()!=null && pais.getRgpd().compareTo("S")==0);
	}

}
