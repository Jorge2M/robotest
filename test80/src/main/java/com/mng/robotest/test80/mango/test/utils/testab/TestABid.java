package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.utils.testab.TestAB.TypeTestAB;

public enum TestABid {

	GaleriaDesktopReact(
		TypeTestAB.GoogleExperiments,
		"KgtNo3S3SWCTsPzuWFIT-Q",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop, AppEcom.votf)
	),
	
	//TODO es posible eliminarlo en cuanto se ejecute el planchado
	GaleriaDesktopReactPRESemanal(
		TypeTestAB.GoogleExperiments,
		"LgtNo3S3SWCTsPzuWFIT-Q",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop, AppEcom.votf)
	),
		
	//Optimize
	//La mejor forma para obtener los parámetros es:
	//1) Acceder a Google Optimize con un usuario autorizado de Mango
	//2) Consultar el Test AB en cuestión
	//3) Seleccionar la opción "OBTENER VISTA PREVIA"
	//4) Seleccionar "Compartir vista previa"
	//5) Obtener los datos de la URL que se muestra
	MobileSelectorTallaColor (
		TypeTestAB.GoogleOptimize,
		"GTM-5T8R33",
		"SHOP-176-PLP-Mobile-Selector talla/color",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-PHJV7$",
	    "QUICK_PREVIEW",
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-B - Color en link + CTA añadir con selectores como overlay", 
	    	"V2-C - Color CTA + CTA añadir con selectores en modal",
	    	"V3-D - Color en link + CTA añadir con selectores en modal"),
	    Arrays.asList(Channel.movil_web),
	    Arrays.asList(AppEcom.shop)
	),
	
	MVPCheckoutDesktop (
		TypeTestAB.GoogleOptimize,
		"GTM-5T8R33",
		"MVP Checkout - desktopr",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-TXX7V$",
	    "QUICK_PREVIEW",
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-MVP Checkout Desktop"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop)
	),
	
	SHOP191_BuscadorDesktop (
		TypeTestAB.GoogleOptimize,
		"GTM-5T8R33",
		"SHOP-191 - Buscador - Desktop - últimas búsquedas",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-MSZD5$",
	    "QUICK_PREVIEW",
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Last Searches Visible"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop, AppEcom.votf)
	),
	
	SHOP126_HeaderNuevosIconosDesktop (
		TypeTestAB.GoogleOptimize,
		"GTM-5T8R33",
		"100% - SHOP-126 - Header - Desktop - Nuevos Iconos",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-KVTBH$",
	    "QUICK_PREVIEW",
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Nuevos iconos"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop, AppEcom.votf)
	);
	
	//Code TestAB GoogleExperiments
	public TypeTestAB typeTestAB;
	public String valueCookieShop;
	public String valueCookieOutlet;
	public List<Integer> variantesInt;
	public List<String> variantes;
	public List<Channel> channels;
	public List<AppEcom> apps;
	private TestABid(
			TypeTestAB typeTestAB, String valueCookieShop, String valueCookieOutlet, List<Integer> variantesInt, List<String> variantes, 
			List<Channel> channels, List<AppEcom> apps) {
		this.typeTestAB = typeTestAB;
		this.valueCookieShop = valueCookieShop;
		this.valueCookieOutlet = valueCookieOutlet;
		this.variantesInt = variantesInt;
		this.variantes = variantes;
		this.channels = channels;
		this.apps = apps;
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
	public String group;
	public String auth;
	String experiment;
	public String preview;
	private TestABid(
			TypeTestAB typeTestAB, String group, String descripcion, String auth, String experiment, String preview, List<String> variantes,
			List<Channel> channels, List<AppEcom> apps) {
		this.typeTestAB = typeTestAB;
		this.group = group;
		this.auth = auth;
		this.experiment = experiment;
		this.preview = preview;
		this.variantes = variantes;
		this.channels = channels;
		this.apps = apps;
	}
	
	public String getExperimentWithVariant(int variante) {
		return (experiment + variante);
	}
}
