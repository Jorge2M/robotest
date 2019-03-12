package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataSearchDeliveryPoint.DataSearchDp;

@SuppressWarnings({"static-access"})
public class SecSelectDPointStpV {
    
	final static String tagSearchDp = "@TagSearchDp";
	@Step (
		description="Introducimos la provincia <b>" + tagSearchDp + "</b> + Return", 
        expected="Aparecen puntos de recogida de " + tagSearchDp)
    public static void searchPoblacion(DataSearchDeliveryPoint dataSearchDp, WebDriver driver) 
    throws Exception {
		DatosStep datosStep = TestCaseData.getDatosCurrentStep();
		datosStep.replaceInDescription(tagSearchDp, dataSearchDp.data);
		datosStep.replaceInExpected(tagSearchDp, dataSearchDp.data);
		
    	TypeDeliveryPoint typeDp = dataSearchDp.tipoTransporte.getTypeDeliveryPoint();
        ModalDroppoints.secSelectDPoint.sendProvincia(dataSearchDp.data, driver);
        WebdrvWrapp.waitForPageLoaded(driver, 5);   

        checkDroppointSelectedContainsDirecc(dataSearchDp, driver);
        validaDeliveryPointOfType(typeDp, driver);
    }
	
	@Validation
	private static ListResultValidation checkDroppointSelectedContainsDirecc(DataSearchDeliveryPoint dataSearchDp, WebDriver driver) 
	throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
	    int maxSecondsWait = 5;
	    State stateVal = State.Warn;
	    if (dataSearchDp.typeData==DataSearchDp.CodigoPostal) {
	    	stateVal = State.Info;
    	}
	 	validations.add(
			"La dirección del droppoint seleccionado contiene <b>" + dataSearchDp.data + "</b> (lo esperamos hasta " + maxSecondsWait + " segundos)",
			ModalDroppoints.secSelectDPoint.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, maxSecondsWait, driver), stateVal);
		return validations;
	}
    
    @Validation
    public static ListResultValidation validaDeliveryPointOfType(TypeDeliveryPoint typeDp, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 3;
	 	validations.add(
			"Es visible el 1er delivery point de la lista (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
			ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1, maxSecondsWait, driver), State.Defect);
	 	validations.add(
			"El 1er delivery point de la lista es de tipo <b>" + typeDp + "</b>",
			ModalDroppoints.secSelectDPoint.getTypeDeliveryPoint(1, driver)==typeDp, State.Defect);
	 	return validations;
    }
    
    @Step (
    	description="Clickamos en el <b>#{position}º</b> droppoint", 
        expected="El droppoint queda seleccionado")
    public static DataDeliveryPoint clickDeliveryPointAndGetData(int position, WebDriver driver) throws Exception {
    	DataDeliveryPoint dataDpToReturn = ModalDroppoints.secSelectDPoint.clickDeliveryPointAndGetData(position, driver);
    	checkIsSelectedDroppoint(position, driver);
        return dataDpToReturn;
    }
    
    @Validation (
    	description="Queda seleccionado el Droppoint #{position}",
    	level=State.Defect)
    private static boolean checkIsSelectedDroppoint(int position, WebDriver driver) {
        return (ModalDroppoints.secSelectDPoint.isDroppointSelected(position, driver));
    }
    
    @Step (
    	description="Clickamos el botón de \"Select\" de la capa de Droppoints", 
        expected="Desaparece al capa de droppoint")
    public static void clickSelectButton(Channel channel, WebDriver driver) throws Exception {
    	int maxSecondsWait = 5;
        ModalDroppoints.secSelectDPoint.clickSelectButtonAndWait(maxSecondsWait, driver);
        ModalDroppointsStpV.secConfirmDatos.validateIsVisible(3, channel, driver);
    }
}
