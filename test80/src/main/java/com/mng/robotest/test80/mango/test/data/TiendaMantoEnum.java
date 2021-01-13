package com.mng.robotest.test80.mango.test.data;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TiendaMantoEnum {
	public enum TiendaManto {
		EuropaShop ("Europa Palau", "001", AppEcom.shop),
		EuropaOutlet ("Europa Montcada", "001", AppEcom.outlet),
		AlemaniaShop ("Alemania", "004", AppEcom.shop),
		USAShop ("USA New Jersey", "400", AppEcom.shop),
		USAOutlet ("Outlet USA", "400", AppEcom.outlet),
		RusiaShop ("Rusia (Major)", "084", AppEcom.shop),
		RusiaOutlet ("Outlet Rusia", "075", AppEcom.outlet),
		TurquiaShop ("Turquía Estambul", "052", AppEcom.shop),
		AlhokairShop("Alhokair", "632", AppEcom.shop),
		//TurquiaOutlet ("Outlet Turquia", "052", AppEcom.outlet),
		ChinaShop ("China Shangai", "720", AppEcom.shop),
		ColombiaShop ("Mercadeo deposit", "480", AppEcom.shop),
		MercadoShop("Mercadeo Deposit", "481", AppEcom.shop),
		MexicoShop("Mexico", "412", AppEcom.shop),
		PhilippinesShop("Trimarket Philippines", "708", AppEcom.shop);
		
		public String litPantManto = "";
		public String codAlmacen = "";
		public AppEcom appE;
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
				return TiendaManto.EuropaShop;
			}
			return TiendaManto.EuropaOutlet;
		}
	}
}
