package com.mng.robotest.test80.mango.test.stpv.shop.checkout.klarna;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.klarna.DataKlarna;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.klarna.PageKlarna;


public class PageKlarnaStpV {

	private final PageKlarna pageKlarna;
	
	public PageKlarnaStpV(WebDriver driver) {
		this.pageKlarna = new PageKlarna(driver);
	}
	
	@Validation (
		description="Aparece la página inicial de Klarna (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
    public boolean checkIsPage(int maxSeconds) { 
        return pageKlarna.isPage(5);
    }
	
	@Step (
		description="Seleccionamos el botón <b>Comprar</b>", 
        expected="Aparece el modal de introducción de datos del usuario")
	public void clickComprar() {
        pageKlarna.clickBuyButton();
        checkIsModalInputUserData(5);
	}
	
	@Validation (
		description="Aparece el modal de introducción de los datos del usuario (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
    private boolean checkIsModalInputUserData(int maxSeconds) { 
        return pageKlarna.isVisibleModalInputUserData(maxSeconds);
    }
	
	@Step (
		description="Introducir y confirmar los datos del usuario:<br>#{dataKlarna.getHtmlFormattedData()}",
        expected="Aparece el modal para la introducción del número personal")
	public void inputUserDataAndConfirm(DataKlarna dataKlarna) {
		pageKlarna.inputUserDataAndConfirm(dataKlarna);
	}
	
	@Validation (
		description="Es visible el modal para la introducción del número personal (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Info)
    public boolean checkModalInputPersonNumber(int maxSeconds) { 
        return pageKlarna.isVisibleModalPersonNumber(maxSeconds);
    }
	
	@Step (
		description="Introducir y confirmar el Personal Number: <b>#{personnumber}</b>",
        expected="Aparece la página Mango de resultado Ok de la compra")
	public void inputPersonNumberAndConfirm(String personnumber) {
		pageKlarna.inputPersonNumberAndConfirm(personnumber);
	}
	
}
