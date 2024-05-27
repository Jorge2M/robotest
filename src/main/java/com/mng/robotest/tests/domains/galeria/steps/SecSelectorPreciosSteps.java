package com.mng.robotest.tests.domains.galeria.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecSelectorPreciosSteps extends StepBase {

	private final PageGaleria pgGaleria = PageGaleria.make(Channel.desktop, app, dataTest.getPais());
	
	private static final String TAG_MINIMO = "[MINIMO]";
	private static final String TAG_MAXIMO = "[MAXIMO]";
	
	@Validation (description="Es visible el selector de precios", level=WARN)
	public boolean checkIsSelector() {
		return pgGaleria.isVisibleSelectorPrecios();
	}

	@Step (
		description="Utilizar el selector de precio: Mínimo=" + TAG_MINIMO + " Máximo=" + TAG_MAXIMO, 
		expected="Aparecen artículos con precio en el intervalo seleccionado")
	public void selectInterval() throws Exception {
		var dataFilter = new DataFilterPrecios();
		if (isDesktop()) {
			pgGaleria.showFilters();
		}
		dataFilter.setMinimoOrig(pgGaleria.getMinImportFilter());
		dataFilter.setMaximoOrig(pgGaleria.getMaxImportFilter());

		pgGaleria.clickIntervalImportFilter(30, 30);
		dataFilter.setMinimoFinal(pgGaleria.getMinImportFilter());
		dataFilter.setMaximoFinal(pgGaleria.getMaxImportFilter());
		if (isDesktop()) {
			pgGaleria.acceptFilters();
		}
		
		replaceStepDescription(TAG_MINIMO, String.valueOf(dataFilter.getMinimoFinal()));
		replaceStepDescription(TAG_MAXIMO, String.valueOf(dataFilter.getMaximoFinal()));
		
		checkResultSelectFiltro(dataFilter);
		checksDefault();
	}
	
	@Validation
	private ChecksTM checkResultSelectFiltro(DataFilterPrecios dataFilter) throws Exception {
		var checks = ChecksTM.getNew();
		checks.add(
			"El nuevo mínimo es mayor que el anterior. Era de <b>" + dataFilter.getMinimoOrig() + "</b> y ahora es <b>" + dataFilter.getMinimoFinal() + "</b>",
			dataFilter.getMinimoFinal() > dataFilter.getMinimoOrig(), WARN);
		
		checks.add(
			"El nuevo máximo es menor que el anterior. Era de <b>" + dataFilter.getMaximoOrig() + "</b> y ahora es <b>" + dataFilter.getMaximoFinal() + "</b>",
			dataFilter.getMaximoFinal() < dataFilter.getMaximoOrig(), WARN);
		
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		checks.add(
			"Todos los precios están en el intervalo [" + dataFilter.getMinimoFinal() + ", " + dataFilter.getMaximoFinal() + "]",
			pageGaleria.preciosInIntervalo(dataFilter.getMinimoFinal(), dataFilter.getMaximoFinal()), WARN);
		
		return checks;
	}
}

class DataFilterPrecios {
	private int minimoOrig = 0;
	private int maximoOrig = 0;
	private int minimoFinal = 0;
	private int maximoFinal = 0;
	
	public int getMinimoOrig() {
		return minimoOrig;
	}
	public void setMinimoOrig(int minimoOrig) {
		this.minimoOrig = minimoOrig;
	}
	public int getMaximoOrig() {
		return maximoOrig;
	}
	public void setMaximoOrig(int maximoOrig) {
		this.maximoOrig = maximoOrig;
	}
	public int getMinimoFinal() {
		return minimoFinal;
	}
	public void setMinimoFinal(int minimoFinal) {
		this.minimoFinal = minimoFinal;
	}
	public int getMaximoFinal() {
		return maximoFinal;
	}
	public void setMaximoFinal(int maximoFinal) {
		this.maximoFinal = maximoFinal;
	}
}
