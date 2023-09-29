package com.mng.robotest.tests.domains.legal.legaltexts;

import java.util.Arrays;

import com.mng.robotest.tests.domains.legal.beans.LegalText;
import com.mng.robotest.tests.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.testslegacy.beans.Pais;

public class LTPageMangoCard extends LegalTextsPage {

	private static final LegalTextGroup RGPD = LegalTextGroup.from(
		"MANGO CARD (RGPD)",
		Arrays.asList(
			new LegalText(
				"mangoCard.getCard.legal1",
				"Rellenando estos datos iniciarás la solicitud de MANGO CARD",
				"//p"),
			
			new LegalText(
				"mangoCard.getCard.legal1",
				"INFORMACIÓN BÁSICA SOBRE PROTECCIÓN DE DATOS PERSONALES",
				"//p"),
			
			new LegalText(
				"mangoCard.getCard.legal1",
				"Responsable: Sabadell Consumer Finance S.A.U. Finalidad: Gestión de la solicitud de MANGO CARD. Legitimación: Consentimiento del interesado. Destinatarios: (i) empresas encargadas de tratamiento de datos que prestan servicios a Sabadell Consumer Finance (ii) Administraciones y organismos públicos cuando así lo exija la normativa. Además, se pueden producir transferencias internacionales. Derechos: Puede ejercitar en cualquier momento sus derechos de acceso, rectificación, supresión, oposición, limitación y portabilidad de los datos dirigiéndose a través del correo electrónico: ejercicioderechosprotecdatos@bancsabadell.com. Información adicional: Puede obtener información adicional de protección de datos por parte de Sabadell Consumer Finance S.A.U en el Anexo publicado en la web de SABADELL CONSUMER (www.sabadellconsumer.com, apartado “Información a clientes” “Anexo información detallada sobre protección de datos de carácter personal”)",
				"//p"),
			
			new LegalText(
				"mangoCard.getCard.legal1",
				"Responsable: Punto Fa, S.L. Finalidad: Gestión de la solicitud de MANGO CARD. Derechos: Puedes ejercer, en cualquier momento, tus derechos de acceso, rectificación, eliminación, oposición y demás derechos legalmente establecidos a través de personaldata@mango.com. Información adicional: Consulta más información en nuestra Política de privacidad",
				"//p")
		)
	);
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		return RGPD;
	}

}
