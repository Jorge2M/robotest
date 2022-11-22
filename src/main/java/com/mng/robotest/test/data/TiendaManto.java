package com.mng.robotest.test.data;

import com.mng.robotest.conftestmaker.AppEcom;

public enum TiendaManto {
	EUROPA_SHOP ("Europa Palau", "001", AppEcom.shop),
	EUROPA_OUTLET ("Europa Montcada", "001", AppEcom.outlet),
	ALEMANIA_SHOP ("Alemania", "004", AppEcom.shop),
	USA_SHOP ("USA New Jersey", "400", AppEcom.shop),
	USA_OUTLET ("Outlet USA", "400", AppEcom.outlet),
	RUSIA_SHOP ("Rusia (Major)", "084", AppEcom.shop),
	RUSIA_OUTLET ("Outlet Rusia", "075", AppEcom.outlet),
	TURQUIA_SHOP ("Turquía Estambul", "052", AppEcom.shop),
	ALHOKAIR_SHOP("Alhokair", "632", AppEcom.shop),
	//TurquiaOutlet ("Outlet Turquia", "052", AppEcom.outlet),
	CHINA_SHOP ("China Shangai", "720", AppEcom.shop),
	COLOMBIA_SHOP ("Mercadeo deposit", "480", AppEcom.shop),
	MERCADO_SHOP("Mercadeo Deposit", "481", AppEcom.shop),
	MEXICO_SHOP("Mexico", "412", AppEcom.shop),
	PHILIPPINES_SHOP("Trimarket Philippines", "708", AppEcom.shop);
	
	private String litPantManto = "";
	private String codAlmacen = "";
	private AppEcom appE;
	private TiendaManto(String litPantManto, String codAlmacen, AppEcom appE) {
		this.litPantManto = litPantManto;
		this.codAlmacen = codAlmacen;
		this.appE = appE;
	}
	
	public static TiendaManto getTienda(String codAlmacen, String codigoPais, AppEcom appE) {
		String codigo = codAlmacen;
		if ("".compareTo(codigo)==0) {
			codigo = codigoPais;
		}
		for (TiendaManto tiendaManto : TiendaManto.values()) {
			if (tiendaManto.codAlmacen.compareTo(codigo)==0 && tiendaManto.appE==appE) {
				return tiendaManto;
			}
		}
		
		if (appE==AppEcom.shop) {
			return TiendaManto.EUROPA_SHOP;
		}
		return TiendaManto.EUROPA_OUTLET;
	}
	
	public String getLitPantManto() {
		return litPantManto;
	}
}
