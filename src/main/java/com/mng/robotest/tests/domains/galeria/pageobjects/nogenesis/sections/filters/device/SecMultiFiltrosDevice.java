package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.device;

import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.SecFiltros;

public abstract class SecMultiFiltrosDevice extends PageBase implements SecFiltros {
	
	public abstract boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros);
	
	public static SecMultiFiltrosDevice make() {
		return new SecMultiFiltrosDeviceNormal();
	}
	
}
