package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.List;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;

@SuppressWarnings({"javadoc", "static-access"})
public class SecCrossSellingStpV {

    public static void validaIsCorrect(LineaType lineaType, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        //Obtenemos la lista de menús de Mujer-Prendas
        List<WebElement> listaMenusBloque = 
        	SecMenusDesktop.
        		secMenuSuperior.secBlockMenus.getListMenusLineaBloque(lineaType, bloqueMenu.prendas, app, dFTest.driver);
        String litMenu1 = listaMenusBloque.get(0).getAttribute("innerHTML");
        String litMenu2 = listaMenusBloque.get(1).getAttribute("innerHTML");
        String litMenu3 = listaMenusBloque.get(2).getAttribute("innerHTML");
        
        //Validaciones
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
        String descripValidac = 
            "1) La sección cross-selling existe (si de primeras no existe scrollamos hasta el final de la galería)<br>" +
            "2) Aparecen los links correspondientes a los 3 primeros menús de Mujer-Prendas (<b>" + litMenu1 + ", " + litMenu2 + ", " + litMenu3 + "</b>)";  
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGaleriaDesktop.secCrossSelling.isSection(dFTest.driver)) {
                pageGaleria.scrollToPageFromFirst(PageGaleriaDesktop.maxPageToScroll, app);
                if (!PageGaleriaDesktop.secCrossSelling.isSection(dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
            }
            if (!PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(1, listaMenusBloque.get(0), dFTest.driver) ||
                !PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(2, listaMenusBloque.get(1), dFTest.driver) ||
                !PageGaleriaDesktop.secCrossSelling.linkAssociatedToMenu(3, listaMenusBloque.get(2), dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
