package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.testab.ActivationData;
import com.mng.robotest.test80.arq.utils.testab.manager.TestABmanager;
import com.mng.robotest.test80.arq.utils.testab.manager.TestABmanager.TypeTestAB;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public enum TestABOptimizeImpl implements TestABOptimize {

	//Optimize
	//La mejor forma para obtener los parámetros es:
	//1) Acceder a Google Optimize con un usuario autorizado de Mango
	//2) Consultar el Test AB en cuestión
	//3) Seleccionar la opción "OBTENER VISTA PREVIA"
	//4) Seleccionar "Compartir vista previa"
	//5) Obtener los datos de la URL que se muestra
	MobileSelectorTallaColor (
		"GTM-5T8R33",
		"SHOP-176-PLP-Mobile-Selector talla/color",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-PHJV7$",
	    "QUICK_PREVIEW",
	    Arrays.asList(0,1,2,3),
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-B - Color en link + CTA añadir con selectores como overlay", 
	    	"V2-C - Color CTA + CTA añadir con selectores en modal",
	    	"V3-D - Color en link + CTA añadir con selectores en modal"),
	    Arrays.asList(Channel.movil_web),
	    Arrays.asList(AppEcom.shop)
	),
	
	MVPCheckoutDesktop (
		"GTM-5T8R33",
		"MVP Checkout - desktopr",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-TXX7V$",
	    "QUICK_PREVIEW",
	    Arrays.asList(0,1),
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-MVP Checkout Desktop"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop)
	),
	
	SHOP191_BuscadorDesktop (
		"GTM-5T8R33",
		"SHOP-191 - Buscador - Desktop - últimas búsquedas",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-MSZD5$",
	    "QUICK_PREVIEW",
	    Arrays.asList(0,1),
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Last Searches Visible"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop, AppEcom.votf)
	),
	
	SHOP126_HeaderNuevosIconosDesktop (
		"GTM-5T8R33",
		"100% - SHOP-126 - Header - Desktop - Nuevos Iconos",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-KVTBH$",
	    "QUICK_PREVIEW",
	    Arrays.asList(0,1),
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Nuevos iconos"),
	    Arrays.asList(Channel.desktop),
	    Arrays.asList(AppEcom.shop, AppEcom.votf)
	);
	
	private String group;
	private String auth;
	private String experiment;
	private String preview;
	private List<String> variantes;
	private List<Channel> channels;
	private List<AppEcom> apps;
	private TestABOptimizeImpl(
			String group, String descripcion, String auth, String experiment, String preview, List<Integer> variantesInt, List<String> variantes,
			List<Channel> channels, List<AppEcom> apps) {
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
