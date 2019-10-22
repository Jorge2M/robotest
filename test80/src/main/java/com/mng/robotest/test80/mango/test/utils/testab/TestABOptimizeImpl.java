package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.testmaker.service.testab.TestABOptimize;
import com.mng.testmaker.conf.Channel;
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
	
//	MVPCheckoutDesktop (
//		"GTM-5T8R33",
//		"MVP Checkout - desktopr",
//	    "UWOU5vObVAZJ7ylnbAK2vQ",
//	    "GTM-KWJ6XJ_OPT-TXX7V$",
//	    "QUICK_PREVIEW",
//	    Arrays.asList(0,1),
//	    Arrays.asList(
//	    	"V0-Original", 
//	    	"V1-MVP Checkout Desktop"),
//	    Arrays.asList(Channel.desktop),
//	    Arrays.asList(AppEcom.shop)
//	),
	
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
	),
	
	SHOP219_PLPMobileSinFavoritos (
		"GTM-5T8R33",
		"SHOP-219 - PLP - Mobile - Sin favoritos",
	    "UWOU5vObVAZJ7ylnbAK2vQ",
	    "GTM-KWJ6XJ_OPT-KTLLM$",
	    "QUICK_PREVIEW",
	    Arrays.asList(0,1),
	    Arrays.asList(
	    	"V0-Original", 
	    	"V1-Sin favoritos"),
	    Arrays.asList(Channel.movil_web),
	    Arrays.asList(AppEcom.shop)
	);
	
	private String group;
	private String auth;
	private String idExperiment;
	private String preview;
	private List<Integer> variantesInt;
	private List<Channel> channels;
	private List<AppEcom> apps;
	private TestABOptimizeImpl(
			String group, String descripcion, String auth, String idExperiment, String preview, List<Integer> variantesInt, 
			List<String> variantes, List<Channel> channels, List<AppEcom> apps) {
		this.group = group;
		this.auth = auth;
		this.idExperiment = idExperiment;
		this.preview = preview;
		this.channels = channels;
		this.apps = apps;
	}
	
	@Override
	public List<Integer> getVariantes() {
		return this.variantesInt;
	}
	@Override
	public List<Channel> getChannels() {
		return this.channels;
	}
	@Override
	public List<Enum<?>> getApps() {
		List<Enum<?>> listReturn = new ArrayList<>();
		for (Enum<?> app : this.apps) {
			listReturn.add(app);
		}
		return listReturn;
	}
	@Override
	public String getAuth() {
		return this.auth;
	}
	@Override
	public String getIdExperiment() {
		return idExperiment;
	}
	@Override
	public String getGroup() {
		return this.group;
	}
	@Override
	public String getPreview() {
		return this.preview;
	}
}
