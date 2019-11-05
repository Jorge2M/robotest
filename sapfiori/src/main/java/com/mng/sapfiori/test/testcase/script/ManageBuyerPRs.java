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
import com.mng.sapfiori.test.testcase.stpv.purchasereqs.PageManagePRsByBuyerStpV;

public class ManageBuyerPRs {

    public ManageBuyerPRs() {}         
  
    @Test (
        groups={"ManagePRs", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Manage PRs from Buyer")
    public void MPR001_ManagePRsFromBuyer() throws Exception {
    	
    	TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
    	WebDriver driver = testCase.getDriver();
        driver.get(testCase.getInputParamsSuite().getUrlBase());
    	
    	PageLoginStpV pageLoginStpV = PageLoginStpV.getNew(driver);
    	PageIconsMenuStpV pageInitialStpV = 
    		pageLoginStpV.inputCredentialsAndEnter("00556106", "Irene_2016");
 
    	PageManagePRsByBuyerStpV pageManagePRsStpV = 
        	pageInitialStpV.clickManagePurchaseRequisitionsBuyer();
    	
    	//Define Filters
    	pageManagePRsStpV.searchSolicitudExistente("10000291");
    	pageManagePRsStpV.selectEstadoEdicion("Todo");
    	ModalSelectConditionsStpV modalSelectPrStpV =
    		pageManagePRsStpV.clickIconPurchaseRequisition();
    	
    	modalSelectPrStpV.include(ConditionInclude.contiene, "291");
    	modalSelectPrStpV.exclude(ConditionExclude.igual_que, "12345");
    	modalSelectPrStpV.clickOk();
    	
    	pageManagePRsStpV.selectClaseDocumento(Arrays.asList("Compras Generales", "Sol. Repetición TH"));
    	pageManagePRsStpV.clickIrButton();
    	
//    	ModalSetFilterFromListStpV modalSetFilterStpV = 
//    		pageSelProductsStpV.clickIconSetFilter(FilterFromList.EsquemaNumeracion);
//    	modalSetFilterStpV.selectElementByValue("EU01");

    }

}
