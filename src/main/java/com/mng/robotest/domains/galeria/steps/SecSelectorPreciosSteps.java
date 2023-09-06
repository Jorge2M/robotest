package com.mng.robotest.domains.galeria.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.pageobjects.SecFiltrosDesktop;
import com.mng.robotest.domains.galeria.pageobjects.SecSelectorPreciosDesktop;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecSelectorPreciosSteps extends StepBase {

	private final SecSelectorPreciosDesktop selectorPreciosDesktop = new SecSelectorPreciosDesktop();
	private final SecFiltrosDesktop secFiltrosDesktop = new SecFiltrosDesktop();

	@Validation (
		description="Es visible el selector de precios",
		level=Warn)
	public boolean validaIsSelector() {
		return (selectorPreciosDesktop.isVisible());
	}

	/**
	 * Selecciona un intervalo de precio mínimo/precio máximo. 
	 * No es posible pasar como parámetro el mínimo/máximo pues lo único que podemos hacer es 'click por la derecha' + 'click por la izquierda'
	 */
	private static final String TAG_MINIMO = "[MINIMO]";
	private static final String TAG_MAXIMO = "[MAXIMO]";
	@Step (
		description="Utilizar el selector de precio: Mínimo=" + TAG_MINIMO + " Máximo=" + TAG_MAXIMO, 
		expected="Aparecen artículos con precio en el intervalo seleccionado")
	public void seleccionaIntervalo() throws Exception {
		var dataFilter = new DataFilterPrecios();
		if (channel==Channel.desktop) {
			secFiltrosDesktop.showFilters();
		}
		dataFilter.minimoOrig = selectorPreciosDesktop.getImporteMinimo();
		dataFilter.maximoOrig = selectorPreciosDesktop.getImporteMaximo();

		selectorPreciosDesktop.clickMinAndMax(30, 30);
		dataFilter.minimoFinal = selectorPreciosDesktop.getImporteMinimo();
		dataFilter.maximoFinal = selectorPreciosDesktop.getImporteMaximo();
		if (channel==Channel.desktop) {
			secFiltrosDesktop.acceptFilters();
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
		
		PageGaleria pageGaleria = PageGaleria.getNew(channel, dataTest.getPais());
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
