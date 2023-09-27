package com.mng.robotest.conf.testab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.testab.TestABOptimize;
import com.mng.robotest.conf.AppEcom;
import com.github.jorge2m.testmaker.conf.Channel;

import static com.github.jorge2m.testmaker.conf.Channel.*;
import static com.mng.robotest.conf.AppEcom.*;

public enum TestABOptimizeImpl implements TestABOptimize {

	//El id está en el campo "ID d experimento" del detalle en Google Optimize
	//Si se quiere activar hay que añadirlo en la clase TestABactive

	NUEVO_GUEST_CHECKOUT_PRE (
		"Kiritaki - Nuevo Guest Checkout (PRE)",
		"36wtTrObQJexIXl6vHW22Q",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(shop)
	),	
	NUEVO_GUEST_CHECKOUT_PRO (
		"Kiritaki - Nuevo Guest Checkout (PRO)",
		"27jOfvJxRauv0X5AbnCylA",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(shop)
	),
	AHORRO_EN_BOLSA_MOBILE_PRE (
		"[BOLSA][MOBILE] Ahorro en bolsa - PRE",
		"Fa9wRNgvRuCQibSCb90DiA",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(shop,outlet)
	),			
	PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_SHOP_PRE (
		"[PLP][MOBILE] Selector Listado Mobile 1 columna",
		"LDNtdfrQS5K13P4o7I1SvQ",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(shop)
	),	
	PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_SHOP_PRO (
		"[PLP][MOBILE] Selector Listado Mobile 1 columna",
		"uMS4ByiBS9q8ZJnfPm972w",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(shop)
	),	
	
	PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_OUTLET_PRO (
		"[PLP][MOBILE] Selector Listado Mobile 1 columna",
		"DGvR1803R46RcLwrwagvPg",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(outlet)
	),	
	PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_OUTLET_PRE (
		"[PLP][MOBILE] Selector Listado Mobile 1 columna",
		"HA8t8zCBR66kLo5tc33G6w",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-Variante1"),
		Arrays.asList(mobile),
		Arrays.asList(outlet)
	),	
	
	
	GPS_NEW_MENU_MOBILE_PRE(
		"GPS - New Menu Mobile (1)",
		"Y9Ad5nDkQtGXnU11J4DtrQ",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Menu"),
		Arrays.asList(mobile),
		Arrays.asList(shop)
	),

	GPS_NEW_MENU_MOBILE_PRO(
		"GPS - New Menu Mobile (1)",
		"gQCjFHmhQNSesWF4pIgdOA",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Original", 
			"V1-New Menu"),
		Arrays.asList(mobile),
		Arrays.asList(shop)
	),	
	
	PLP_DESKTOP_ESCONDER_MENU_LATERAL_PRE(
		"[PLP][DESKTOP] Esconder menú lateral",
		"7_ZNHkZORjWkWERWxvAU7w",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1 - Sin menú lateral"),
		Arrays.asList(desktop),
		Arrays.asList(shop)
	),
	
	LOGIN_REGISTRI_PAGE_WITHOUT_TABS_SHOP_PRE(
		"Kiritaki - Login/registry page without tabs",
		"j-GC587dTDmww4MOZsy0hg",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1 - Sin menú lateral"),
		Arrays.asList(desktop, mobile),
		Arrays.asList(shop)
	),

	LOGIN_REGISTRI_PAGE_WITHOUT_TABS_OUTLET_PRE(
		"Kiritaki - Login/registry page without tabs",
		"tlT3dWCeR6ealFvkLjJqhg",
		Arrays.asList(0,1),
		Arrays.asList(
			"Variante 0 - Original",
			"Variante 1 - Sin menú lateral"),
		Arrays.asList(desktop, mobile),
		Arrays.asList(outlet)
	);		
	
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
