package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeLikes;

public class PageHomeLikesStpV {

	WebDriver driver;
	
	private PageHomeLikesStpV(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeLikesStpV getNewInstance(WebDriver driver) {
		return (new PageHomeLikesStpV(driver));
	}
	
	@Validation
	public ChecksResult checkIsPageOk() {
    	ChecksResult validations = ChecksResult.getNew();
    	PageHomeLikes pageLikes = PageHomeLikes.getNewInstance(driver);
    	validations.add(
    		"Aparece la página de <b>Mango likes you</b><br>",
    		pageLikes.checkIsPage(), State.Defect);
    	validations.add(
    		"Aparecen bloques para el canjeo de Likes",
    		pageLikes.areVisibleBlocksExchangeLikes(), State.Defect);
    	return validations;
	}
}
