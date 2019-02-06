package com.mng.robotest.test80.arq.annotations.validation;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@SuppressWarnings("javadoc")
@Aspect
public class ValidationAspect {
	
	InfoValidation infoValidation;
	
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
    
    @AfterReturning(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	returning="resultMethod")
    public void grabValidationAfter(JoinPoint joinPoint, Object resultMethod) throws Throwable {
    	infoValidation = InfoValidation.from(joinPoint);
    	ValidationResult valResult = getValResultFromMethod(resultMethod);
    	modifyValidationResultAccordingAnnotationParams(valResult, joinPoint);
    	
    	//TODO el datosStep se puede acabar eliminando pero de momento lo forzaremos como parámetro obligatorio sólo para probar
    	DatosStep datosStep = getDatosStepFromMethodParams(joinPoint);
    	
    	if (!valResult.isOvercomed()) {
	    	//TODO al dFTest habría que ubicarlo en una clase de arquitectura en lugar del GestorWebDriver
	    	List<SimpleValidation> listVals = new ArrayList<>();
	    	fmwkTest.addValidation(1, valResult.getLevelError(), listVals);
    	}
    	
    	fmwkTest.grabStepValidation(datosStep, valResult.getDescription(), GestorWebDriver.getdFTest());
    }
    
    private ValidationResult getValResultFromMethod(Object resultMethod) {
        if (resultMethod!=null && resultMethod instanceof Boolean) {
        	ValidationResult valResult = new ValidationResult();
        	valResult.setOvercomed((Boolean)resultMethod);
        	return valResult;
        }
        
        if (resultMethod!=null && resultMethod instanceof ValidationResult) {
        	return (ValidationResult)resultMethod;
        }
        
        throw (new RuntimeException("The return of a method marked with @Validation annotation must be of type boolean or ValidationResult"));
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
    		String descripValidationMatched = getValidationDescriptionMatchingWithMethodParameters(validation, joinPoint);
    		valResult.setDescription(descripValidationMatched);
    	}
    	
    	if (valResult.getLevelError()==State.Undefined &&
    		validation.level()!=null) {
    		valResult.setLevelError(validation.level());
    	}
    }
    
    private String getValidationDescriptionMatchingWithMethodParameters(Validation validation, JoinPoint joinPoint) {
    	String descrToReturn  = validation.description();
    	Pattern p = Pattern.compile("\\#\\{([^}]*)\\}");
    	Matcher m = p.matcher(descrToReturn);
    	while (m.find()) {
    	  String group = m.group(1);
    	  String valueParameter = getStringValueParameterFromMethod(group, joinPoint);
    	  descrToReturn = descrToReturn.replace("#{" + group + "}", valueParameter);
    	  System.out.println(m.group(1));
    	}
    	
    	return (descrToReturn);
    }
    
    private String getStringValueParameterFromMethod(String paramNameInDescrValidation, JoinPoint joinPoint) {
    	Object[] signatureArgs = joinPoint.getArgs();
    	String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
    	for (int i=0; i<parameterNames.length; i++) {
    		if (parameterNames[i].compareTo(paramNameInDescrValidation)==0) {
    			return (signatureArgs[i].toString());
    		}
    	}
    	
    	return null;
    }
    
    //TODO manage exception -> NOK
}
