package com.mng.robotest.test80.mango.test.stpv.shop;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraDesktop;

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
	
	@Validation (
		description="Aparece el logo/link correcto correspondiente al canal, país, línea (esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
    public boolean validaLogoDesktop(int maxSecondsWait, LineaType lineaType) {
		return (((SecCabeceraDesktop)secCabecera).isPresentLogoCorrectUntil(dCtxSh.pais, lineaType, maxSecondsWait));
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
		}
		else {
	    	validations.add(
    			"<b>No</b> es posible comprar (aparece la capa relacionada con la bolsa)",
    			!isVisibleIconoBolsa, State.Warn);
		}
		return validations;
    }
	
	@Validation (
		description="Es visible el bloque de Likes en la cabecera",
		level=State.Defect)
	public boolean checkIsVisibleLikesDesktop() {
		//TODO implementar "isVisibleLikes" a nivel de la interfaz
		return (((SecCabeceraDesktop)secCabecera).isVisibleLikes());
	}
}
