package com.mng.robotest.test80.arq.annotations.step;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mng.robotest.test80.arq.utils.ThreadData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

public class InfoStep {

	private final Step stepAnnotation;
	private final DatosStep datosStep;
	
	private InfoStep(JoinPoint joinPoint) {
		this.stepAnnotation = getStepAnnotation(joinPoint);
		this.datosStep = getDatosStepFromStepAnnotation();
	}
	
	public static InfoStep from(JoinPoint joinPoint) {
		return (new InfoStep(joinPoint));
	}
	
	public Step getStepAnnotation() {
		return stepAnnotation;
	}
	
	public DatosStep getDatosStep() {
		return datosStep;
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
    	if (ThreadData.getdFTest()!=null) {
    		datosStep.setSaveNettrafic(stepAnnotation.saveNettraffic(), ThreadData.getdFTest().ctx);
    	}

    	return datosStep;
    }
}