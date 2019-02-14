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

    @Pointcut("@annotation(Step)")
    public void annotationStepPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationStepPointcut() && atExecution()")
    public void before(JoinPoint joinPoint) {
    	InfoStep infoStep = InfoStep.from(joinPoint);
    	DatosStep datosStep = getFromJoinPointAndStoreDatosStep(joinPoint, infoStep);
    	setInitDataStep(infoStep, joinPoint, datosStep);
    }
    
    private DatosStep getFromJoinPointAndStoreDatosStep(JoinPoint joinPoint, InfoStep infoStep) {
    	DatosStep datosStep = infoStep.getDatosStep();
    	DatosStep stepAnterior = ThreadData.peekDatosStep();
    	if (stepAnterior!=null) {
    		datosStep.setStepNumber(stepAnterior.getStepNumber()+1);
    	}
    	
    	ThreadData.storeInThread(datosStep);
    	return datosStep;
    }
    
    @AfterThrowing(
    	pointcut="annotationStepPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	DatosStep datosStep = ThreadData.pollDatosStep();
    	setEndDataStep(datosStep);
    	storeStep(State.Nok, true, datosStep);
    }
    
    @AfterReturning(
    	pointcut="annotationStepPointcut() && atExecution()")
    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
    	DatosStep datosStep = ThreadData.pollDatosStep();
    	storeStep(State.Ok, false, datosStep);
    }
    
    private void setInitDataStep(InfoStep infoStep, JoinPoint joinPoint, DatosStep datosStep) {
        MatcherWithMethodParams matcher = MatcherWithMethodParams.from(joinPoint);
        String stepDescription = infoStep.getStepAnnotation().description();
        String stepResExpected = infoStep.getStepAnnotation().expected();
        datosStep.setDescripcion(matcher.match(stepDescription)); 
        datosStep.setResExpected(matcher.match(stepResExpected));
        datosStep.setHoraInicio(new Date(System.currentTimeMillis()));
    }
    
    private void setEndDataStep(DatosStep datosStep) {
    	datosStep.setHoraFin(new Date(System.currentTimeMillis()));
    }
    
    private void storeStep(State stateResult, boolean isException, DatosStep datosStep) {
    	updateDatosStep(datosStep, stateResult, isException);
        int numStep = fmwkTest.grabStep(datosStep, ThreadData.getdFTest());
        datosStep.setStepNumber(numStep);
    }
    
    private void updateDatosStep(DatosStep datosStep, State stateResult, boolean isException) {
        datosStep.setExcepExists(isException); 
        datosStep.setResultSteps(stateResult);
    }
}
