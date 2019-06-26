package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
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
		"KgtNo3S3SWCTsPzuWFIT-Q",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React")
	),
	
	//TODO es posible eliminarlo en cuanto se ejecute el planchado
	GaleriaDesktopReactPRESemanal(
		TypeTestAB.GoogleExperiments,
		"LgtNo3S3SWCTsPzuWFIT-Q",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React")
	),
		
	//Optimize
	//La mejor forma para obtener los parámetros es:
	//1) Acceder a Google Optimize con un usuario autorizado de Mango
	//2) Consultar el Test AB en cuestión
	//3) Seleccionar la opción "OBTENER VISTA PREVIA"
	//4) Seleccionar "Compartir vista previa"
	//5) Obtener los datos de la URL que se muestra
	HeaderDesktopNewIcons (
		TypeTestAB.GoogleOptimize,
		"GTM-5T8R33",
		"SHOP-126-Header-Desktop-Nuevos Iconos", 
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-T9VSJ%24",
	    "QUICK_PREVIEW",
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Iconos con labels",
	    	"V2-Iconos sin labels")
    ),
    
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
