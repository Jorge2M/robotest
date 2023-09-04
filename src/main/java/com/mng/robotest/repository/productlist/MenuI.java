package com.mng.robotest.repository.productlist;

import com.mng.robotest.conf.AppEcom;

public interface MenuI {

	public String getSeccion();
	public String getGaleria();
	public String getFamilia(AppEcom app, boolean isPro);
	
}
