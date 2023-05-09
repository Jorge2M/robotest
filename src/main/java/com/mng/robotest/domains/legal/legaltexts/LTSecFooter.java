package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;

import static com.mng.robotest.test.data.PaisShop.*;

public class LTSecFooter extends LegalTextsPage {

	private static final List<LegalText> RGPD = Arrays.asList(
		new LegalText(
			"subscribe.rgpd.legal.text",
			"Al suscribirte aceptas nuestra Política de privacidad para que tratemos los datos",
			"//*[@data-testid='newsletterSubscription.consentgdprDesktop.container']")
	);
	
	private static final List<LegalText> TURQUIA_LT = Arrays.asList(
		//TODO pantalla <> confluence <> lokalise
		new LegalText(
			"subscribe.turkey.legal.text",
			"E-postanızı yazarak kişisel verilerin işlenmesini ve Mango'dan Gizlilik Politikasına uygun olarak iletişim almayı kabul ediyorsunuz",
			"//*[@data-testid='consent-tr']")
	);
	
	private static final List<LegalText> NO_RPGD = Arrays.asList(
		new LegalText(
			"subscribe.noRgpd.legal.text",
			"By subscribing, you agree to our Privacy Policy and Promotion conditions",
			"//p[@data-testid='consent-tr']")
	);	
	
	@Override
	public List<LegalText> getLegalTexts(Pais pais) {
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
