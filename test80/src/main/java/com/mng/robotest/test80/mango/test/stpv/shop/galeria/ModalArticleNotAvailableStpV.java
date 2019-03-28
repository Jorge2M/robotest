package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;
import org.openqa.selenium.WebDriver;

public class ModalArticleNotAvailableStpV {

    WebDriver driver = null;
    ChannelEnum.Channel channel = null;
    AppEcomEnum.AppEcom app = null;

    private ModalArticleNotAvailableStpV(ChannelEnum.Channel channel, AppEcomEnum.AppEcom app, WebDriver driver) throws Exception {
        this.driver = driver;
        this.channel = channel;
        this.app = app;
    }

    public static ModalArticleNotAvailableStpV getInstance(ChannelEnum.Channel channel, AppEcomEnum.AppEcom app, WebDriver driver) throws Exception {
        return (new ModalArticleNotAvailableStpV(channel, app, driver));
    }

    @Validation (
    description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} (lo esperamos hasta #{maxSecondsWait} segundos)",
    level=State.Info)
    public boolean validateState(int maxSecondsWait, StateModal stateModal, WebDriver driver) {
		return (ModalArticleNotAvailable.inStateUntil(stateModal, maxSecondsWait, driver));
    }
    
	@Step (
		description="Seleccionamos el aspa del modal para cerrarlo", 
        expected="El modal queda en estado No-visible")
    public void clickAspaForClose(WebDriver driver) {
        ModalArticleNotAvailable.clickAspaForClose(driver);
        
        //Validaciones
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
