package com.mng.robotest.test80.mango.test.stpv.votf;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageSelectLineaVOTFStpV {

	@Validation
    public static ListResultValidation validateIsPage(WebDriver driver) { 
    	ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Aparece el banner correspondiente a SHE<br>",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.she, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a MAN<br>",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.he, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a NIÑAS<br>",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.nina, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a NIÑOS<br>",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.nino, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a VIOLETA",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.violeta, driver), State.Warn);
    	return validations;
    }
    
	@Step (
		description="Seleccionar el #{umMenu}o menu de Mujer y finalmente seleccionar el logo de Mango",
        expected="Aparece la página inicial de SHE")
    public static void selectMenuAndLogoMango(int numMenu, Pais pais, WebDriver driver) throws Exception {
        PageSelectLineaVOTF.clickBanner(LineaType.she, driver);
        PageSelectLineaVOTF.clickMenu(LineaType.she, numMenu, driver);
        SecCabecera.getNew(Channel.desktop, AppEcom.votf, driver).clickLogoMango();
        
        //Validaciones
        SectionBarraSupVOTFStpV.validate(pais.getAccesoVOTF().getUsuario(), driver);
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
}
