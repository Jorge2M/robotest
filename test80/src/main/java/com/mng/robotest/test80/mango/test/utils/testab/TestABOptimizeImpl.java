package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.testab.TestABOptimize;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public enum TestABOptimizeImpl implements TestABOptimize {

	//El id está en el campo "ID d experimento" del detalle en Google Optimize
	//Si se quiere activar hay que añadirlo en la clase TestABactive
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
	
	SHOP_260_Menu_Mobile_Nuevo_Diseño(
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
