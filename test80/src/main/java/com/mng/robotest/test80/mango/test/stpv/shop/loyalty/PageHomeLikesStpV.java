package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeDonateLikes;
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
    	PageHomeLikes pageLikes = PageHomeLikes.getNewInstance(driver);
    	validations.add(
    		"Aparece la página de <b>Mango likes you</b><br>",
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
		PageHomeLikes pageLikes = PageHomeLikes.getNewInstance(driver);
		pageLikes.clickPurchaseWithDiscount();
	}

	@Step(
		description="Seleccionar el link \"Donar Likes\"",
		expected="Aparece la página de \"Donar Likes\"")
	public void clickOpcionDonarLikes() throws Exception {
		PageHomeLikes pageLikes = PageHomeLikes.getNewInstance(driver);
		pageLikes.clickDonateLikes();
	}

	@Validation
	public ChecksResult checkHomePurchaseWithDiscountPageOk() {
		ChecksResult validations = ChecksResult.getNew();
		PageHomePurchaseWithDiscount pageHomePurchaseWithDiscount = PageHomePurchaseWithDiscount.getNewInstance(driver);
		validations.add(
				"Aparece la página de <b>Descuento Mango likes you</b><br>",
				pageHomePurchaseWithDiscount.checkIsPage(), State.Defect);
		validations.add(
				"Aparece el boton que permite <b>comprar ahora</b><br>",
				pageHomePurchaseWithDiscount.areVisibleButtonPurchaseNow(), State.Defect);
		return validations;
	}

	@Validation
	public ChecksResult checkHomeDonateLikesPageOk() {
		ChecksResult validations = ChecksResult.getNew();
		PageHomeDonateLikes pageHomeDonateLikes = PageHomeDonateLikes.getNewInstance(driver);
		validations.add(
				"Aparece la pagina de <b>Donar Likes<b><br>",
				pageHomeDonateLikes.checkIsPage(), State.Defect);
		validations.add(
				"Aparece los botones de <b>Donar likes</b><br>",
				pageHomeDonateLikes.areVisibleButtonsDonateLikes(), State.Defect);
		return validations;
	}
}
