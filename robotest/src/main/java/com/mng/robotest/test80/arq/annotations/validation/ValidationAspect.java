package com.mng.robotest.test80.arq.annotations.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class ValidationAspect {
	
	ChecksResult listResultValidations;
	
    @Pointcut("@annotation(Validation)")
    public void annotationValidationPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationValidationPointcut()")
    public void before(JoinPoint joinPoint) {}
    
    @AfterThrowing(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	InfoValidation infoValidation = InfoValidation.from(joinPoint);
    	listResultValidations = infoValidation.getListResultValidation();
    	listResultValidations.getDatosStep().setNOKstateByDefault();
    	listResultValidations.checkAndStoreValidations();
    }
    
    @AfterReturning(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	returning="resultMethod")
    public void grabValidationAfter(JoinPoint joinPoint, Object resultMethod) throws Throwable {
    	getDataFromReturning(joinPoint, resultMethod);
    	listResultValidations.checkAndStoreValidations();
    }
    
    private void getDataFromReturning(JoinPoint joinPoint, Object resultMethod) {
    	InfoValidation infoValidation = InfoValidation.from(joinPoint, resultMethod);
    	listResultValidations = infoValidation.getListResultValidation();
    }
    
	//TODO el datosStep se puede acabar eliminando pero de momento lo forzaremos como parámetro obligatorio sólo para probar
	//TODO al dFTest habría que ubicarlo en una clase de arquitectura en lugar del GestorWebDriver
}
