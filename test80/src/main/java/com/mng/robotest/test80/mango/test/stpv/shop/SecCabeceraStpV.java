package com.mng.robotest.test80.mango.test.stpv.shop;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraOutletMovil;

public class SecCabeceraStpV {

	private final SecCabecera secCabecera;
	private final DataCtxShop dCtxSh;
	
	private SecCabeceraStpV(DataCtxShop dCtxSh, WebDriver driver) {
		this.dCtxSh = dCtxSh;
		this.secCabecera = SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver);
	}
	
	public static SecCabeceraStpV getNew(DataCtxShop dCtxSh, WebDriver driver) {
		return (new SecCabeceraStpV(dCtxSh, driver));
	}
    
	@Step (
		description="Seleccionar el logo de Mango", 
        expected="Se accede a la página principal de la línea")
    public void selecLogo() throws Exception {
		secCabecera.clickLogoMango();            
    }    
    
	@Validation
    public ChecksResult validateIconoBolsa() {
		ChecksResult validations = ChecksResult.getNew();
		boolean isVisibleIconoBolsa = secCabecera.isVisibleIconoBolsa();
		if ("true".compareTo(dCtxSh.pais.getShop_online())==0) {
	    	validations.add(
    			"<b>Sí</b> es posible comprar (aparece la capa relacionada con la bolsa)",
    			isVisibleIconoBolsa, State.Warn);
		} else {
	    	validations.add(
    			"<b>No</b> es posible comprar (aparece la capa relacionada con la bolsa)",
    			!isVisibleIconoBolsa, State.Warn);
		}
		return validations;
    }
	
	@Step (
		description="Establecer con visibilidad #{setVisible} el menú izquierdo de móvil",
		expected="El menú lateral se establece con visibilidad #{setVisible}")
	public void setVisibilityLeftMenuMobil(boolean setVisible) throws Exception {
		((SecCabeceraOutletMovil)secCabecera).clickIconoMenuHamburguer(setVisible);
	}
}
