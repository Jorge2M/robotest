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

//	Legal_Policy_Checkout_Desktop (
//		"PIUR-3434 legal policy checkout 1 Desktop",
//		"w9F_YC3SR2qhGWa97r8ouQ",
//		Arrays.asList(0,1),
//		Arrays.asList(
//			"V0-Original", 
//			"V1-Variante1"),
//		Arrays.asList(Channel.desktop),
//		Arrays.asList(AppEcom.shop)
//	),
//	
//	Legal_Policy_Checkout_Mobile (
//		"PIUR-3434 legal policy checkout 1 Mobile",
//		"IA1ctJL1QXiW22ib9GCdDw",
//		Arrays.asList(0,1),
//		Arrays.asList(
//			"V0-Original", 
//			"V1-Variante1"),
//		Arrays.asList(Channel.mobile),
//		Arrays.asList(AppEcom.shop)
//	),
	
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
	
	CLAV_4345_Optimizacion_Imagenes_PRE(
		"[PDP][PLP][CLAV-4345] Optimización imágenes PRE",
		"1ae9zzpfQ8G27RoiFxWePg",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(Channel.desktop, Channel.mobile),
		Arrays.asList(AppEcom.shop)
	),
	
	CLAV_4345_Optimizacion_Imagenes_Desktop_PRO(
		"[PDP][PLP][CLAV-4345] Optimización imágenes PRO",
		"t-A4thioStqyXlc0mcG5CA",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop)
	),

	CLAV_4345_Optimizacion_Imagenes_Mobile_PRO(
		"[PDP][PLP][CLAV-4345] Optimización imágenes PRE",
		"o-4LOztGSLKXnVHZleRVuA",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(Channel.mobile),
		Arrays.asList(AppEcom.shop)
	),

	Test_New_Menu_Desktop_PRE(
		"Test - New Menú - Desktop PRE",
		"7gBkOrIuSPOvqf80HInMEQ",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Menu"),
		Arrays.asList(Channel.desktop),
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
	
	Test_New_Menu_Desktop_PRO(
			"Test - New Menú - Desktop PRO",
			"_0pE_bPVTCa9mB5wgaENww",
			Arrays.asList(0,1),
			Arrays.asList(
				"V0-Original", 
				"V1-New Menu"),
			Arrays.asList(Channel.desktop),
			Arrays.asList(AppEcom.shop)
		),
	
	PLP_Desktop_New_filters_v2(
		"PLP - Desktop - New filters v2",
		"GTM-KWJ6XJ_OPT-K9JCF$",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Filters"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop, AppEcom.votf)
	),
	
	SHOP_260_Menu_Mobile_Nuevo_Diseno(
		"SHOP-260 - Menu - Mobile - Nuevo Diseño",
		"GTM-KWJ6XJ_OPT-KMZZV$",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1 - Anidado plegado", 
			"Variante 2 - Anidado desplegado"),
		Arrays.asList(Channel.mobile, Channel.tablet),
		Arrays.asList(AppEcom.shop)
	),
	
	PLP_Desktop_Navigation_Filters(
		"PLP - Desktop - Navigation filters",
		"OPT-KWJ6XJ_OPT-N5ZLQ$",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1 - Filtros superiores"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop)
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
