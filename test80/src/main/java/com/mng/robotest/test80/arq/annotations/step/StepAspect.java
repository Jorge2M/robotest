package com.mng.robotest.test80.arq.annotations.step;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.robotest.test80.arq.annotations.validation.InfoValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

@SuppressWarnings("javadoc")
@Aspect
public class StepAspect {
	
    @Pointcut("@annotation(Step)")
    public void annotationStepPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationStepPointcut()")
    public void before(JoinPoint joinPoint) {}
    
    @AfterThrowing(
    	pointcut="annotationStepPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
//    	InfoValidation infoValidation = InfoValidation.from(joinPoint);
//    	listResultValidations = infoValidation.getListResultValidation();
//    	listResultValidations.getDatosStep().setNOKstateByDefault();
//    	listResultValidations.checkAndStoreValidations();
    }
    
    @AfterReturning(
    	pointcut="annotationStepPointcut() && atExecution()")
    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
    	InfoStep infoStep = InfoStep.from(joinPoint);

    	//listResultValidations.checkAndStoreValidations();
    }
}
