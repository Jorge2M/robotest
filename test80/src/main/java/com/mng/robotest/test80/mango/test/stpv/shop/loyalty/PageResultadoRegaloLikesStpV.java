package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageResultadoRegaloLikes;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageResultadoRegaloLikesStpV {

	private final PageResultadoRegaloLikes pageResultado;
	
	public PageResultadoRegaloLikesStpV(WebDriver driver) {
		this.pageResultado = new PageResultadoRegaloLikes(driver);
	}
	
	@Validation (
		description="Aparece la página de resultado Ok del envío de Likes (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsEnvioLikesOk(int maxSeconds) {
		return pageResultado.isEnvioOk(maxSeconds);
	}
	
}
