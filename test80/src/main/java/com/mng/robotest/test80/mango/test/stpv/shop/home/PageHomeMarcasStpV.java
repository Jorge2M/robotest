package com.mng.robotest.test80.mango.test.stpv.shop.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.IdiomaPais;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageHomeMarcas;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;
import static com.mng.robotest.test80.mango.test.data.Constantes.PrefixRebajas;

public class PageHomeMarcasStpV {
	
	public final PageHomeMarcas pageHomeMarcas;
	public final Channel channel;
	public final AppEcom app;
	public final WebDriver driver;
	
	public static BannerRebajas2019StpV bannerRebajas2019; 
	public static BannerSpringIsHere2019StpV bannerSpringIsHere2019; 
	
	public enum TypeHome {Multimarca, PortadaLinea}

	public PageHomeMarcasStpV(Channel channel, AppEcom app, WebDriver driver) {
		pageHomeMarcas = new PageHomeMarcas(app, driver);
		this.channel = channel;
		this.app = app;
		this.driver = driver;
	}
	
	public void validateIsPageWithCorrectLineas(Pais pais) throws Exception {
		AllPagesStpV.validateMainContentPais(pais, driver);
		validateIsPageOk(pais);
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(channel, app, pais, driver);
		secMenusStpV.validateLineas(pais);
	}
	
	@Validation
	public ChecksTM validateIsPageOk(Pais pais) {
		ChecksTM validations = ChecksTM.getNew();
		if (app!=AppEcom.outlet) {
			validations.add(
				"Aparece la home de marcas/multimarcas según el país",
				pageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais), State.Warn);	
		}
		validations.add(
			"No aparece ningún tag de error",
			!state(Present, By.xpath("//error"), driver).check(), State.Warn);
		return validations;
	}
		
	@Validation
	public ChecksTM checkMsgNewsletterFooter(boolean salesOnInCountry, IdiomaPais idioma) {
		ChecksTM validations = ChecksTM.getNew();
		String percentageSymbol = UtilsTestMango.getPercentageSymbol(idioma);
		boolean isMsgWithPercentageSimbol = (new SecFooter(app, driver)).newsLetterMsgContains(percentageSymbol);
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
