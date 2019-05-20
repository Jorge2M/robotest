package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroFin;

@SuppressWarnings({"static-access"})
public class PageRegistroFinStpV {
    
	@Validation(
		description="Aparece la página final del proceso de registro (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
    public static boolean isPageUntil(int maxSecondsWait, DataFmwkTest dFTest) {
		return (PageRegistroFin.isPageUntil(maxSecondsWait, dFTest.driver));
    }
    
	@Step (
		description="Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
        expected="Se accede a la shop correctamente")
    public static void clickIrDeShoppingButton(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        PageRegistroFin.clickIrDeShopping(dFTest.driver);
        SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver).clickLogoMango();
        validateWeAreLogged(dCtxSh, dFTest.driver);
    }
	
	@Validation
	public static ChecksResult validateWeAreLogged(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"El logo de Mango redirige al país/idioma origen: " + dCtxSh.idioma.getAcceso(),
    		SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver).validaLogoMangoGoesToIdioma(dCtxSh.idioma), State.Warn);
    	validations.add(
    		"Aparece el link superior de <b>Cerrar Sesión</b> (estamos loginados)",
    		SecMenusWrap.secMenusUser.isPresentCerrarSesion(dCtxSh.channel, driver), State.Defect);
    	return validations;		
	}
}