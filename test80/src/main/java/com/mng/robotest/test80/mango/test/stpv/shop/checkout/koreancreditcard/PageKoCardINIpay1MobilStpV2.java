package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyen;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay1Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay1Mobil.BodyPageKoCardINIpay1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class PageKoCardINIpay1MobilStpV2 {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    @Validation
    public static ChecksTM validateIsPage(WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
      	validations.add(
    		"Aparece una página de INIpay",
    		PageKoCardINIpay1Mobil.isPage(driver), State.Warn);
      	validations.add(
    		"Existe el botón <b>SamsungPay</b>",
    		PageKoCardINIpay1Mobil.isElementInStateUntil(BodyPageKoCardINIpay1.terms, StateElem.Present, 2, driver), 
    		State.Warn);
      	validations.add(
    		"Existe el checkbox para los <b>terminos</b> del pago",
    		PageKoCardINIpay1Mobil.isElementInStateUntil(BodyPageKoCardINIpay1.terms, StateElem.Present, 2, driver), 
    		State.Warn);
      	validations.add(
    		"Existe el titulo de los terminos",
    		PageKoCardAdyen.isElementInStateUntil(BodyPageKoCardINIpay1.termsTitle, StateElem.Present, 2, driver), 
    		State.Defect);
      	return validations;
    }

    @Step (
    	description="Marcamos el checkbox de los terminos",
        expected="Desaparecen el titulo de los terminos")
    public static void checkTerminosBox(WebDriver driver) throws Exception {
    	PageKoCardINIpay1Mobil.clickAndWait(BodyPageKoCardINIpay1.terms, 2, driver);

        //Validation
    	checkDesapareceApartadoTerminos(driver);
    }
    
    @Validation (
    	description="Desaparece el apartado de los términos",
    	level=State.Defect)
    private static boolean checkDesapareceApartadoTerminos(WebDriver driver) {
    	return (!PageKoCardINIpay1Mobil.isElementInStateUntil(BodyPageKoCardINIpay1.termsTitle, StateElem.Visible, 1, driver));
    }

	final static String litButtonTypeCard = "케이뱅크";
    @Step (
    	description="Seleccionamso el botón correspondiente al tipo de tarjeta <b>" + litButtonTypeCard + "</b>",
        expected="Aparece información varia y el boton de continuar")
    public static void continuarConPagoCoreaMobile(WebDriver driver) throws Exception {
    	BodyPageKoCardINIpay1.clickTypeCardButton(litButtonTypeCard, driver);
        
        //Validations
        PageKoCardINIpay2MobilStpV.validateIsPage(driver);
    }
}
