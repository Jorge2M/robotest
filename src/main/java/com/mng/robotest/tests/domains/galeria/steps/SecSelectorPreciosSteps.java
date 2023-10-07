package com.mng.robotest.tests.domains.galeria.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecSelectorPreciosSteps extends StepBase {

	private final PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.make(Channel.desktop, app, dataTest.getPais());
	
	private static final String TAG_MINIMO = "[MINIMO]";
	private static final String TAG_MAXIMO = "[MAXIMO]";
	
	@Validation (
		description="Es visible el selector de precios",
		level=Warn)
	public boolean validaIsSelector() {
		return pageGaleriaDesktop.isVisibleSelectorPrecios();
	}

	@Step (
		description="Utilizar el selector de precio: Mínimo=" + TAG_MINIMO + " Máximo=" + TAG_MAXIMO, 
		expected="Aparecen artículos con precio en el intervalo seleccionado")
	public void seleccionaIntervalo() throws Exception {
		var dataFilter = new DataFilterPrecios();
		if (channel==Channel.desktop) {
			pageGaleriaDesktop.showFilters();
		}
		dataFilter.minimoOrig = pageGaleriaDesktop.getMinImportFilter();
		dataFilter.maximoOrig = pageGaleriaDesktop.getMaxImportFilter();

		pageGaleriaDesktop.clickIntervalImportFilter(30, 30);
		dataFilter.minimoFinal = pageGaleriaDesktop.getMinImportFilter();
		dataFilter.maximoFinal = pageGaleriaDesktop.getMaxImportFilter();
		if (channel==Channel.desktop) {
			pageGaleriaDesktop.acceptFilters();
		}
		
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_MINIMO, String.valueOf(dataFilter.minimoFinal));
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_MAXIMO, String.valueOf(dataFilter.maximoFinal));	
		checkResultSelectFiltro(dataFilter);

		checksDefault();
	}
	
	@Validation
	private ChecksTM checkResultSelectFiltro(DataFilterPrecios dataFilter) throws Exception {
		var checks = ChecksTM.getNew();
		checks.add(
			"El nuevo mínimo es mayor que el anterior. Era de <b>" + dataFilter.minimoOrig + "</b> y ahora es <b>" + dataFilter.minimoFinal + "</b>",
			dataFilter.minimoFinal > dataFilter.minimoOrig, Warn);
		
		checks.add(
			"El nuevo máximo es menor que el anterior. Era de <b>" + dataFilter.maximoOrig + "</b> y ahora es <b>" + dataFilter.maximoFinal + "</b>",
			dataFilter.maximoFinal < dataFilter.maximoOrig, Warn);
		
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		checks.add(
			"Todos los precios están en el intervalo [" + dataFilter.minimoFinal + ", " + dataFilter.maximoFinal + "]",
			pageGaleria.preciosInIntervalo(dataFilter.minimoFinal, dataFilter.maximoFinal), Warn);
		
		return checks;
	}
}

class DataFilterPrecios {
	public int minimoOrig = 0;
	public int maximoOrig = 0;
	public int minimoFinal = 0;
	public int maximoFinal = 0;
}
