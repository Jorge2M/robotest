package com.mng.robotest.test80.arq.annotations.step;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;

public class InfoStep {

	private final JoinPoint joinPoint;
	private final Step stepAnnotation;
	private final DatosStep datosStep;
	
	private InfoStep(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
		this.stepAnnotation = getStepAnnotation(joinPoint);
		this.datosStep = getDatosStepFromStepAnnotation();
	}
	
	public static InfoStep from(JoinPoint joinPoint) {
		return (new InfoStep(joinPoint));
	}
	
    private Step getStepAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Step StepAnnotation = method.getAnnotation(Step.class);
        return (StepAnnotation);
    }
    
    private DatosStep getDatosStepFromStepAnnotation() {
    	DatosStep datosStep = new DatosStep();
    	datosStep.setDescripcion(stepAnnotation.description());
    	datosStep.setResExpected(stepAnnotation.expected());
    	datosStep.setSaveImagePage(stepAnnotation.saveImagePage());
    	datosStep.setSaveErrorPage(stepAnnotation.saveErrorPage());
    	datosStep.setSaveHtmlPage(stepAnnotation.saveHtmlPage());
    	datosStep.setSaveNettrafic(saveNettraffic, context);
    	
    	SaveData saveNettraffic() default SaveData.Never;
    	
    	datosStep.setGrabNettrafic(GestorWebDriver.getdFTest().ctx);

    	return datosStep;
    }
    
    private String getValidationDescriptionMatchingWithMethodParameters() {
    	String descrToReturn  = valAnnotation.description();
    	Pattern p = Pattern.compile("\\#\\{([^}]*)\\}");
    	Matcher m = p.matcher(descrToReturn);
    	while (m.find()) {
    	  String group = m.group(1);
    	  String valueParameter = getStringValueParameterFromMethod(group);
    	  descrToReturn = descrToReturn.replace("#{" + group + "}", valueParameter);
    	}
    	
    	return (descrToReturn);
    }
    
    private String getStringValueParameterFromMethod(String paramNameInDescrValidation) {
    	Object[] signatureArgs = joinPoint.getArgs();
    	String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
    	for (int i=0; i<parameterNames.length; i++) {
    		if (parameterNames[i].compareTo(paramNameInDescrValidation)==0) {
    			return (signatureArgs[i].toString());
    		}
    	}
    	
    	return null;
    }
}