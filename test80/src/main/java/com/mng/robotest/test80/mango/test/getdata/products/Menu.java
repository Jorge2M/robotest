package com.mng.robotest.test80.mango.test.getdata.products;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public enum Menu {
	Camisas("prendas", "camisas", "14,414", "14"),
	Toallas("bano", "toallas", "722", "722"),
	Vaqueros("prendas", "vaqueros", "28,428", "28,428");
	
	String seccion;
	String galeria;
	String familiaShop;
	String familiaOutlet;
	private Menu(String seccion, String galeria, String familiaShop, String familiaOutlet) {
		this.seccion = seccion;
		this.galeria = galeria;
		this.familiaShop = familiaShop;
		this.familiaOutlet = familiaOutlet;
	}
	public String getSeccion() {
		return seccion;
	}
	public String getGaleria() {
		return galeria;
	}
	public String getFamilia(AppEcom app) {
		if (app==AppEcom.outlet) {
			return familiaOutlet;
		}
		return familiaShop;
	}
}
