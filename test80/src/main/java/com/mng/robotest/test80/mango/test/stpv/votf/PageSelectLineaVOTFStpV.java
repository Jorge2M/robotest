package com.mng.robotest.test80.mango.test.stpv.votf;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.testab.manager.TestABmanager;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageSelectLineaVOTFStpV {

	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) { 
    	ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Aparece el banner correspondiente a SHE",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.she, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a MAN",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.he, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a NIÑAS",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.nina, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a NIÑOS",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.nino, driver), State.Warn);
    	validations.add(
    		"Aparece el banner correspondiente a VIOLETA",
    		PageSelectLineaVOTF.isBannerPresent(LineaType.violeta, driver), State.Warn);
    	return validations;
    }
    
	@Step (
		description="Seleccionar el #{umMenu}o menu de Mujer y finalmente seleccionar el logo de Mango",
        expected="Aparece la página inicial de SHE")
    public static void selectMenuAndLogoMango(int numMenu, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        PageSelectLineaVOTF.clickBanner(LineaType.she, driver);
        PageSelectLineaVOTF.clickMenu(LineaType.she, numMenu, driver);
        SecCabecera.getNew(Channel.desktop, AppEcom.votf, driver).clickLogoMango();
        SectionBarraSupVOTFStpV.validate(dCtxSh.pais.getAccesoVOTF().getUsuario(), driver);
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
}
