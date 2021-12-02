package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.SecSelectorPreciosDesktop;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

@SuppressWarnings({"static-access"})
public class SecSelectorPreciosStpV {

	private final SecSelectorPreciosDesktop selectorPreciosDesktop;
	private final SecFiltrosDesktop secFiltrosDesktop;
	private final WebDriver driver;
	private final AppEcom app;
	private final Channel channel;
	
	public SecSelectorPreciosStpV(AppEcom app, Channel channel, WebDriver driver) {
		this.selectorPreciosDesktop = new SecSelectorPreciosDesktop(app, driver);
		this.secFiltrosDesktop = SecFiltrosDesktop.getInstance(channel, app, driver);
		this.driver = driver;
		this.app = app;
		this.channel = channel;
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
	final static String tagMinimo = "[MINIMO]";
	final static String tagMaximo = "[MAXIMO]";
	@Step (
		description="Utilizar el selector de precio: Mínimo=" + tagMinimo + " Máximo=" + tagMaximo, 
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
			secFiltrosDesktop.hideFilters();
		}
		
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagMinimo, String.valueOf(dataFilter.minimoFinal));
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagMaximo, String.valueOf(dataFilter.maximoFinal));	
		checkResultSelectFiltro(dataFilter);

		GenericChecks.from(Arrays.asList(
				GenericCheck.SEO,
				GenericCheck.JSerrors,
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}
	
	@Validation
	private ChecksTM checkResultSelectFiltro(DataFilterPrecios dataFilter) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"El nuevo mínimo es mayor que el anterior. Era de <b>" + dataFilter.minimoOrig + "</b> y ahora es <b>" + dataFilter.minimoFinal + "</b>",
			dataFilter.minimoFinal > dataFilter.minimoOrig, State.Warn);
		validations.add(
			"El nuevo máximo es menor que el anterior. Era de <b>" + dataFilter.maximoOrig + "</b> y ahora es <b>" + dataFilter.maximoFinal + "</b>",
			dataFilter.maximoFinal < dataFilter.maximoOrig, State.Warn);
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app, driver);
		validations.add(
			"Todos los precios están en el intervalo [" + dataFilter.minimoFinal + ", " + dataFilter.maximoFinal + "]",
			pageGaleria.preciosInIntervalo(dataFilter.minimoFinal, dataFilter.maximoFinal), State.Warn);
		return validations;
	}
}

class DataFilterPrecios {
	public int minimoOrig = 0;
	public int maximoOrig = 0;
	public int minimoFinal = 0;
	public int maximoFinal = 0;
	public DataFilterPrecios() {}
}
