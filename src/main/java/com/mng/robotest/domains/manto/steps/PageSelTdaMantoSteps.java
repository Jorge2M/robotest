package com.mng.robotest.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageMenusManto;
import com.mng.robotest.domains.manto.pageobjects.PageSelTda;
import com.mng.robotest.domains.manto.pageobjects.SecCabecera;
import com.mng.robotest.test.data.TiendaManto;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageSelTdaMantoSteps extends StepMantoBase {
	
	private static final String TAG_TIENDA = "@TagTienda";
	
	@Step (
		description="Seleccionamos el entorno \"" + TAG_TIENDA + "\"", 
		expected="Aparece la página de Menús",
		saveErrorData=SaveWhen.Never)
	public void selectTienda(String codigoAlmacen, String codigoPais) {
		TiendaManto tienda = TiendaManto.getTienda(codigoAlmacen, codigoPais, app);
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_TIENDA, tienda.name());
		
		var pageSelTda = new PageSelTda();
		if (!pageSelTda.isPage()) {
			new SecCabecera().clickButtonSelTienda();
		}
		
		pageSelTda.selectTienda(tienda);
		checkIsPageMenusManto();
	}
	
	@Validation(
		description="Aparece la página del Menú principal de Manto donde se encuentran todas las opciones de éste",
		level=Defect)
	private boolean checkIsPageMenusManto() {
		return (new PageMenusManto().isPage());
	}
	
}
