package com.mng.robotest.domains.legal.tests;

import org.testng.annotations.*;

//https://app.lokalise.com/project/184379416194e9c6ba4119.40650357/editor-search/?view=multi&reference_lang_id=&single_lang_id=&mode=endless&offset=0&search=giftVoucher.useConditionsText&search_case_sensitive=0&exact_match=0&filter=&reset_filter=1&screenshots_filter=&reset_screenshots_filter=1&sort_mode=1&document_id=&branch=
//TODO necesito poder realizar un Watch de la página en Confluence

/**
 * Chequeo de todo lo documentado en:
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-FormulariodeayudaGenki
 *
 */
public class TextosLegales {
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Cheque Regalo (Pagos)")
	public void LEG001_TextosLegalesChequeRegalo() throws Exception {
		new Leg001().execute();
	}
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Formulario de ayuda (Genki)")
	public void LEG002_TextosLegalesFormularioAyuda() throws Exception {
		new Leg002().execute();
	}
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Mango Card")
	public void LEG003_TextosLegalesMangoCasrd() throws Exception {
		new Leg003().execute();
	}	
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Mis Datos")
	public void LEG004_TextosLegalesMisDatos() throws Exception {
		new Leg004().execute();
	}	
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Avisame + suscripción")
	public void LEG005_TextosLegalesAvisameSuscripcion() throws Exception {
		new Leg005().execute();
	}	
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Suscripción Footer y non modal")
	public void LEG006_TextosLegalesSuscripcionFooterAndNonModal() throws Exception {
		new Leg006().execute();
	}	
	
	//NOTA: el Suscripción no lo puedo implementar porque es exclusivo de apps
	//https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Suscripcin
	
	//NOTA: el Guest checkout - Paso 1 no lo puedo implementar porque no aparece en el caso del nuevo flujo bolsa -> checkout	
	//https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-GuestcheckoutPaso1
	
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Guest checkout - Paso 2")
	public void LEG007_TextosLegalesGuestCheckoutPaso2() throws Exception {
		new Leg007().execute();
	}	
	
	//TODO (Grecia y España) actualmente el problema con el scroll provoca un Defect dado que no se ve el 2o link de Política de privacidad
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Nuevo Registro")
	public void LEG008_TextosLegalesNuevoRegistro() throws Exception {
		new Leg008().execute();
	}	
	
	//TODO en Russia salen los textos mal -> Reportar
	@Test(
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Antiguo Registro")
	public void LEG009_TextosLegalesAntiguoRegistro() throws Exception {
		new Leg009().execute();
	}	

}