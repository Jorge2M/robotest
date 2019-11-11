package com.mng.sapfiori.test.testcase.script;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.domain.TestCaseTM;
import com.mng.sapfiori.test.testcase.generic.stpv.modals.ModalSelectConditionsStpV;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.ModalSelectConditions.ConditionExclude;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.ModalSelectConditions.ConditionInclude;
import com.mng.sapfiori.test.testcase.stpv.iconsmenu.PageIconsMenuStpV;
import com.mng.sapfiori.test.testcase.stpv.login.PageLoginStpV;
import com.mng.sapfiori.test.testcase.stpv.pedidos.PageGestionSolPedidoBuyerStpV;
import com.mng.sapfiori.test.testcase.stpv.pedidos.PageSolicitudPedidoStpV;

public class SolicitudPedido {

    public SolicitudPedido() {}         
  
    @Test (
        groups={"ManagePRs", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Manage PRs from Buyer")
    public void SOL001_SolicitudPedidoBuyer() throws Exception {
    	
    	TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
    	WebDriver driver = testCase.getDriver();
        driver.get(testCase.getInputParamsSuite().getUrlBase());
    	
    	PageLoginStpV pageLoginStpV = PageLoginStpV.getNew(driver);
    	PageIconsMenuStpV pageInitialStpV = 
    		pageLoginStpV.inputCredentialsAndEnter("00556106", "Irene_2016");
 
    	PageGestionSolPedidoBuyerStpV pageGestSolPedidoStpV = 
        	pageInitialStpV.clickManagePurchaseRequisitionsBuyer();
    	
    	PageSolicitudPedidoStpV pageSolPedidoStpV = 
    		pageGestSolPedidoStpV.clickIconAñadirPedido();
    	
    	
    	pageSolPedidoStpV.añadirArticulo("Material");
//    	
//    	pageManagePRsStpV.selectClaseDocumento("Solicitud Pedido TH");
//    	pageManagePRsStpV.clickRadioDetFuenteAprov();
//    	pageManagePRsStpV.inputDescripcionSolicitud("CINTURON JUDITH 30");
//    	pageManagePRsStpV.clickIconAddArticulos();
//    	
//    	pageManagePRsStpV.selectTipoArticulo("Material"); //Aparece la página de "Posición de solicitud de pedido"
//    	pageSolicitudPedido.filtrar("*JUDITH*");
    	
    	
    	
    	//Define Filters
//    	pageManagePRsStpV.searchSolicitudExistente("10000291");
//    	pageManagePRsStpV.selectEstadoEdicion("Todo");
//    	ModalSelectConditionsStpV modalSelectPrStpV =
//    		pageManagePRsStpV.clickIconPurchaseRequisition();
//    	
//    	modalSelectPrStpV.include(ConditionInclude.contiene, "291");
//    	modalSelectPrStpV.exclude(ConditionExclude.igual_que, "12345");
//    	modalSelectPrStpV.clickOk();
//    	
//    	pageManagePRsStpV.selectClaseDocumento(Arrays.asList("Compras Generales", "Sol. Repetición TH"));
//    	pageManagePRsStpV.clickIrButton();

    }

}
