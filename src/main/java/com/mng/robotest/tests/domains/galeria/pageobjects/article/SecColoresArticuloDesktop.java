package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class SecColoresArticuloDesktop extends PageBase {

	public abstract String getNameColorFromCodigo(String codigoColor);
	public abstract Optional<WebElement> getArticuloConVariedadColores(int numArticulo);
	public abstract void clickColorArticulo(WebElement articulo, int posColor);
	
	public static SecColoresArticuloDesktop make(AppEcom app, Pais pais) {
		if (pais.isGaleriaKondo(app)) {
			return new SecColoresArticuloDesktopKondo();
		}
		return new SecColoresArticuloDesktopNormal();
	}

}
