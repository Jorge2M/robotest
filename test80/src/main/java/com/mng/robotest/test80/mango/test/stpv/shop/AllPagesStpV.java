package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.WebDriverArqUtils;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.ResultadoErrores;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

@SuppressWarnings("javadoc")
public class AllPagesStpV {
    
    public static void validacionesEstandar(boolean validaSEO, boolean validaJS, boolean validaImgBroken, 
    										datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        validacionesEstandar(validaSEO, State.Info_NoHardcopy, validaJS, State.Info_NoHardcopy, validaImgBroken, State.Warn, datosStep, dFTest);
    }
    
    public static void validacionesEstandar(boolean validaSEO, State levelAlertSEO, boolean validaJS, 
    										State levelAlertJS, boolean validaImgBroken, State levelAlertImgBroken, 
    										datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones
        String descripValidac = "";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (validaSEO) {
                descripValidac+="1) Se cumplen las validaciones genéricas de SEO<br>"; 
                ResultadoErrores resValidac = AllPagesSEO.validacionesGenericasSEO(dFTest.driver);
                if (!resValidac.isOK()) {
                    descripValidac += resValidac.getlistaLogError().toString() + "<br>";
                    fmwkTest.addValidation(1, levelAlertSEO, listVals);
                }
            }
            //2)
            if (validaJS) {
            	//Nota: No funciona con GeckoDriver porque no están implementados los servicios al no formar parte del protocolo W3C https://github.com/w3c/webdriver/issues/406
            	if (WebdrvWrapp.getTypeDriver(dFTest.driver)!=TypeDriver.firefox &&
            		WebdrvWrapp.getTypeDriver(dFTest.driver)!=TypeDriver.firefoxhless) {
	                descripValidac+="2) No hay errores JavaScript<br>";
	                ResultadoErrores resultadoLogs = WebDriverArqUtils.getLogErrors(Level.WARNING, dFTest.driver, 1/*maxErrors*/, dFTest.ctx);
	                if (resultadoLogs.getResultado() != ResultadoErrores.Resultado.OK) { //Si hay error lo pintamos en la descripción de la validación
	                    descripValidac += resultadoLogs.getlistaLogError().toString()  + "<br>";
	                    if (resultadoLogs.getResultado() != ResultadoErrores.Resultado.MAX_ERRORES) //Sólo mostraremos warning en caso que alguno no se haya mostrado ya un máximo de veces durante el test
	                        fmwkTest.addValidation(2, levelAlertJS, listVals);
	                }
            	}
            }
            //3)
            if (validaImgBroken) {
                descripValidac+="3) No hay ninguna imagen cortada<br>";
                ResultadoErrores resultadoImgs = WebDriverMngUtils.imagesBroken(dFTest.driver, Channel.desktop, 1/*maxErrors*/, dFTest.ctx);
                if (resultadoImgs.getResultado() != ResultadoErrores.Resultado.OK) { //Si hay error lo pintamos en la descripción de la validación
                    descripValidac += resultadoImgs.getlistaLogError().toString();
                    if (resultadoImgs.getResultado() != ResultadoErrores.Resultado.MAX_ERRORES) //Sólo mostraremos warning en caso que alguno no se haya mostrado ya un máximo de veces durante el test
                        fmwkTest.addValidation(3, levelAlertImgBroken, listVals);
                }
            }
            //4)
            descripValidac+="4) No hay literales sin traducir (contienen \"???\" o \"#\")";
            if (AllPages.isCodLiteralSinTraducir(dFTest.driver))
                fmwkTest.addValidation(4, State.Warn, listVals);
           
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validatePageWithFooter(Pais pais, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones
        String descripValidac = 
            "1) No hay SRCs con HTTPs maliciosos<br>" + 
            "2) Aparece el footer<br>";
        if (pais!=null)
            descripValidac+=
            "3) Aparece el div de contenido asociado al país " + pais.getCodigo_pais();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (datosStep.getResultSteps() == State.Ok) {
                ArrayList<String> srcMalicious = AllPages.httpMalicious(dFTest.driver, dFTest.ctx, pais);
                if (srcMalicious.size() > 0) {
                    descripValidac += srcMalicious.toString();
                    fmwkTest.addValidation(1, State.Warn, listVals);
                }
            }
            //2)
            if (!SecFooter.isPresent(app, dFTest.driver)) {
                fmwkTest.addValidation(2, State.Warn, listVals);
                if (WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//footer"))) {
                    descripValidac += "<br><b>data-cache-id footer</b>: " + dFTest.driver.findElement(By.xpath("//footer")).getAttribute("data-cache-id");
                    descripValidac += "<br><b>style footer</b>: " + dFTest.driver.findElement(By.xpath("//footer")).getAttribute("style");
                }
            }
            //3)
            if (pais!=null) {
                if (!WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//div[@class[contains(.,'main-content')] and @data-pais='" + pais.getCodigo_pais() + "']")))
                    fmwkTest.addValidation(3, State.Warn, listVals);
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * Validaciones que comprueban que está apareciendo el Main Content asociado a un país concreto
     */
    public static void validateMainContentPais(Pais pais, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el div de contenido asociado al país " + pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!AllPages.isPresentMainContent(pais, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void backNagegador(DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep       (
            "Realizamos un <b>back</b> del navegador", 
            "Se vuelve a la página anterior");
        try {
            dFTest.driver.navigate().back();
            WebdrvWrapp.waitForPageLoaded(dFTest.driver, 10/*segundos*/);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
    }
    

}
