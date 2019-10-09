package com.mng.robotest.test80.mango.test.stpv.shop.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
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
    public static ChecksResult validateIsPageOk(Pais pais, AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	if (app!=AppEcom.outlet) {
			validations.add(
				"Aparece la home de marcas/multimarcas según el país",
				PageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais, app, driver), State.Warn);    
    	}
		validations.add(
			"No aparece ningún tag de error",
			!WebdrvWrapp.isElementPresent(driver, By.xpath("//error")), State.Warn);
		return validations;
    }
        
    @Validation
    public static ChecksResult checkMsgNewsletterFooter(boolean salesOnInCountry, IdiomaPais idioma, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
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
