package com.mng.robotest.tests.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.tests.domains.legal.beans.LegalText;
import com.mng.robotest.tests.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.testslegacy.beans.Pais;

public class LTPageMisDatos extends LegalTextsPage {

	private static final LegalTextGroup TEXTO_COMUN_RGPD = LegalTextGroup.from(
		"MIS DATOS (RGPD)",
		Arrays.asList(
			new LegalText(
				"my_account.my_details.legal1",
				"? -> Buscar literal en Serbia (Inglés)",
				"//p[@class[contains(.,'gdpr-profiling')]]"),
			
			new LegalText(
				"audit.new.form.registrate.step1.dataprivacy.1",
				"? -> Buscar literal en Serbia (Inglés)",
				"//*[@loyaltykey='commons.legal.text']",
				"span[translated='tr-true']")
		)
	);
	
	private static final LegalTextGroup PAISES_LOYALTY = LegalTextGroup.from(
		"MIS DATOS (LOYALTY)",
		Arrays.asList(
			new LegalText(
				"my_account.my_details.legal1",
				"Al continuar trataremos tus datos, obtenidos a través de tu navegación en la web, para ofrecerte un contenido afín a tus gustos, basándonos en tus interacciones con la marca. Ten en cuenta que podrás oponerte a este tratamiento",				
				"//p[@class[contains(.,'gdpr-profiling')]]"),
				
			new LegalText(
				"form.registrate.step1.dataprivacy.2.loyalty",
				"INFORMACIÓN BÁSICA SOBRE PROTECCIÓN DE DATOS. RESPONSABLE: Punto Fa, S.L. FINALIDAD: Gestionar el registro en la web. LEGITIMACIÓN: Consentimiento del interesado. DESTINATARIOS: Empresas del Grupo MANGO y empresas encargadas del tratamiento de datos. Se pueden producir transferencias internacionales. DERECHOS: Puede ejercitar en cualquier momento sus derechos de acceso, rectificación, supresión, oposición y demás derechos legalmente establecidos a través del siguiente email: online@mango.com. INFORMACIÓN ADICIONAL: Puede consultarse la información adicional y detallada sobre protección de datos",				
				"//*[@loyaltykey='commons.legal.text']",
				"span[translated='tr-true']")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		if (pais.loyalty()) {
			return PAISES_LOYALTY;
		}
		return TEXTO_COMUN_RGPD;
	}

}
