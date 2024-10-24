package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.manto.pageobjects.PageMenusManto;
import com.mng.robotest.tests.domains.manto.pageobjects.PageSelTda;
import com.mng.robotest.tests.domains.manto.pageobjects.SecCabecera;
import com.mng.robotest.testslegacy.data.TiendaManto;

import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageSelTdaMantoSteps extends StepMantoBase {
	
	private static final String TAG_TIENDA = "@TagTienda";
	
	@Step (
		description="Seleccionamos el entorno \"" + TAG_TIENDA + "\"", 
		expected="Aparece la página de Menús",
		saveErrorData=NEVER)
	public void selectTienda(String codigoAlmacen, String codigoPais) {
		var tienda = TiendaManto.getTienda(codigoAlmacen, codigoPais, app);
		replaceStepDescription(TAG_TIENDA, tienda.name());
		
		var pageSelTda = new PageSelTda();
		if (!pageSelTda.isPage()) {
			new SecCabecera().clickButtonSelTienda();
		}
		
		pageSelTda.selectTienda(tienda);
		checkIsPageMenusManto();
	}
	
	@Validation(
		description="Aparece la página del Menú principal de Manto donde se encuentran todas las opciones de éste")
	private boolean checkIsPageMenusManto() {
		return (new PageMenusManto().isPage());
	}
	
}
