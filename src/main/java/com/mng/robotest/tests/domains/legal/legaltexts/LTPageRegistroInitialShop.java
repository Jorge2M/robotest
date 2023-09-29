package com.mng.robotest.tests.domains.legal.legaltexts;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.util.Arrays;

import com.mng.robotest.tests.domains.legal.beans.LegalText;
import com.mng.robotest.tests.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.testslegacy.beans.Pais;

public class LTPageRegistroInitialShop extends LegalTextsPage {

	private static final LegalTextGroup RGPD_NEWREGISTER = LegalTextGroup.from(
		"NEW REGISTER (RGPD)",
		Arrays.asList(
			new LegalText(
				"createAccount.rgpd.noLoyalty.legal.text",
				"By creating an account, you confirm that you have read the Privacy Policy",
				"//micro-frontend[@id='registry']//p"),
			
			new LegalText(
				"createAccount.rgpd.legal.title",
				"How do we treat and protect your data?",
				"//micro-frontend[@id='registry']//p"),
	
			new LegalText(
				"createAccount.rgpd.noLoyalty.legal.bodycopy",
				"Data Controller: Punto Fa, S.L.Purpose: Management of Mango registration and presentation of content adapted to your profile on the platform. Likewise, and if you give us your consent, the sending of personalised communications. Rights: You may, at any time, exercise your rights of access, rectification, erasure, objection and other legally-established rights at personaldata@mango.com. Additional information: For more information, consult our Privacy Policy",
				"//micro-frontend[@id='registry']//p[@class='text-body-s']")
		)
	);
	
	private static final LegalTextGroup LOYALTY_NEWREGISTER = LegalTextGroup.from(
		"NEW REGISTER (LOYALTY)",		
		Arrays.asList(
			new LegalText(
				"createAccount.rgpd.loyalty.legal.text",
				"Al crear una cuenta y suscribirte, confirmas que has leído la Política de privacidad",
				"//micro-frontend[@id='registry']//p"),
			
			new LegalText(
				"createAccount.rgpd.legal.title",
				"¿Cómo tratamos y protegemos tus datos?",
				"//micro-frontend[@id='registry']//p"),
	
			new LegalText(
				"createAccount.rgpd.loyalty.legal.bodycopy",
				"Responsable: Punto Fa, S.L. Finalidad: Gestión del registro en Mango, el programa Mango likes you, y presentación de contenido en la plataforma adaptado a tu perfil. Asimismo y si nos das tu consentimiento, el envío de comunicaciones personalizadas. Derechos: Puedes ejercer, en cualquier momento, tus derechos de acceso, rectificación, eliminación, oposición y demás derechos legalmente establecidos a través de personaldata@mango.com. Información adicional: Consulta más información en nuestra Política de privacidad",
				"//micro-frontend[@id='registry']//p")		
		)
	);	
	
	private static final LegalTextGroup COREA_NEWREGISTER = LegalTextGroup.from(
		"NEW REGISTER (COREA)",
		Arrays.asList(
			new LegalText(
				"subscribe.legal.korea.checkbox",
				"I would like to receive the Newsletter and I consent to the collection and use of optional personal information",
				"//micro-frontend[@id='registry']//*[@for='newsletter']"),
			
			new LegalText(
				"privacyPolicy.korea.title",
				"COLLECTION AND USE OF YOUR PERSONAL INFORMATION",
				"//micro-frontend[@id='registry']//*[@id='newsletter_description']/p"),
	
			new LegalText(
				"subscribe.korea.legal.bodycopy",
				"Purpose: Receive Mango Newsletter communications. Personal information collected: e-mail and gender. Retention period: Until consent withdrawn or until the end of the processing purpose and the retention period to comply with the applicable legislation",
				"//micro-frontend[@id='registry']//*[@id='newsletter_description']/p[2]"),		
			
			new LegalText(
				"createAccount.korea.legal.checkbox",
				"I have read and consent to the collection and use of my personal information",
				"//micro-frontend[@id='registry']//*[@for='createAccountLegal']"),
			
			new LegalText(
				"privacyPolicy.korea.title",
				"COLLECTION AND USE OF YOUR PERSONAL INFORMATION",
				"//micro-frontend[@id='registry']//*[@id='createAccountLegal_description']/p"),
			
			new LegalText(
				"createAccount.korea.legal.bodycopy",
				"Purpose: Create Mango account and management of the contract. Personal information collected: Name, e-mail address, postal address, phone number, Man or Woman line interest for personalised experience and date of birth. Retention period: Until consent withdrawn or until the end of the processing purpose and the retention period to comply with the applicable legislation",
				"//micro-frontend[@id='registry']//*[@id='createAccountLegal_description']/p[2]")		
		)
	);
	
	private static final LegalTextGroup RGPD_OLDREGISTER = LegalTextGroup.from(
		"OLD REGISTER (RGPD)",
		Arrays.asList(
			new LegalText(
				"form.registrate.step1.profileconsent",
				"By clicking on ‘Register’, we will process your personal data obtained from your browsing on the website, in order to offer you content according to your preferences, based on your interaction with the brand. Bear in mind that you can oppose said data processing. ",
				"//div[@class='gdpr']/p"),
			
			new LegalText(
				"form.registrate.step1.dataprivacy.1",
				"BASIC INFORMATION REGARDING DATA PROTECTION. DATA CONTROLLER: Punto Fa, SL. PURPOSE: Manage the registration on the website. AUTHORISATION: Consent of the interested party. ADDRESSEES: MANGO Group companies and companies responsible for data processing. International transfers may occur. RIGHTS: You may, at any time, exercise your rights of access, rectification, cancellation, opposition and other legally-established rights via the following e-mail address: personaldata@mango.com. ADDITIONAL INFORMATION: You may consult more detailed information regarding data protection ",
				"//div[@class='gdpr']/p")
		)
	);	
	
