package com.mng.robotest.tests.repository.productlist;

import com.mng.robotest.tests.conf.AppEcom;

public enum Menu implements MenuI {
	CAMISAS("prendas", "camisas", "14,414", "14", "14", "14"),
	CAMISAS_HE("prendas", "camisas", "120", "120", "120", "120"),
	TOALLAS("bano", "toallas", "722", "722", "722", "722"),
	ALBORNOCES("bano", "albornoces", "726", "726", "726", "726"),
	ALFOMBRAS("bano", "alfombras", "724", "724", "724", "724"),
	VAQUEROS("prendas", "vaqueros", "28,428", "28,428", "28,428", "28,428"),
	SHORTS("prendas", "shorts", "25,422", "25", "25,422", "25"),
	PIJAMAS("prendas", "pijamas", "628", "628", "628", "628"),
	FALDAS("prendas", "faldas", "20", "20", "20", "20"),
	FULARES("accesorio", "fulares", "141", "141", "141", "141");
	
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
