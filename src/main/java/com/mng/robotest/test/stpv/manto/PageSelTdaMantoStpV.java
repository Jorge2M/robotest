package com.mng.robotest.test.stpv.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.TiendaMantoEnum.TiendaManto;
import com.mng.robotest.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test.pageobject.shop.PageMenusManto;

/**
 * Ejecución de pasos/validaciones relacionados con la página "Selección de la tienda en Manto" (la posterior al login)
 * @author jorge.munoz
 *
 */

public class PageSelTdaMantoStpV {
	
	static final String TagTienda = "@TagTienda";
	@Step (
		description="Seleccionamos el entorno \"" + TagTienda + "\"", 
		expected="Aparece la página de Menús",
		saveErrorData=SaveWhen.Never)
	public static void selectTienda(String codigoAlmacen, String codigoPais, AppEcom appE, WebDriver driver) 
	throws Exception {
		TiendaManto tienda = TiendaManto.getTienda(codigoAlmacen, codigoPais, appE);
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagTienda, tienda.name());
		
		if (!PageSelTda.isPage(driver)) {
			SecCabecera.clickButtonSelTienda(driver);
		}
		
		PageSelTda.selectTienda(tienda, driver);
		checkIsPageMenusManto(driver);
	}
	
	@Validation(
		description="Aparece la página del Menú principal de Manto donde se encuentran todas las opciones de éste",
		level=State.Defect)
	private static boolean checkIsPageMenusManto(WebDriver driver) {
		return (PageMenusManto.isPage(driver));
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
