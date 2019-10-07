package com.mng.testmaker.annotations.step;

import java.sql.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.testmaker.annotations.MatcherWithMethodParams;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.utils.controlTest.FmwkTest;


@Aspect
public class StepAspect {

    @Pointcut("@annotation(Step)")
    public void annotationStepPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationStepPointcut() && atExecution()")
    public void before(JoinPoint joinPoint) {
    	InfoStep infoStep = InfoStep.from(joinPoint);
    	StepTestMaker datosStep = getFromJoinPointAndStoreDatosStep(joinPoint, infoStep);
    	setInitDataStep(infoStep, joinPoint, datosStep);
    }
    
    private StepTestMaker getFromJoinPointAndStoreDatosStep(JoinPoint joinPoint, InfoStep infoStep) {
    	StepTestMaker datosStep = infoStep.getDatosStep();
    	StepTestMaker maxDatosStep = TestCaseData.getDatosLastStep();
    	if (maxDatosStep!=null) {
    		datosStep.setStepNumber(maxDatosStep.getStepNumber()+1);
    	}
    	
    	TestCaseData.storeInThread(datosStep);
    	return datosStep;
    }
    
    private void setInitDataStep(InfoStep infoStep, JoinPoint joinPoint, StepTestMaker datosStep) {
        MatcherWithMethodParams matcher = MatcherWithMethodParams.from(joinPoint);
        String stepDescription = infoStep.getStepAnnotation().description();
        String stepResExpected = infoStep.getStepAnnotation().expected();
        datosStep.setDescripcion(matcher.match(stepDescription)); 
        datosStep.setResExpected(matcher.match(stepResExpected));
        datosStep.setHoraInicio(new Date(System.currentTimeMillis()));
    }
    
    @AfterThrowing(
    	pointcut="annotationStepPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	StepTestMaker datosStep = TestCaseData.pollDatosStepForStep();
    	setEndDataStep(datosStep);
    	storeStep(State.Nok, true, datosStep);
    }
    
    @AfterReturning(
    	pointcut="annotationStepPointcut() && atExecution()")
    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
    	StepTestMaker datosStep = TestCaseData.pollDatosStepForStep();
    	datosStep.setExcepExists(false);
    	storeDataAfterStep(datosStep);
    }
    
    public static void storeDataAfterStep(StepTestMaker datosStep) {
    	if (!datosStep.isStateUpdated() && !datosStep.getExcepExists()) {
    		storeStep(State.Ok, false, datosStep);
    	} else {
    		FmwkTest.grabStep(datosStep, TestCaseData.getdFTest());
    	}
    }

    private void setEndDataStep(StepTestMaker datosStep) {
    	datosStep.setHoraFin(new Date(System.currentTimeMillis()));
    }
    
    private static void storeStep(State stateResult, boolean isException, StepTestMaker datosStep) {
    	updateDatosStep(datosStep, stateResult, isException);
        FmwkTest.grabStep(datosStep, TestCaseData.getdFTest());
    }
    
    private static void updateDatosStep(StepTestMaker datosStep, State stateResult, boolean isException) {
        datosStep.setExcepExists(isException); 
        datosStep.setResultSteps(stateResult);
    }
}
