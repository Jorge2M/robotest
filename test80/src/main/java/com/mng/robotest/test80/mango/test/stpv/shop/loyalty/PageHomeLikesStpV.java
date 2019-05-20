package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomePurchaseWithDiscount;
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
    	PageHomeLikes pageLikes = PageHomeLikes.getNew(driver);
    	validations.add(
    		"Aparece la página de <b>Mango likes you</b>",
    		pageLikes.checkIsPage(), State.Defect);
    	validations.add(
    		"Aparecen bloques para el canjeo de Likes",
    		pageLikes.areVisibleBlocksExchangeLikes(), State.Defect);
    	return validations;
	}

	@Step(
		description="Seleccionar el link \"Compra un descuento\"",
		expected="Aparece la página de \"Compra con descuento\"")
	public void clickOpcionCompraUnDescuento() throws Exception {
		PageHomeLikes pageLikes = PageHomeLikes.getNew(driver);
		pageLikes.clickPurchaseWithDiscount();
        checkHomePurchaseWithDiscountPageOk();
	}

	@Step(
		description="Seleccionar el 1er botón \"Donar mis Likes\"",
		expected="Aparece una página para donar mis Likes")
	public void clickButtonDonarLikes() throws Exception {
		PageHomeLikes.getNew(driver).clickDonateLikes();
		PageHomeDonateLikesStpV.getNew(driver).checkIsPage();
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Conseguir por 1200 Likes\"",
		expected="Aparece una página para conseguir por 1200 Likes")
	public void clickButtonConseguirPor1200Likes() throws Exception {
		PageHomeLikes.getNew(driver).clickConseguirPor1200Likes();
		PageHomeConseguirPor1200LikesStpV.getNew(driver).checkIsPage();
	}

	@Validation
	public ChecksResult checkHomePurchaseWithDiscountPageOk() {
		ChecksResult validations = ChecksResult.getNew();
		PageHomePurchaseWithDiscount pageHomePurchaseWithDiscount = PageHomePurchaseWithDiscount.getNewInstance(driver);
		validations.add(
			"Aparece la página de <b>Descuento Mango likes you</b>",
			pageHomePurchaseWithDiscount.checkIsPage(), State.Defect);
		validations.add(
			"Aparece el boton que permite <b>comprar ahora</b>",
			pageHomePurchaseWithDiscount.areVisibleButtonPurchaseNow(), State.Defect);
		return validations;
	}
}
