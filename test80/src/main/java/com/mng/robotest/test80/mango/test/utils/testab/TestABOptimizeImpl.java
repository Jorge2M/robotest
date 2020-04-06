package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.testab.TestABOptimize;
import com.github.jorge2m.testmaker.conf.Channel;
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
	
	PLP_Desktop_New_filters_v2(
		"GTM-5T8R33",
		"PLP - Desktop - New filters v2",
		"UWOU5vObVAZJ7ylnbAK2vQ",
		"GTM-KWJ6XJ_OPT-K9JCF$",
		"QUICK_PREVIEW",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Filters"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop, AppEcom.votf)
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
