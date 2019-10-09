package com.mng.testmaker.boundary.aspects.step;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mng.testmaker.domain.StepTestMaker;

public class InfoStep {

	private final Step stepAnnotation;
	private final StepTestMaker datosStep;
	
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
	
	public StepTestMaker getDatosStep() {
		return datosStep;
	}
	
    private Step getStepAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Step StepAnnotation = method.getAnnotation(Step.class);
        return (StepAnnotation);
    }
    
    private StepTestMaker getDatosStepFromStepAnnotation() {
    	StepTestMaker datosStep = new StepTestMaker();
    	datosStep.setDescripcion(stepAnnotation.description());
    	datosStep.setResExpected(stepAnnotation.expected());
    	datosStep.setSaveImagePage(stepAnnotation.saveImagePage());
    	datosStep.setSaveErrorPage(stepAnnotation.saveErrorData());
    	datosStep.setSaveHtmlPage(stepAnnotation.saveHtmlPage());
    	datosStep.setSaveNettrafic(stepAnnotation.saveNettraffic());
    	return datosStep;
    }
}