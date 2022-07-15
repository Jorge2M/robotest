package com.mng.robotest.domains.loyalty.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.loyalty.pageobjects.PageResultadoRegaloLikes;

public class PageResultadoRegaloLikesSteps {

	private final PageResultadoRegaloLikes pageResultado;
	
	public PageResultadoRegaloLikesSteps(WebDriver driver) {
		this.pageResultado = new PageResultadoRegaloLikes(driver);
	}
	
	@Validation (
		description="Aparece la página de resultado Ok del envío de Likes (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsEnvioLikesOk(int maxSeconds) {
		return pageResultado.isEnvioOk(maxSeconds);
	}
	
}
