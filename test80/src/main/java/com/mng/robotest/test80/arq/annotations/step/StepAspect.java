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
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;


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
    	DatosStep maxDatosStep = TestCaseData.getDatosStepForValidation();
    	if (maxDatosStep!=null) {
    		datosStep.setStepNumber(maxDatosStep.getStepNumber()+1);
    	}
    	
    	TestCaseData.storeInThread(datosStep);
    	return datosStep;
    }
    
    private void setInitDataStep(InfoStep infoStep, JoinPoint joinPoint, DatosStep datosStep) {
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
    	DatosStep datosStep = TestCaseData.pollDatosStepForStep();
    	setEndDataStep(datosStep);
    	storeStep(State.Nok, true, datosStep);
    }
    
    @AfterReturning(
    	pointcut="annotationStepPointcut() && atExecution()")
    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
    	DatosStep datosStep = TestCaseData.pollDatosStepForStep();
    	datosStep.setExcepExists(false);
    	storeDataAfterStep(datosStep);
    }
    
    public static void storeDataAfterStep(DatosStep datosStep) {
    	if (!datosStep.isStateUpdated() && !datosStep.getExcepExists()) {
    		storeStep(State.Ok, false, datosStep);
    	}
    	else {
    		fmwkTest.grabStep(datosStep, TestCaseData.getdFTest());
    	}
    }

    private void setEndDataStep(DatosStep datosStep) {
    	datosStep.setHoraFin(new Date(System.currentTimeMillis()));
    }
    
    private static void storeStep(State stateResult, boolean isException, DatosStep datosStep) {
    	updateDatosStep(datosStep, stateResult, isException);
        fmwkTest.grabStep(datosStep, TestCaseData.getdFTest());
    }
    
    private static void updateDatosStep(DatosStep datosStep, State stateResult, boolean isException) {
        datosStep.setExcepExists(isException); 
        datosStep.setResultSteps(stateResult);
    }
}
