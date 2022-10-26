package com.mng.robotest.getdata.productlist;

import com.mng.robotest.conftestmaker.AppEcom;

public class MenuApi implements MenuI {

	private final String seccion;
	private final String galeria;
	private final String familia;
	
	private MenuApi(String seccion, String galeria, String familia) {
		this.seccion = seccion;
		this.galeria = galeria;
		this.familia = familia;
	}
	
	public static MenuApi from(String seccion, String galeria, String familia) {
		return new MenuApi(seccion, galeria, familia);
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
		return familia;
	}

	@Override
	public String toString() {
		return "MenuApi [seccion=" + seccion + ", galeria=" + galeria + ", familia=" + familia + "]";
	}
	
}
