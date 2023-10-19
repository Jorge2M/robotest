package com.mng.robotest.tests.domains.galeria.pageobjects.filters.device;

import java.util.List;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecFiltros;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class SecMultiFiltrosDevice extends PageBase implements SecFiltros {
	
	public abstract boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros);
	
	public static SecMultiFiltrosDevice make(AppEcom app, Pais pais) {
		if (pais.isGaleriaKondo(app)) {
			return new SecMultiFiltrosDeviceKondo();
		}
		return new SecMultiFiltrosDeviceNormal();
	}
	
}