	private static final LegalTextGroup LOYALTY_OLDREGISTER = LegalTextGroup.from(
		"OLD REGISTER (LOYALTY)",
		Arrays.asList(
			new LegalText(
				"form.registrate.step1.profileconsent",
				"Al continuar trataremos tus datos, obtenidos a través de tu navegación en la web, para ofrecerte un contenido afín a tus gustos, basándonos en tus interacciones con la marca. Ten en cuenta que podrás oponerte a este tratamiento.",
				"//loyalty-literal[@loyaltykey[contains(.,'profileconsent')]]",
				"span[translated='tr-true']"),
			
			new LegalText(
				"form.registrate.step1.dataprivacy.2.loyalty",
				"INFORMACIÓN BÁSICA SOBRE PROTECCIÓN DE DATOS. RESPONSABLE: Punto Fa, S.L. FINALIDAD: Gestionar el registro en la web, así como la suscripción en el programa MANGO likes you y, en su caso, gestión del servicio de envío de comunicaciones personalizadas adaptadas al perfil del interesado. LEGITIMACIÓN: Consentimiento del interesado. DESTINATARIOS:  Empresas del grupo Mango y terceros que sean necesarios para la prestación de los servicios. Se pueden producir transferencias internacionales. DERECHOS: Puede ejercitar en cualquier momento sus derechos de acceso, rectificación, supresión, oposición y demás derechos legalmente establecidos a través del siguiente e-mail: personaldata@mango.com. INFORMACIÓN ADICIONAL: Puede consultarse la información adicional y detallada sobre protección de datos ",
				"//loyalty-literal[@loyaltykey[contains(.,'profileconsent')]]",
				"span[translated='tr-true']")
		)
	);	
	
	private static final LegalTextGroup ARABIA_OLDREGISTER = LegalTextGroup.from(
		"OLD REGISTER (ARABIA)",
		Arrays.asList(
			new LegalText(
				"createAccount.legal.arabia.start.text",
				"By creating an account, you confirm that you have read our",
				"//p[@class[contains(.,'gdpr')]]/span"),
			
			new LegalText(
				"createAccount.legal.arabia.link",
				"Privacy Policy",
				"//p[@class[contains(.,'gdpr')]]//span"),
			
			new LegalText(
				"createAccount.legal.arabia.end.text",
				"and agree the cross-border transfer of your personal information to Spain and Ireland",
				"//p[@class[contains(.,'gdpr')]]//span")		
		)
	);	
	
	private static final LegalTextGroup RUSSIA_OLDREGISTER = LegalTextGroup.from(
		"OLD REGISTER (RUSSIA)",
		Arrays.asList(
			new LegalText(
				"register.legal_check.privacy_policy",
				"Acepto la Política de Privacidad y el contrato de miembro de Mango",
				"//p")
		)
	);	
	
	private static final LegalTextGroup TURQUIA_OLDREGISTER = LegalTextGroup.from(
		"OLD REGISTER (TURQUÍA)",
		Arrays.asList(
			new LegalText(
				"register.legal_check.privacy_policy",
				"Kvkk aydinlatma metni ve Mango üye sözleşmesini kabul ediyorum",
				"//*[@id[contains(.,'privacy_policy')]]")
		)
	);	
	
	private static final LegalTextGroup NORGPD_OLDREGISTER = LegalTextGroup.from(
		"OLD REGISTER (NO RGPD)",
		Arrays.asList(
			new LegalText(
				"form.registrate.step1.privacidad1",
				"By clicking on 'Create account', you accept our",
				"//div[@class='labelContent']/span"),
			
			new LegalText(
				"form.registrate.step1.privacidad2",
				"Confidentiality Policy",
				"//div[@class='labelContent']//a")
		)
	);	
	
	@Override
	public LegalTextGroup getLegalTexts(Pais pais) {
		if (pais==COREA_DEL_SUR.getPais()) {
			return COREA_NEWREGISTER;
		}
		if (pais==SAUDI_ARABIA.getPais()) {
			return ARABIA_OLDREGISTER;
		}
		if (pais==RUSSIA.getPais()) {
			return RUSSIA_OLDREGISTER;
		}
		if (pais==TURQUIA.getPais()) {
			return TURQUIA_OLDREGISTER;
		}		
		if (pais.isNewRegister()) {
			if (pais.loyalty()) {
				return LOYALTY_NEWREGISTER;
			}
			return RGPD_NEWREGISTER;
		} else {
			if (pais.loyalty()) {
				return LOYALTY_OLDREGISTER;
			}
			if (isRgpd(pais)) {
				return RGPD_OLDREGISTER;
			}
			return NORGPD_OLDREGISTER;
		}
	}
	
	public boolean isRgpd(Pais pais) {
		return (pais.getRgpd()!=null && pais.getRgpd().compareTo("S")==0);
	}
}
