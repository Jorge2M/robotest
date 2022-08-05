package com.mng.robotest.test.steps.shop.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.test.data.Constantes.PrefixRebajas;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.PageHomeMarcas;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.UtilsTest;

public class PageHomeMarcasSteps {
	
	public final PageHomeMarcas pageHomeMarcas;
	public final Channel channel;
	public final AppEcom app;
	public final WebDriver driver;
	
	public enum TypeHome {Multimarca, PortadaLinea}

	public PageHomeMarcasSteps(Channel channel, AppEcom app, WebDriver driver) {
		pageHomeMarcas = new PageHomeMarcas(app, driver);
		this.channel = channel;
		this.app = app;
		this.driver = driver;
	}
	
	public void validateIsPageWithCorrectLineas(Pais pais) throws Exception {
		AllPagesSteps.validateMainContentPais(pais, driver);
		validateIsPageOk(pais);
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps(channel, app, pais);
		secMenusSteps.validateLineas(pais);
	}
	
	@Validation
	public ChecksTM validateIsPageOk(Pais pais) {
		ChecksTM checks = ChecksTM.getNew();
		if (app!=AppEcom.outlet) {
			checks.add(
				"Aparece la home de marcas/multimarcas según el país",
				pageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais), State.Warn);	
		}
		checks.add(
			"No aparece ningún tag de error",
			!state(Present, By.xpath("//error"), driver).check(), State.Warn);
		return checks;
	}
		
	@Validation
	public ChecksTM checkMsgNewsletterFooter(boolean salesOnInCountry, IdiomaPais idioma) {
		ChecksTM checks = ChecksTM.getNew();
		String percentageSymbol = UtilsTest.getPercentageSymbol(idioma);
		boolean isMsgWithPercentageSimbol = (new SecFooter(app, driver)).newsLetterMsgContains(percentageSymbol);
		if (salesOnInCountry) {
			checks.add(
				Check.make(
				    PrefixRebajas + "El mensaje de NewsLetter del Footer No contiene \"" + percentageSymbol + "\"",
				    !isMsgWithPercentageSimbol, State.Info)
				.store(StoreType.None).build());	
		} else {
			checks.add(
				PrefixRebajas + "El mensaje de NewsLetter del Footer Sí contiene \"" + percentageSymbol + "\"",
				isMsgWithPercentageSimbol, State.Warn);	
		}
		
		return checks;
	}
}
