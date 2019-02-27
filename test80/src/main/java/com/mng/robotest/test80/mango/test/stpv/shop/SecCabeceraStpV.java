package com.mng.robotest.test80.mango.test.stpv.shop;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraDesktop;

public class SecCabeceraStpV {

	private final SecCabecera secCabecera;
	private final DataCtxShop dCtxSh;
	
	private SecCabeceraStpV(DataCtxShop dCtxSh, DataFmwkTest dFTest) {
		this.dCtxSh = dCtxSh;
		this.secCabecera = SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
	}
	
	public static SecCabeceraStpV getNew(DataCtxShop dCtxSh, DataFmwkTest dFTest) {
		return (new SecCabeceraStpV(dCtxSh, dFTest));
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
    public ListResultValidation validateIconoBolsa() {
		ListResultValidation validations = ListResultValidation.getNew();
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
}
