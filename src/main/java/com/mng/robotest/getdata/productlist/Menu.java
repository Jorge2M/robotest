package com.mng.robotest.getdata.productlist;

import com.mng.robotest.conftestmaker.AppEcom;

public enum Menu implements MenuI {
	Camisas("prendas", "camisas", "14,414", "14", "14", "14"),
	CamisasHE("prendas", "camisas", "120", "120", "120", "120"),
	Toallas("bano", "toallas", "722", "722", "722", "722"),
	Albornoces("bano", "albornoces", "726", "726", "726", "726"),
	Alfombras("bano", "alfombras", "724", "724", "724", "724"),
	Sujetadores("intimissimi", "bra", "821", "821", "821", "821"),
	Braguitas("intimissimi", "knickers", "822", "822", "822", "822"),
	Lenceria("intimissimi", "lingerie", "823", "823", "823", "823"),
	Vaqueros("prendas", "vaqueros", "28,428", "28,428", "28,428", "28,428"),
	Shorts("prendas", "shorts", "25,422", "25", "25,422", "25"),
	Pijamas("prendas", "pijamas", "628", "628", "628", "628"),
	Faldas("prendas", "faldas", "20", "20", "20", "20"),
	Fulares("accesorio", "fulares", "141", "141", "141", "141");
	
	String seccion;
	String galeria;
	String familiaShopPro;	
	String familiaOutletPro;
	String familiaShopTest;
	String familiaOutletTest;
	private Menu(
			String seccion, String galeria, String familiaShopPro, String familiaOutletPro,
			String familiaShopTest, String familiaOutletTest) {
		this.seccion = seccion;
		this.galeria = galeria;
		this.familiaShopPro = familiaShopPro;
		this.familiaOutletPro = familiaOutletPro;
		this.familiaShopTest = familiaShopTest;
		this.familiaOutletTest = familiaOutletTest;
	}
	
	@Override
	public String getSeccion() {
		return seccion;
	}
	
	@Override
	public String getGaleria() {
		return galeria;
	}
	
	@Override
	public String getFamilia(AppEcom app, boolean isPro) {
		if (app==AppEcom.outlet) {
			if (isPro) {
				return familiaOutletPro;
			}
			return familiaOutletTest;
		}
		if (isPro) {
			return familiaShopPro;
		}
		return familiaShopTest;
	}
}
