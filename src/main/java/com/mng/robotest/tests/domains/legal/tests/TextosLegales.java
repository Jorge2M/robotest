package com.mng.robotest.tests.domains.legal.tests;

import org.testng.annotations.*;

//https://app.lokalise.com/project/184379416194e9c6ba4119.40650357/editor-search/?view=multi&reference_lang_id=&single_lang_id=&mode=endless&offset=0&search=giftVoucher.useConditionsText&search_case_sensitive=0&exact_match=0&filter=&reset_filter=1&screenshots_filter=&reset_screenshots_filter=1&sort_mode=1&document_id=&branch=
//TODO necesito poder realizar un Watch de la página en Confluence

/**
 * Chequeo de todo lo documentado en:
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-FormulariodeayudaGenki
 *
 */
public class TextosLegales {
	
	//TODO (Grecia y España) actualmente el problema con el scroll provoca un Defect dado que no se ve el 2o link de Política de privacidad
	@Test(
		testName="LEG001",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Nuevo Registro")
	public void textosLegalesNuevoRegistro() throws Exception {
		new Leg001().execute();
	}	
	
	//TODO en Russia salen los textos mal
	@Test(
		testName="LEG002",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Antiguo Registro")
	public void textosLegalesAntiguoRegistro() throws Exception {
		new Leg002().execute();
	}
	
	//NOTA: el Guest checkout - Paso 1 no lo puedo implementar porque no aparece en el caso del nuevo flujo bolsa -> checkout	
	//https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-GuestcheckoutPaso1
	
	@Test(
		testName="LEG003",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Guest checkout - Paso 2")
	public void textosLegalesGuestCheckoutPaso2() throws Exception {
		new Leg003().execute();
	}
	
	//NOTA: el Suscripción no lo puedo implementar porque es exclusivo de apps (Comenta Cele que en algún momento se extenderá a web en algunos puntos)
	//https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Suscripcin	

	@Test(
		testName="LEG004",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Suscripción Footer y non modal")
	public void textosLegalesSuscripcionFooterAndNonModal() throws Exception {
		new Leg004().execute();
	}	

	//TODO el 3er literal sale cortado
	@Test(
		testName="LEG005",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Avisame + suscripción")
	public void textosLegalesAvisameSuscripcion() throws Exception {
		new Leg005().execute();
	}	
	
	//TODO en Serbia (texto común RGPD) no aparecen los textos legales -> parece un error -> reportar
	@Test(
		testName="LEG006",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Mis Datos")
	public void textosLegalesMisDatos() throws Exception {
		new Leg006().execute();
	}	
	
	@Test(
		testName="LEG008",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Formulario de ayuda (Genki)")
	public void textosLegalesFormularioAyuda() throws Exception {
		new Leg008().execute();
	}
	
	@Test(
		testName="LEG009",			
		groups={"Legal", "Canal:mobile_App:shop"}, 
		description="Chequeo textos legales: Cheque Regalo (Pagos)")
	public void textosLegalesChequeRegalo() throws Exception {
		new Leg009().execute();
	}
	
}