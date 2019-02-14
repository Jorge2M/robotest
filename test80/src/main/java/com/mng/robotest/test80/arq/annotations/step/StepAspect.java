package com.mng.robotest.test80.arq.annotations.step;

import java.sql.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.robotest.test80.arq.annotations.MatcherWithMethodParams;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.ThreadData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;

@SuppressWarnings("javadoc")
@Aspect
public class StepAspect {
	
	JoinPoint joinPoint;
	InfoStep infoStep;
	DatosStep datosStep;
	
    @Pointcut("@annotation(Step)")
    public void annotationStepPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationStepPointcut() && atExecution()")
    public void before(JoinPoint joinPoint) {
    	setInstanceData(joinPoint);
    	ThreadData.storeInThread(datosStep);
    	setInitDataStep();
    }
    
    @AfterThrowing(
    	pointcut="annotationStepPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	setEndDataStep();
    	storeStep(State.Nok, true);
    }
    
    @AfterReturning(
    	pointcut="annotationStepPointcut() && atExecution()")
    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
    	storeStep(State.Ok, false);
    }
    
    private void setInstanceData(JoinPoint joinPoint) {
    	this.joinPoint = joinPoint;
    	infoStep = InfoStep.from(joinPoint);
    	datosStep = infoStep.getDatosStep();
    	DatosStep stepAnterior = ThreadData.getDatosStep();
    	if (stepAnterior!=null) {
    		datosStep.setStepNumber(stepAnterior.getStepNumber()+1);
    	}
    }
    
    private void setInitDataStep() {
        MatcherWithMethodParams matcher = MatcherWithMethodParams.from(joinPoint);
        String stepDescription = infoStep.getStepAnnotation().description();
        String stepResExpected = infoStep.getStepAnnotation().expected();
        datosStep.setDescripcion(matcher.match(stepDescription)); 
        datosStep.setResExpected(matcher.match(stepResExpected));
        datosStep.setHoraInicio(new Date(System.currentTimeMillis()));
    }
    
    private void setEndDataStep() {
    	datosStep.setHoraFin(new Date(System.currentTimeMillis()));
    }
    
    private void storeStep(State stateResult, boolean isException) {
    	updateDatosStep(datosStep, stateResult, isException);
        int numStep = fmwkTest.grabStep(datosStep, ThreadData.getdFTest());
        datosStep.setStepNumber(numStep);
    }
    
    private void updateDatosStep(DatosStep datosStep, State stateResult, boolean isException) {
        datosStep.setExcepExists(isException); 
        datosStep.setResultSteps(stateResult);
    }
}
