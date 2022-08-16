package com.mng.robotest.test.steps.shop.galeria;

import java.util.Arrays;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.SecSelectorPreciosDesktop;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


public class SecSelectorPreciosSteps extends StepBase {

	private final SecSelectorPreciosDesktop selectorPreciosDesktop;
	private final SecFiltrosDesktop secFiltrosDesktop;

	public SecSelectorPreciosSteps() {
		this.selectorPreciosDesktop = new SecSelectorPreciosDesktop();
		this.secFiltrosDesktop = SecFiltrosDesktop.getInstance(channel, app);
	}
	
	@Validation (
		description="Es visible el selector de precios",
		level=State.Warn)
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
		DataFilterPrecios dataFilter = new DataFilterPrecios();
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
			//secFiltrosDesktop.hideFilters();
		}
		
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_MINIMO, String.valueOf(dataFilter.minimoFinal));
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_MAXIMO, String.valueOf(dataFilter.maximoFinal));	
		checkResultSelectFiltro(dataFilter);

		GenericChecks.checkDefault(driver);
	}
	
	@Validation
	private ChecksTM checkResultSelectFiltro(DataFilterPrecios dataFilter) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"El nuevo mínimo es mayor que el anterior. Era de <b>" + dataFilter.minimoOrig + "</b> y ahora es <b>" + dataFilter.minimoFinal + "</b>",
			dataFilter.minimoFinal > dataFilter.minimoOrig, State.Warn);
		
		checks.add(
			"El nuevo máximo es menor que el anterior. Era de <b>" + dataFilter.maximoOrig + "</b> y ahora es <b>" + dataFilter.maximoFinal + "</b>",
			dataFilter.maximoFinal < dataFilter.maximoOrig, State.Warn);
		
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app);
		checks.add(
			"Todos los precios están en el intervalo [" + dataFilter.minimoFinal + ", " + dataFilter.maximoFinal + "]",
			pageGaleria.preciosInIntervalo(dataFilter.minimoFinal, dataFilter.maximoFinal), State.Warn);
		
		return checks;
	}
}

class DataFilterPrecios {
	public int minimoOrig = 0;
	public int maximoOrig = 0;
	public int minimoFinal = 0;
	public int maximoFinal = 0;
	public DataFilterPrecios() {}
}
