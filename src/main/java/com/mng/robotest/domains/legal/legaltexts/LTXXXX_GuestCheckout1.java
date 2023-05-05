package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;

public class LTXXXX_GuestCheckout1 extends LegalTextsPage {

	private static final List<LegalText> TEXTO_COMUN_RGPD = Arrays.asList(
		new LegalText(
			"guestCheckout.access.rgpd.legal.text",
			"Al continuar el pedido, confirmas que has leído la Política de privacidad",
			"?"),

		new LegalText(
			"guestCheckout.access.rgpd.legal.modal.title",
			"¿Cómo tratamos y protegemos tus datos?",
			"?"),
		
		new LegalText(
			"guestCheckout.access.rgpd.legal.modal.bodycopy",
			"Responsable: Punto Fa, S.L. Finalidad: Tramitación de tu pedido y, en su caso, envío de comunicaciones",
			"?")
	);
	
	
	///----
	
	private static final List<LegalText> TURQUIA = Arrays.asList(
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
		if (PaisShop.getPais(pais)==PaisShop.TURQUIA) {
			return TURQUIA;
		}
		if (isRgpd(pais)) {
			return TEXTO_COMUN_RGPD;
		}
		return NO_RPGD;
	}
	
	public boolean isRgpd(Pais pais) {
		return (pais.getRgpd()!=null && pais.getRgpd().compareTo("S")==0);
	}



}
