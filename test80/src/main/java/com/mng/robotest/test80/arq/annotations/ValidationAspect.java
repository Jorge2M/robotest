package com.mng.robotest.test80.arq.annotations;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@SuppressWarnings("javadoc")
@Aspect
public class ValidationAspect {
    @Pointcut("@annotation(Validation)")
    public void annotationValidationPointcut() {
        //Pointcut
    }
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationValidationPointcut()")
    public void before(JoinPoint joinPoint) {
    	System.out.println("Before : " + joinPoint.getSignature().getName());
    }
    
//    @AfterReturning(pointcut="annotationValidationPointcut() && atExecution()")
//    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
//        System.out.println("hijacked : " + joinPoint.getSignature().getName());
//        ValidationResult valResult;
//        valResult = new ValidationResult();
//        
//    	modifyValidationResultAccordingAnnotationParams(valResult, joinPoint);
//    	//TODO el datosStep se puede acabar eliminando pero de momento lo forzaremos como parámetro obligatorio sólo para probar
//    	DatosStep datosStep = getDatosStepFromMethodParams(joinPoint);
//    	
//    	//TODO al dFTest habría que ubicarlo en una clase de arquitectura en lugar del GestorWebDriver
//    	List<SimpleValidation> listVals = new ArrayList<>();
//    	fmwkTest.addValidation(1, valResult.getLevelError(), listVals);
//        fmwkTest.grabStepValidation(datosStep, valResult.getDescription(), GestorWebDriver.getdFTest());
//    }
    
    @AfterReturning(pointcut="annotationValidationPointcut() && atExecution()", returning="result")
    public void grabValidationAfter(JoinPoint joinPoint, Object result) throws Throwable {
        System.out.println("hijacked : " + joinPoint.getSignature().getName());
        ValidationResult valResult;
        if (result!=null && result instanceof ValidationResult) {
        	valResult = (ValidationResult)result;
        }
        else {
        	valResult = new ValidationResult();
        }
        
    	modifyValidationResultAccordingAnnotationParams(valResult, joinPoint);
    	//TODO el datosStep se puede acabar eliminando pero de momento lo forzaremos como parámetro obligatorio sólo para probar
    	DatosStep datosStep = getDatosStepFromMethodParams(joinPoint);
    	
    	//TODO al dFTest habría que ubicarlo en una clase de arquitectura en lugar del GestorWebDriver
    	List<SimpleValidation> listVals = new ArrayList<>();
    	fmwkTest.addValidation(1, valResult.getLevelError(), listVals);
        fmwkTest.grabStepValidation(datosStep, valResult.getDescription(), GestorWebDriver.getdFTest());
    }
    
	//TODO el datosStep se puede acabar eliminando pero de momento lo forzaremos como parámetro obligatorio sólo para probar
    private DatosStep getDatosStepFromMethodParams(JoinPoint joinPoint) {
    	Object[] signatureArgs = joinPoint.getArgs();
    	for (Object signatureArg: signatureArgs) {
    		if (signatureArg!=null && signatureArg instanceof DatosStep) {
    			return ((DatosStep)signatureArg);
    		}
    	}
    	
    	throw (new RuntimeException("A parameter of Type DatosStep is mandatory in method with @Validation annotation"));
    }
    
    private void modifyValidationResultAccordingAnnotationParams(ValidationResult valResult, JoinPoint joinPoint) {
    	Validation validation = getValidationAnnotation(joinPoint);
    	if ("".compareTo(valResult.getDescription())==0) {
    		valResult.setDescription(validation.description());
    	}
    	
    	if (valResult.getLevelError()==State.Undefined &&
    		validation.level()!=null) {
    		valResult.setLevelError(validation.level());
    	}
    }
    
    private Validation getValidationAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Validation validationAnnotation = method.getAnnotation(Validation.class);
        return (validationAnnotation);
    }
    
    //TODO manage exception -> NOK
}
