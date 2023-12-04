package com.mng.robotest.tests.domains.galeria.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktopKondo;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecFiltros;
import com.mng.robotest.testslegacy.data.Color;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class SecFiltrosSteps extends StepBase {

	private final PageGaleria pgGaleria = PageGaleria.make(channel, app, dataTest.getPais());
	
	private static final String TAG_LIT_COLORS_TO_SELECT = "@TagLitColorsToSelect";
	
	@Step (
		description="Seleccionar los colores <b>" + TAG_LIT_COLORS_TO_SELECT + "</b>", 
		expected="Aparece la galería de imágenes",
		saveNettraffic=ALWAYS)
	public int selectFiltroColores(List<Color> colorsToSelect, String litMenu) {
		replaceStepDescription(
			TAG_LIT_COLORS_TO_SELECT, 
			Color.getListNamesFiltros(colorsToSelect).toString());
		
		int numArticulos1page = pgGaleria.selectColoresAndReturnNumArticles(colorsToSelect);			
		checkAfterSelectFiltroColores(colorsToSelect, litMenu, numArticulos1page);
		return numArticulos1page;
	}
	
	@Validation
	private ChecksTM checkAfterSelectFiltroColores(
			List<Color> colorsSelected, String litMenu, int numArticulos1page) {
		
		var checks = ChecksTM.getNew();
		if (dataTest.getPais().isGaleriaKondo(app)) {
			if (isDesktop()) {
			 	checks.add(
					"Aparecen los tags de color <b>" + colorsSelected.toString() + "</b>",
					((PageGaleriaDesktopKondo)pgGaleria).isVisibleColorTags(colorsSelected));
			}
		} else {
			String currentUrl = driver.getCurrentUrl();
			var listCodColors = Color.getListCodigosColor(colorsSelected);
		 	checks.add(
				"En la URL (*) aparece el parámetro c= que contiene los códigos de color <b>" + 
				listCodColors.toString() + "</b> (*) " + currentUrl + "<br>",
				SecFiltros.checkUrlAfterFilterContainsColors(colorsSelected, currentUrl), WARN);		
		}
		
	 	checks.add(
			"Aparece una pantalla en la que el title contiene \"" + litMenu.toUpperCase(),
			driver.getTitle().toUpperCase().contains(litMenu.toUpperCase()), WARN);
	 	
	 	checks.add(
			"En pantalla aparecen >1 artículos (están apareciendo " + numArticulos1page + ")",
			numArticulos1page>1, WARN);
	 	
	 	return checks;
	}

}
