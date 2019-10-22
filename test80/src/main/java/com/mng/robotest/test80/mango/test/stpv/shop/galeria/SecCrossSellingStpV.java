package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;

@SuppressWarnings({"static-access"})
public class SecCrossSellingStpV {

	@Validation
    public static ChecksResult validaIsCorrect(LineaType lineaType, AppEcom app, WebDriver driver) 
    throws Exception {
        //Obtenemos la lista de menús de Mujer-Prendas
		SecMenusDesktop secMenus = SecMenusDesktop.getNew(app, driver);
        List<WebElement> listaMenusBloque = 
        	secMenus.secMenuSuperior.secBlockMenus.getListMenusLineaBloque(lineaType, bloqueMenu.prendas);
        
        String litMenu1 = listaMenusBloque.get(0).getAttribute("innerHTML");
        String litMenu2 = listaMenusBloque.get(1).getAttribute("innerHTML");
        String litMenu3 = listaMenusBloque.get(2).getAttribute("innerHTML");
        String hrefMenu1 = listaMenusBloque.get(0).getAttribute("href");
        String hrefMenu2 = listaMenusBloque.get(1).getAttribute("href");
        String hrefMenu3 = listaMenusBloque.get(2).getAttribute("href");
        
    	ChecksResult validations = ChecksResult.getNew();
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
    	if (!PageGaleriaDesktop.secCrossSelling.isSectionVisible(driver)) {
            pageGaleria.scrollToPageFromFirst(PageGaleriaDesktop.maxPageToScroll, app);
    	}
    	validations.add(
    		"La sección cross-selling existe (si de primeras no existe scrollamos hasta el final de la galería)",
    		PageGaleriaDesktop.secCrossSelling.isSectionVisible(driver), State.Defect);
    	validations.add(
    		"Aparecen los links correspondientes a los 3 primeros menús de Mujer-Prendas:<br>" + 
    		litMenu1 + " (" + hrefMenu1 + "),<br> " + 
    		litMenu2 + " (" + hrefMenu2 + "),<br>" + 
    		litMenu3 + " (" + hrefMenu3 + ")",
    		PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(1, litMenu1, hrefMenu1, driver) &&
            PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(2, litMenu2, hrefMenu2, driver) &&
            PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(3, litMenu3, hrefMenu3, driver), State.Defect);
    	return validations;
    }
}
