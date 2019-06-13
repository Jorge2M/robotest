package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;
import org.openqa.selenium.WebDriver;

public class ModalArticleNotAvailableStpV {

    WebDriver driver = null;
    Channel channel = null;
    AppEcom app = null;

    private ModalArticleNotAvailableStpV(Channel channel, AppEcom app, WebDriver driver) throws Exception {
        this.driver = driver;
        this.channel = channel;
        this.app = app;
    }

    public static ModalArticleNotAvailableStpV getInstance(Channel channel, AppEcom app, WebDriver driver) throws Exception {
        return (new ModalArticleNotAvailableStpV(channel, app, driver));
    }

    @Validation (
    description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} (lo esperamos hasta #{maxSecondsWait} segundos)",
    level=State.Info)
    public boolean validateState(int maxSecondsWait, StateModal stateModal, WebDriver driver) throws Exception {
		return (ModalArticleNotAvailable.inStateUntil(stateModal, maxSecondsWait, driver));
    }
    
	@Step (
		description="Seleccionamos el aspa del modal para cerrarlo", 
        expected="El modal queda en estado No-visible")
    public void clickAspaForClose(WebDriver driver) throws Exception {
        ModalArticleNotAvailable.clickAspaForClose(driver);
        validateState(1, StateModal.notvisible, driver);
    }

    @Validation
    public ChecksResult checkVisibleAvisame() throws Exception {
        ChecksResult validations = ChecksResult.getNew();
        boolean isVisibleModal = ModalArticleNotAvailable.isVisibleUntil(2, driver);
        boolean isVisibleRPGD = ModalArticleNotAvailable.isVisibleRPGD(2, driver);
        validations.add(
            "Si aparece el modal de avisame",
            isVisibleModal, State.Defect);
        validations.add(
            "Si aparece la descripcion de RPGD de usuario",
            isVisibleRPGD, State.Defect);
        return validations;
    }
}
