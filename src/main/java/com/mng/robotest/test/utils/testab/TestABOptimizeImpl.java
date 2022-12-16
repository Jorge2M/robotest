package com.mng.robotest.test.utils.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.testab.TestABOptimize;
import com.mng.robotest.conftestmaker.AppEcom;
import com.github.jorge2m.testmaker.conf.Channel;

public enum TestABOptimizeImpl implements TestABOptimize {

	//El id está en el campo "ID d experimento" del detalle en Google Optimize
	//Si se quiere activar hay que añadirlo en la clase TestABactive

	New_Registry_MLY_Desktop_PRO (
		"NEW REGISTRY MLY - Desktop PRO",
		"7RVVwdg2Q0-tYl1ECEidvg",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop)
	),
	
	New_Registry_MLY_Mobile_PRO (
		"NEW REGISTRY MLY - Mobile PRO",
		"U2shAUFFRkKpMfDaesBEZw",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(Channel.mobile),
		Arrays.asList(AppEcom.shop)
	),	

	GPS_NEW_MENU_MOBILE_PRE(
		"GPS - New Menu Mobile (1)",
		"Y9Ad5nDkQtGXnU11J4DtrQ",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Menu"),
		Arrays.asList(Channel.mobile),
		Arrays.asList(AppEcom.shop)
	),

	GPS_NEW_MENU_MOBILE_PRO(
		"GPS - New Menu Mobile (1)",
		"gQCjFHmhQNSesWF4pIgdOA",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Menu"),
		Arrays.asList(Channel.mobile),
		Arrays.asList(AppEcom.shop)
	),	
	
	PDP_Desktop_Size_Selector_PRE(
		"[PDP][DESKTOP] Size Selector",
		"CUSxSJA4RfmbbIkYEmmMUA",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop)
	),

	PDP_Desktop_Size_Selector_Outlet_PRE(
		"[PDP][DESKTOP] Size Selector",
		"plupGFDfRP6MjHjKaCxliQ",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.outlet)
	),	
	
	PDP_Desktop_Size_Selector_Outlet_PRO(
		"[PDP][DESKTOP] Size Selector",
		"ivw-Z5sgQ8GqBtC6TFEJAA",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.outlet)
	),	
	
	PDP_Desktop_Size_Selector_PRO(
		"[PDP][DESKTOP] Size Selector",
		"4pmgwV0pSL6osr9cG8pDPA",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop)
	),
	
	KIRITAKI_LOGIN_DESKTOP_MOBILE_PRE(
		"KIRITAKI- login [shop-outlet][desktop - mobile] PRE",
		"JAyY02hnRCCvoOEHppLbPw",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop, Channel.mobile),
		Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf)
	),

	KIRITAKI_LOGIN_DESKTOP_PRO(
		"KIRITAKI- login [shop-outlet][desktop] PRO",
		"B48P4S8pSiajjRl2U2h_AQ",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf)
	),	
	
	KIRITAKI_LOGIN_MOBILE_PRO(
		"KIRITAKI- login [shop-outlet][mobile] PRO",
		"hZK_tYsgQf6axBuLqFgOOg",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf)
	);		
	

//	 ES_SHOP_XXX_EMP_vs_FH_Search_Desktop (
//		"PLP - Desktop - Navigation filters",
//		"hs4jDFfIRamVMIScEgesvw",
//		Arrays.asList(0,1),
//		Arrays.asList(
//			"Variante 0 - Original",
//			"Variante 1 - Filtros superiores"),
//		Arrays.asList(Channel.desktop),
//		Arrays.asList(AppEcom.shop)
//	);

	private String idExperiment;
	private List<Integer> variantesInt;
	private List<Channel> channels;
	private List<AppEcom> apps;
	private TestABOptimizeImpl(
			String descripcion, String idExperiment, List<Integer> variantesInt,
			List<String> variantes, List<Channel> channels, List<AppEcom> apps) {
		this.idExperiment = idExperiment;
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
	public String getIdExperiment() {
		return idExperiment;
	}
}
