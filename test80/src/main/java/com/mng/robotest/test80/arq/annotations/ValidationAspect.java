package com.mng.robotest.test80.arq.annotations;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.sun.javafx.collections.MappingChange.Map;

import java.lang.reflect.Method;
import java.util.HashMap;

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
    
//    @Before("annotationValidationPointcut()")
//    public void aaa(JoinPoint joinPoint) {
//        System.out.println("aspect before");
//        Object[] signatureArgs = joinPoint.getArgs();
//        for (Object signatureArg: signatureArgs) {
//           System.out.println("Arg: " + signatureArg);
//        }
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
        fmwkTest.grabStepValidation(valResult.datosStep, valResult.validation, valResult.dFTest);
    }
    
//    private DataFmwkTest getDataFmwkTestFromMethodParams(JoinPoint joinPoint) {
//    	Object[] signatureArgs = joinPoint.getArgs();
//    	for (Object signatureArg: signatureArgs) {
//    		if (signatureArg!=null && signatureArg instanceof DataFmwkTest) {
//    			return ((DataFmwkTest)signatureArg);
//    		}
//    	}
//    	
//    	throw (new RuntimeException("A parameter of Type DataFmwkTest is mandatory in method with @Validation annotation"));
//    }
    
    private void modifyValidationResultAccordingAnnotationParams(ValidationResult valResult, JoinPoint joinPoint) {
    	HashMap<String, Object> params = getAnnotationParameters(joinPoint);
    	if (params.get("description")!=null && 
    		"".compareTo(valResult.getDescription())==0) {
    		valResult.setDescription((String)params.get("description"));
    	}
    	
    	if (params.get("level")!=null && 
    		valResult.getLevelError()==State.Undefined) {
    		valResult.setDescription((String)params.get("level"));
    	}
    }
    
    private HashMap<String, Object> getAnnotationParameters(JoinPoint joinPoint) {
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] values = joinPoint.getArgs();
        HashMap<String, Object> params = new HashMap<>();
        if (argNames.length != 0) {
            for (int i = 0; i < argNames.length; i++) {
                params.put(argNames[i], values[i]);
            }
        }
        
        return params;
    }
    
    //TODO manage exception -> NOK
}
