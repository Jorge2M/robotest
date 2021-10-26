package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.SecCrossSelling;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;

@SuppressWarnings({"static-access"})
public class SecCrossSellingStpV {

	private final SecCrossSelling secCrossSelling;
	private final WebDriver driver;
	private final AppEcom app;
	
	public SecCrossSellingStpV(Channel channel, AppEcom app, WebDriver driver) {
		this.secCrossSelling = new SecCrossSelling(channel, driver);
		this.driver = driver;
		this.app = app;
	}
	
	@Validation
	public ChecksTM validaIsCorrect(LineaType lineaType) throws Exception {
		//Obtenemos la lista de menús de Mujer-Prendas
		SecMenusDesktop secMenus = SecMenusDesktop.getNew(app, driver);
		List<WebElement> listaMenusBloque = 
			secMenus.secMenuSuperior.secBlockMenus.getListMenusLineaBloque(lineaType, bloqueMenu.prendas);

		String litMenu1 = listaMenusBloque.get(0).findElement(By.xpath("./span")).getAttribute("innerHTML");
		String litMenu2 = listaMenusBloque.get(1).findElement(By.xpath("./span")).getAttribute("innerHTML");
		String litMenu3 = listaMenusBloque.get(2).findElement(By.xpath("./span")).getAttribute("innerHTML");
		String hrefMenu1 = listaMenusBloque.get(0).getAttribute("href");
		String hrefMenu2 = listaMenusBloque.get(1).getAttribute("href");
		String hrefMenu3 = listaMenusBloque.get(2).getAttribute("href");

		ChecksTM validations = ChecksTM.getNew();
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app, driver);
		if (!secCrossSelling.isSectionVisible()) {
			pageGaleria.scrollToPageFromFirst(PageGaleriaDesktop.maxPageToScroll);
		}
		validations.add(
			"La sección cross-selling existe (si de primeras no existe scrollamos hasta el final de la galería)",
			secCrossSelling.isSectionVisible(), State.Defect);
		validations.add(
			"Aparecen los links correspondientes a los 3 primeros menús de Mujer-Prendas:<br>" + 
			litMenu1 + " (" + hrefMenu1 + "),<br> " + 
			litMenu2 + " (" + hrefMenu2 + "),<br>" + 
			litMenu3 + " (" + hrefMenu3 + ")",
			secCrossSelling.linkAssociatedToMenu(1, litMenu1, hrefMenu1) &&
			secCrossSelling.linkAssociatedToMenu(2, litMenu2, hrefMenu2) &&
			secCrossSelling.linkAssociatedToMenu(3, litMenu3, hrefMenu3), State.Defect);
		
		pageGaleria.goToInitPageAndWaitForArticle();
		return validations;
	}
}
