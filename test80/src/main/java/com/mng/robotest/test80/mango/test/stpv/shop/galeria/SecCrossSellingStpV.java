package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.List;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;

@SuppressWarnings({"static-access"})
public class SecCrossSellingStpV {

	@Validation
    public static ChecksResult validaIsCorrect(LineaType lineaType, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Obtenemos la lista de menús de Mujer-Prendas
        List<WebElement> listaMenusBloque = 
        	SecMenusDesktop.
        		secMenuSuperior.secBlockMenus.getListMenusLineaBloque(lineaType, bloqueMenu.prendas, app, dFTest.driver);
        String litMenu1 = listaMenusBloque.get(0).getAttribute("innerHTML");
        String litMenu2 = listaMenusBloque.get(1).getAttribute("innerHTML");
        String litMenu3 = listaMenusBloque.get(2).getAttribute("innerHTML");
        
    	ChecksResult validations = ChecksResult.getNew();
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
    	if (!PageGaleriaDesktop.secCrossSelling.isSection(dFTest.driver)) {
            pageGaleria.scrollToPageFromFirst(PageGaleriaDesktop.maxPageToScroll, app);
    	}
    	validations.add(
    		"La sección cross-selling existe (si de primeras no existe scrollamos hasta el final de la galería)<br>",
    		PageGaleriaDesktop.secCrossSelling.isSection(dFTest.driver), State.Defect);
    	validations.add(
    		"Aparecen los links correspondientes a los 3 primeros menús de Mujer-Prendas (<b>" + litMenu1 + ", " + litMenu2 + ", " + litMenu3 + "</b>)",
    		PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(1, listaMenusBloque.get(0), dFTest.driver) &&
            PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(2, listaMenusBloque.get(1), dFTest.driver) &&
            PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(3, listaMenusBloque.get(2), dFTest.driver), State.Defect);
    	return validations;
    }
}
