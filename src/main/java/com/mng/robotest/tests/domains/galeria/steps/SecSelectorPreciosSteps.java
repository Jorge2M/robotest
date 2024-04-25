package com.mng.robotest.tests.domains.galeria.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleriaDesktop;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecSelectorPreciosSteps extends StepBase {

	private final PageGaleriaDesktop pgGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.make(Channel.desktop, app, dataTest.getPais());
	
	private static final String TAG_MINIMO = "[MINIMO]";
	private static final String TAG_MAXIMO = "[MAXIMO]";
	
	@Validation (
		description="Es visible el selector de precios",
		level=WARN)
	public boolean checkIsSelector() {
		return pgGaleriaDesktop.isVisibleSelectorPrecios();
	}

	@Step (
		description="Utilizar el selector de precio: Mínimo=" + TAG_MINIMO + " Máximo=" + TAG_MAXIMO, 
		expected="Aparecen artículos con precio en el intervalo seleccionado")
	public void selectInterval() throws Exception {
		var dataFilter = new DataFilterPrecios();
		if (isDesktop()) {
			pgGaleriaDesktop.showFilters();
		}
		dataFilter.minimoOrig = pgGaleriaDesktop.getMinImportFilter();
		dataFilter.maximoOrig = pgGaleriaDesktop.getMaxImportFilter();

		pgGaleriaDesktop.clickIntervalImportFilter(30, 30);
		dataFilter.minimoFinal = pgGaleriaDesktop.getMinImportFilter();
		dataFilter.maximoFinal = pgGaleriaDesktop.getMaxImportFilter();
		if (isDesktop()) {
			pgGaleriaDesktop.acceptFilters();
		}
		
		replaceStepDescription(TAG_MINIMO, String.valueOf(dataFilter.minimoFinal));
		replaceStepDescription(TAG_MAXIMO, String.valueOf(dataFilter.maximoFinal));
		
		checkResultSelectFiltro(dataFilter);
		checksDefault();
	}
	
	@Validation
	private ChecksTM checkResultSelectFiltro(DataFilterPrecios dataFilter) throws Exception {
		var checks = ChecksTM.getNew();
		checks.add(
			"El nuevo mínimo es mayor que el anterior. Era de <b>" + dataFilter.minimoOrig + "</b> y ahora es <b>" + dataFilter.minimoFinal + "</b>",
			dataFilter.minimoFinal > dataFilter.minimoOrig, WARN);
		
		checks.add(
			"El nuevo máximo es menor que el anterior. Era de <b>" + dataFilter.maximoOrig + "</b> y ahora es <b>" + dataFilter.maximoFinal + "</b>",
			dataFilter.maximoFinal < dataFilter.maximoOrig, WARN);
		
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		checks.add(
			"Todos los precios están en el intervalo [" + dataFilter.minimoFinal + ", " + dataFilter.maximoFinal + "]",
			pageGaleria.preciosInIntervalo(dataFilter.minimoFinal, dataFilter.maximoFinal), WARN);
		
		return checks;
	}
}

class DataFilterPrecios {
	public int minimoOrig = 0;
	public int maximoOrig = 0;
	public int minimoFinal = 0;
	public int maximoFinal = 0;
}
