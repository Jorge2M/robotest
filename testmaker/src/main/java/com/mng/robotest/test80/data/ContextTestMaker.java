package com.mng.robotest.test80.data;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.jdbc.dao.CorreosGroupDAO;
import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;

public class TestMakerContext {

	private final InputDataTestMaker inputData;
	private final List<String> toMail;
	private final List<String> ccMail;
	private final String subjectMail;
	
	private String idSuiteExecution;
	
	private TestMakerContext(InputDataTestMaker inputData) {
		this.inputData = inputData;
		
		toMail = new ArrayList<>();
		if (inputData.isEnvioCorreoGroup()) {
			toMail = CorreosGroupDAO.getCorreosGroup(inputData.getEnvioCorreo());
		} else {
			toMail.add("eqp.ecommerce.qamango@mango.com");
		}
		
		ccMail = new ArrayList<>();
		ccMail.add("jorge.munoz.sge@mango.com");
		parametersSuite.put(Constantes.paramAsuntoMail,  "Result TestSuite " + params.getSuiteName() + " (" + params.getAppE() + " / " + params.getURLBase() + ")");
	}
	
	public static TestMakerContext getNew(InputDataTestMaker inputData) {
		TestMakerContext testMakerCtx = new TestMakerContext(inputData);
		
	}

	
    public static void getCommonParameters(InputDataTestMaker inputData) {

        if (params.getEnvioCorreo()!=null && "".compareTo(params.getEnvioCorreo())!=0) {
            ArrayList<String> correosGroup = CorreosGroupDAO.getCorreosGroup(params.getEnvioCorreo());
            if (correosGroup!=null) {
                parametersSuite.put(Constantes.paramToListMail, toCommaSeparated(correosGroup));
            } else {
                parametersSuite.put(Constantes.paramToListMail, "eqp.ecommerce.qamango@mango.com");
            }
            
            parametersSuite.put(Constantes.paramCcListMail, "jorge.munoz.sge@mango.com");
            parametersSuite.put(Constantes.paramAsuntoMail,  "Result TestSuite " + params.getSuiteName() + " (" + params.getAppE() + " / " + params.getURLBase() + ")");
        }
        
        CallBack callBack = params.getCallBack();
        if (params.getCallBack()!=null) {
            parametersSuite.put(Constantes.paramCallBackMethod, callBack.getCallBackMethod());
            parametersSuite.put(Constantes.paramCallBackResource, callBack.getCallBackResource());
            parametersSuite.put(Constantes.paramCallBackSchema, callBack.getCallBackSchema());  
            parametersSuite.put(Constantes.paramCallBackParams, callBack.getCallBackParams());
            parametersSuite.put(Constantes.paramCallBackUser, callBack.getCallBackUser());
            parametersSuite.put(Constantes.paramCallBackPassword, callBack.getCallBackPassword());
        }

        //RecicleWD: Indica el modo de gestión de los webdriver/browsers
        //    true:  reutiliza el webdriver para futuros casos de prueba
        //    false: cuando acaba un caso de prueba cierra el webdriver
        if (params.getRecicleWD()!=null && params.getRecicleWD().compareTo("true")==0) {
            parametersSuite.put(Constantes.paramRecycleWD, "true");
        } else {
            parametersSuite.put(Constantes.paramRecycleWD, "false");
        }
    }
}
