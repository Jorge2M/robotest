package com.mng.robotest.test.steps.shop.galeria;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.galeria.ModalArticleNotAvailable;
import com.mng.robotest.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;

import org.openqa.selenium.WebDriver;

public class ModalArticleNotAvailableSteps {

	WebDriver driver = null;
	Channel channel = null;
	AppEcom app = null;

	private ModalArticleNotAvailableSteps(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		this.driver = driver;
		this.channel = channel;
		this.app = app;
	}

	public static ModalArticleNotAvailableSteps getInstance(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		return (new ModalArticleNotAvailableSteps(channel, app, driver));
	}

	@Validation (
	description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} (lo esperamos hasta #{maxSeconds} segundos)",
	level=State.Info)
	public boolean validateState(int maxSeconds, StateModal stateModal, WebDriver driver) throws Exception {
		return (ModalArticleNotAvailable.inStateUntil(stateModal, maxSeconds, driver));
	}
	
	@Step (
		description="Seleccionamos el aspa del modal para cerrarlo", 
		expected="El modal queda en estado No-visible")
	public void clickAspaForClose(WebDriver driver) throws Exception {
		ModalArticleNotAvailable.clickAspaForClose(driver);
		validateState(1, StateModal.notvisible, driver);
	}

	@Validation
	public ChecksTM checkVisibleAvisame() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		boolean isVisibleModal = ModalArticleNotAvailable.isVisibleUntil(2, driver);
		boolean isVisibleRPGD = ModalArticleNotAvailable.isVisibleRPGD(2, driver);
		checks.add(
			"Si aparece el modal de avisame",
			isVisibleModal, State.Defect);
		checks.add(
			"Si aparece la descripcion de RPGD de usuario",
			isVisibleRPGD, State.Defect);
		return checks;
	}
}
