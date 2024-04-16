package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

public abstract class SecColoresArticuloDesktop extends PageBase {

	public abstract String getNameColorFromCodigo(String codigoColor);
	public abstract Optional<WebElement> getArticuloConVariedadColores(int numArticulo);
	public abstract void clickColorArticulo(WebElement articulo, int posColor);
	
	public static SecColoresArticuloDesktop make() {
		return new SecColoresArticuloDesktopNormal();
	}

}
