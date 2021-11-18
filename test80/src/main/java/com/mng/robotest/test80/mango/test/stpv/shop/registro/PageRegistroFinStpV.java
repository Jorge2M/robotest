package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroFin;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;

@SuppressWarnings({"static-access"})
public class PageRegistroFinStpV {
	
	@Validation(
		description="Aparece la página final del proceso de registro (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
		return (PageRegistroFin.isPageUntil(maxSeconds, driver));
	}
	
	@Step (
		description="Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
		expected="Se accede a la shop correctamente")
	public static void clickIrDeShoppingButton(DataCtxShop dCtxSh, WebDriver driver) {
		PageRegistroFin.clickIrDeShopping(driver);
		SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver).clickLogoMango();
		validateWeAreLogged(dCtxSh, driver);
	}
	
	public static void validateWeAreLogged(DataCtxShop dCtxSh, WebDriver driver) {
		validateLogoGoesToPaisIdioma(dCtxSh, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		secMenusUserStpV.checkIsVisibleLinkCerrarSesion();
	}
	
	@Validation
	public static ChecksTM validateLogoGoesToPaisIdioma(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"El logo de Mango redirige al país/idioma origen: " + dCtxSh.idioma.getAcceso(),
			SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver).validaLogoMangoGoesToIdioma(dCtxSh.idioma), State.Warn);
		return validations;		
	}
}