package com.mng.robotest.test80.mango.test.stpv.shop.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageHomeMarcas;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;
import static com.mng.robotest.test80.mango.test.data.Constantes.PrefixRebajas;

public class PageHomeMarcasStpV {
	
	public static BannerRebajas2019StpV bannerRebajas2019; 
	public static BannerSpringIsHere2019StpV bannerSpringIsHere2019; 
	
    public enum TypeHome {Multimarca, PortadaLinea}

    public static void validateIsPageWithCorrectLineas(Pais pais, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        AllPagesStpV.validateMainContentPais(pais, driver);
        validateIsPageOk(pais, app, driver);
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(channel, app, pais, driver);
        secMenusStpV.validateLineas(pais);
    }
    
    @Validation
    public static ChecksTM validateIsPageOk(Pais pais, AppEcom app, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
    	if (app!=AppEcom.outlet) {
			validations.add(
				"Aparece la home de marcas/multimarcas según el país",
				PageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais, app, driver), State.Warn);    
    	}
		validations.add(
			"No aparece ningún tag de error",
			!state(Present, By.xpath("//error"), driver).check(), State.Warn);
		return validations;
    }
        
    @Validation
    public static ChecksTM checkMsgNewsletterFooter(boolean salesOnInCountry, IdiomaPais idioma, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
    	String percentageSymbol = UtilsTestMango.getPercentageSymbol(idioma);
    	boolean isMsgWithPercentageSimbol = SecFooter.getNewsLetterMsgText(driver).contains(percentageSymbol);
    	if (salesOnInCountry) {
	    	validations.add(
	    		PrefixRebajas + "El mensaje de NewsLetter del Footer No contiene \"" + percentageSymbol + "\"",
	    		!isMsgWithPercentageSimbol, State.Info, true);    
    	} else {
	    	validations.add(
	    		PrefixRebajas + "El mensaje de NewsLetter del Footer Sí contiene \"" + percentageSymbol + "\"",
	    		isMsgWithPercentageSimbol, State.Warn);    
    	}
    	
    	return validations;
    }
}
