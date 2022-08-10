package com.mng.robotest.test.steps.manto;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.TiendaMantoEnum.TiendaManto;
import com.mng.robotest.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test.pageobject.shop.PageMenusManto;


public class PageSelTdaMantoSteps extends StepBase {
	
	private static final String TAG_TIENDA = "@TagTienda";
	
	@Step (
		description="Seleccionamos el entorno \"" + TAG_TIENDA + "\"", 
		expected="Aparece la página de Menús",
		saveErrorData=SaveWhen.Never)
	public void selectTienda(String codigoAlmacen, String codigoPais) throws Exception {
		TiendaManto tienda = TiendaManto.getTienda(codigoAlmacen, codigoPais, app);
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_TIENDA, tienda.name());
		
		if (!PageSelTda.isPage(driver)) {
			SecCabecera.clickButtonSelTienda(driver);
		}
		
		PageSelTda.selectTienda(tienda, driver);
		checkIsPageMenusManto();
	}
	
	@Validation(
		description="Aparece la página del Menú principal de Manto donde se encuentran todas las opciones de éste",
		level=State.Defect)
	private boolean checkIsPageMenusManto() {
		return (new PageMenusManto().isPage());
	}

//	/**
//	 * Accede a la tienda asociada al almacén (sólo si no estamos en ella ya)
//	 */
//	public static void goToTiendaPais(String codigoAlmacen, String codigoPais,  AppEcom appE, WebDriver driver) 
//	throws Exception {
//		String tiendaActual = SecCabecera.getLitTienda(driver);
//		TiendaManto tiendaToGo = TiendaManto.getTienda(codigoAlmacen, codigoPais, appE);
//		if (!tiendaActual.contains(tiendaToGo.litPantManto)) {
//			selectTienda(codigoAlmacen, codigoPais, appE, driver);
//		}
//	}
}
