package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.utils.testab.TestAB.TypeTestAB;

public enum TestABid {
	Galeria(
		TypeTestAB.GoogleExperiments,
		"6YnA_5gERfC87CROPLRUKw",
		"6YnA_5gERfC87CROPLRUKw",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-nueva", 
			"V1-Antigua")
	), 
	Ficha(
		TypeTestAB.GoogleExperiments,
		"zi8X3zIHR6uaNGgEta8YGA",
		"nKrTsm6AQHe8oivoI8cxVw",
		Arrays.asList(0,1,2),
		Arrays.asList(
			"V0-intermedia", 
			"V1-nueva con 1 foto rande en la primera fila", 
			"V2-ficha nueva con 2 fotos en la 1a fila")
	),
//	MenuMovil(
//		TypeTestAB.GoogleExperiments,
//		"4EaecYi3RAiS0UGayymTiA",
//		"4EaecYi3RAiS0UGayymTiA",
//		Arrays.asList(0,1),
//		Arrays.asList(
//			"V0-menú antiguo sin React y sin filtros de galerías", //Este es el único caso posible en Outlet
//			"V1-menú nuevo en React y con filtros de galerías")
//	),
	CheckoutMovilNpasos(
		TypeTestAB.GoogleExperiments,
		"ON4I2uBlSBSL6iffFMxy3Q",
		"ON4I2uBlSBSL6iffFMxy3Q",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Checkout original en 3 pasos", //Este es el único caso posible en Outlet
			"V1-checkout nuevo en 2 pasos")
	),
	GaleriaDesktopReact(
		TypeTestAB.GoogleExperiments,
		"lTzroOP0TgOP5Q9bUV9rLw",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React")
	),
	//Este TestAB se definió únicamente para certificar que el Robotest soportaba la V1 sin necesidad de aplicar ningún cambio
	//Si se tuviera que activar hay que tener en cuenta que sólo funciona si después de aplicar el TestAB se refresca la portada
//	HeaderMobileFreeShipping (
//		TypeTestAB.GoogleOptimize,
//		"GTM-5T8R33",
//		"Header - Mobile - Free Shipping", 
//	    "UWOU5vObVAZJ7ylnbAK2vQ",
//	    "GTM-KWJ6XJ_OPT-MXVVB%24",
//	    "QUICK_PREVIEW",
//	    Arrays.asList(
//	    	"V0-Original", 
//	    	"V1-Barra Free Shipping arriba")
//	),
	HeaderMobileFavoritos (
		TypeTestAB.GoogleOptimize,
		"GTM-5T8R33",
		"Header Mobile Visibilidad Mi Cuenta Favoritos", 
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-WV3FC$",
	    "QUICK_PREVIEW",
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Iconos sin texto", 
	    	"V2-Iconos con texto")
	);
	
//	BolsaDesktop (
//			TypeTestAB.GoogleOptimize,
//			"GTM-5T8R33",
//			"Icono bolsa Desktop",
//			"UWOU5vObVAZJ7ylnbAK2vQ",
//			"GTM-KWJ6XJ_OPT-P4XWX$",
//			"QUICK_PREVIEW",
//			Arrays.asList(
//				"V0-Original", 
//				"V1-Opción con Hover Effect", 
//				"V2-Opción con Segundo Click")
//	)
//	PagoAustriaEPS (
//		TypeTestAB.GoogleOptimize,
//	    "GTM-5T8R33",
//		"Checkout - EPS Austria - Mostrar método de pago", 
//		"UWOU5vObVAZJ7ylnbAK2vQ",
//		"GTM-KWJ6XJ_OPT-PG7W2$",
//		"QUICK_PREVIEW",
//		Arrays.asList(
//			"V0-No pago EPS", 
//			"V1-Sí pago EPS")
//	);
	
	//Code TestAB GoogleExperiments
	public TypeTestAB typeTestAB;
	public String valueCookieShop;
	public String valueCookieOutlet;
	public List<Integer> variantesInt;
	public List<String> variantes;
	private TestABid(TypeTestAB typeTestAB, String valueCookieShop, String valueCookieOutlet, List<Integer> variantesInt, List<String> variantes) {
		this.typeTestAB = typeTestAB;
		this.valueCookieShop = valueCookieShop;
		this.valueCookieOutlet = valueCookieOutlet;
		this.variantesInt = variantesInt;
		this.variantes = variantes;
	}
	
	public String getValueCookie(AppEcom app) {
		switch (app) {
		case outlet:
			return this.valueCookieOutlet;
		case shop:
		default:
			return this.valueCookieShop;
		}
	}
	
	public List<Integer> getVariantesInt() {
		return variantesInt;
	}
	
	public TypeTestAB getType() {
		return typeTestAB;
	}
	
	//Code TestAB GoogleOptimize
	public String id;
	public String auth;
	String experiment;
	public String preview;
	private TestABid(TypeTestAB typeTestAB, String id, String descripcion, String auth, String experiment, String preview, List<String> variantes) {
		this.typeTestAB = typeTestAB;
		this.id = id;
		this.auth = auth;
		this.experiment = experiment;
		this.preview = preview;
		this.variantes = variantes;
	}
	
	public String getExperimentWithVariant(int variante) {
		return (experiment + variante);
	}
}
