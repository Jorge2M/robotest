package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;

public class ModalArticleNotAvailableStpV {
    
	@Validation (
		description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Info)
    public static boolean validateState(int maxSecondsWait, StateModal stateModal, WebDriver driver) {	
		return (ModalArticleNotAvailable.inStateUntil(stateModal, maxSecondsWait, driver));
    }
    
	@Step (
		description="Seleccionamos el aspa del modal para cerrarlo", 
        expected="El modal queda en estado No-visible")
    public static void clickAspaForClose(WebDriver driver) {
        ModalArticleNotAvailable.clickAspaForClose(driver);
        
        //Validaciones
        validateState(1, StateModal.notvisible, driver);
    }
}
