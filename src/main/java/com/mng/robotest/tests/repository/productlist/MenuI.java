package com.mng.robotest.tests.repository.productlist;

import com.mng.robotest.tests.conf.AppEcom;

public interface MenuI {

	public String getSeccion();
	public String getGaleria();
	public String getFamilia(AppEcom app, boolean isPro);
	
}
