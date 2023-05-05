package com.mng.robotest.domains.legal.legaltexts;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;

public class LTPageMisDatos extends LegalTextsPage {

	private static final List<LegalText> TEXTO_COMUN_RGPD = Arrays.asList(
		new LegalText(
			"my_account.my_details.legal1",
			"Al continuar trataremos tus datos, obtenidos a través",
			"//p[@class[contains(.,'gdpr-profiling')]]"),
		
		new LegalText(
			"audit.new.form.registrate.step1.dataprivacy.1",
			"INFORMACIÓN BÁSICA SOBRE PROTECCIÓN DE DATOS. RESPONSABLE: Punto Fa,",
			"//*[@loyaltykey='commons.legal.text']",
			"span[translated='tr-true']")
	);
	
	private static final List<LegalText> PAISES_LOYALTY = Arrays.asList(
		new LegalText(
			"my_account.my_details.legal1",
			"Al continuar trataremos tus datos, obtenidos a través",
			"//p[@class[contains(.,'gdpr-profiling')]]"),
			
		new LegalText(
			"form.registrate.step1.dataprivacy.2.loyalty",
			"INFORMACIÓN BÁSICA SOBRE PROTECCIÓN DE DATOS. RESPONSABLE: Punto Fa,",
			"//*[@loyaltykey='commons.legal.text']",
			"span[translated='tr-true']")
	);
	
	@Override
	public List<LegalText> getLegalTexts(Pais pais) {
		if (pais.isLoyalty()) {
			return PAISES_LOYALTY;
		}
		return TEXTO_COMUN_RGPD;
	}

}
