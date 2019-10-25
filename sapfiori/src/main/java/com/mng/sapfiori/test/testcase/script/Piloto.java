package com.mng.sapfiori.test.testcase.script;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mng.testmaker.domain.TestCaseTM;
import com.mng.sapfiori.test.testcase.generic.stpv.sections.filterheader.ModalSetFilterFromListStpV;
import com.mng.sapfiori.test.testcase.stpv.PageSelProdsToReclassifyStpV;
import com.mng.sapfiori.test.testcase.stpv.PageInitialStpV;
import com.mng.sapfiori.test.testcase.stpv.PageLoginStpV;
import com.mng.sapfiori.test.testcase.stpv.PageReclassifProductsStpV;
import com.mng.sapfiori.test.testcase.webobject.FiltersPageClassifProductos.FilterFromList;
import com.mng.sapfiori.test.testcase.webobject.PageSelProdsToReclassify.ProductData;

public class Piloto {

    public Piloto() {}         
      
    @BeforeMethod (groups={"Piloto", "Canal:desktop_App:all"})
    synchronized public void login() 
    throws Exception {
    }
    
    @AfterMethod (groups={"Piloto", "Canal:desktop_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
    }       

    @Test (
        groups={"Piloto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Se realiza un login de usuario")
    public void PIL001_Login() throws Exception {
    	
    	TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
    	WebDriver driver = testCase.getDriver();
        driver.get(testCase.getInputParamsSuite().getUrlBase());
    	
    	PageLoginStpV pageLoginStpV = PageLoginStpV.getNew(driver);
    	PageInitialStpV pageInitialStpV = 
    		pageLoginStpV.inputCredentialsAndEnter("00556106", "Irene_2016");
 
    	PageSelProdsToReclassifyStpV pageSelProductsStpV = 
    		pageInitialStpV.clickClasificarProductos();
    	
    	//Define Filters
    	ModalSetFilterFromListStpV modalSetFilterStpV = 
    		pageSelProductsStpV.clickIconSetFilter(FilterFromList.EsquemaNumeracion);
    	modalSetFilterStpV.selectElementByValue("EU01");
    	
    	List<String> productsToReclassify = Arrays.asList("1830950HPM001470", "1830950HPM002445");
    	modalSetFilterStpV = 
    		pageSelProductsStpV.clickIconSetFilter(FilterFromList.Producto);
    	modalSetFilterStpV.selectElementsByValue(productsToReclassify);
    	modalSetFilterStpV.clickOkButton();
    	
    	pageSelProductsStpV.clickIrButton();
    	List<ProductData> productsDataOriginal = 
    		pageSelProductsStpV.getPageObject().getData(productsToReclassify);
    			
    	pageSelProductsStpV.selectProducts(productsToReclassify);
    	
    	PageReclassifProductsStpV pageReclassifProdsStpV = 
    		pageSelProductsStpV.clickVolverAclasificar();
    	
    	String newCodEstadMerc = getNewCodEstadMerc(productsDataOriginal);
    	pageReclassifProdsStpV.writeInputCodEstadMercAndSave(newCodEstadMerc);
    	pageSelProductsStpV.checkIsVisiblePage(5);
    	pageSelProductsStpV.checkCodMercModified(newCodEstadMerc, productsToReclassify);
    }
    
    private String getNewCodEstadMerc(List<ProductData> productsDataOriginal) {
    	List<String> candidatesCodEstadMerc = Arrays.asList("6201930000", "6204629090");
    	String newCodEstadMerc = candidatesCodEstadMerc.get(0);
    	if (productsDataOriginal.get(0).codEstadMerc.compareTo(newCodEstadMerc)!=0) {
    		return newCodEstadMerc;
    	}
    	return candidatesCodEstadMerc.get(1);
    }
}
