package com.mng.sapfiori.access.test.testcase.script;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.sapfiori.access.test.testcase.generic.stpv.modals.ModalSelectItemStpV;
import com.mng.sapfiori.access.test.testcase.stpv.iconsmenu.PageIconsMenuStpV;
import com.mng.sapfiori.access.test.testcase.stpv.login.PageLoginStpV;
import com.mng.sapfiori.access.test.testcase.stpv.reclassifprods.PageReclassifProductsStpV;
import com.mng.sapfiori.access.test.testcase.stpv.reclassifprods.PageSelProdsToReclassifyStpV;
import com.mng.sapfiori.access.test.testcase.webobject.reclassifprods.PageSelProdsToReclassify.ProductData;
import com.mng.testmaker.domain.suitetree.TestCaseTM;

public class Piloto {

	public Piloto() {}

	@Test (
		groups={"Piloto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Se realiza un login de usuario")
	public void PIL001_Login() throws Exception {

		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		driver.get(testCase.getInputParamsSuite().getUrlBase());

		PageLoginStpV pageLoginStpV = PageLoginStpV.getNew(driver);
		PageIconsMenuStpV pageInitialStpV = 
			pageLoginStpV.inputCredentialsAndEnter("00556106", "Irene_2016");

		PageSelProdsToReclassifyStpV pageSelProductsStpV = 
			pageInitialStpV.clickClasificarProductos();

		//Define Filters
		ModalSelectItemStpV modalSetFilterStpV = 
			pageSelProductsStpV.clickIconEsquemaNumeracion();

		modalSetFilterStpV.searchAndSelectElement("EU01", "EU01");

		List<String> productsToReclassify = Arrays.asList("1810101GKC01003", "1810101GKC01002");
		modalSetFilterStpV = 
			pageSelProductsStpV.clickIconProducto();
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
