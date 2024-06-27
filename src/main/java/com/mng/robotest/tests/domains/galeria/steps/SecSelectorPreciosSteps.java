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
	
	@Validation (description="Es visible el selector de precios", level=WARN)
	public boolean checkIsSelector() {
		return pgGaleria.isVisibleSelectorPrecios();
	}

	@Step (
		description="Utilizar el selector de precio: Mínimo=<b>#{minim}</b> Máximo=#{maxim}", 
		expected="Aparecen artículos con precio en el intervalo seleccionado")
	public void selectInterval(int minim, int maxim) throws Exception {
		pgGaleria.selectIntervalImports(minim, maxim);
		checkResultSelectFiltro(minim, maxim);
		checksDefault();
	}
	
	@Validation
	private ChecksTM checkResultSelectFiltro(int min, int max) throws Exception {
		var checks = ChecksTM.getNew();
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		checks.add(
			"En pantalla aparece el fitro por precios aplicado con valores [<b>" + min + " - " + max + "</b>]",
			pageGaleria.isVisibleLabelFiltroPrecioApplied(min, max));
		
		checks.add(
			"Todos los precios están en el intervalo [<b>" + min + " - " + max + "</b>]",
			pageGaleria.preciosInIntervalo(min, max));
		
		return checks;
	}
}
