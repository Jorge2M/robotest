package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
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
    		state(Present, BodyPageKoCardINIpay1.terms.getBy(), driver).wait(2).check(), 
    		State.Warn);
      	validations.add(
    		"Existe el checkbox para los <b>terminos</b> del pago",
    		state(Present, BodyPageKoCardINIpay1.terms.getBy(), driver).wait(2).check(),
    		State.Warn);
      	validations.add(
    		"Existe el titulo de los terminos",
    		state(Present, BodyPageKoCardINIpay1.termsTitle.getBy(), driver).wait(2).check(),
    		State.Defect);
      	return validations;
    }

    @Step (
    	description="Marcamos el checkbox de los terminos",
        expected="Desaparecen el titulo de los terminos")
    public static void checkTerminosBox(WebDriver driver) throws Exception {
    	click(BodyPageKoCardINIpay1.terms.getBy(), driver).waitLoadPage(2).exec();
    	checkDesapareceApartadoTerminos(driver);
    }
    
    @Validation (
    	description="Desaparece el apartado de los términos",
    	level=State.Defect)
    private static boolean checkDesapareceApartadoTerminos(WebDriver driver) {
    	return (!state(Visible, BodyPageKoCardINIpay1.termsTitle.getBy(), driver)
    			.wait(1).check());
    }

	final static String litButtonTypeCard = "케이뱅크";
    @Step (
    	description="Seleccionamso el botón correspondiente al tipo de tarjeta <b>" + litButtonTypeCard + "</b>",
        expected="Aparece información varia y el boton de continuar")
    public static void continuarConPagoCoreaMobile(WebDriver driver) throws Exception {
    	BodyPageKoCardINIpay1.clickTypeCardButton(litButtonTypeCard, driver);
        PageKoCardINIpay2MobilStpV.validateIsPage(driver);
    }
}
