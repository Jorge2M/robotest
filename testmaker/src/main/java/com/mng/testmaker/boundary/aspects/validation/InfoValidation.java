package com.mng.testmaker.boundary.aspects.validation;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mng.testmaker.boundary.aspects.MatcherWithMethodParams;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.TestCaseTM;

public class InfoValidation {

	private final JoinPoint joinPoint;
	private final MethodSignature methodSignature;
	private final Validation valAnnotation;
	private final Object resultMethod;
	private final ChecksResult listResultValidations;
	
	private InfoValidation(JoinPoint joinPoint, Object resultMethod) {
		this.joinPoint = joinPoint;
		this.methodSignature = (MethodSignature) joinPoint.getSignature();
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.resultMethod = resultMethod;
		this.listResultValidations = getValResultFromMethodData();
	}
	
	private InfoValidation(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
		this.methodSignature = (MethodSignature) joinPoint.getSignature();
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.listResultValidations = getValResultFromMethodData();
		this.resultMethod = null;
	}
	
	public static InfoValidation from(JoinPoint joinPoint, Object resultMethod) {
		return (new InfoValidation(joinPoint, resultMethod));
	}
	
	public static InfoValidation from(JoinPoint joinPoint) {
		return (new InfoValidation(joinPoint));
	}
	
	public ChecksResult getListResultValidation() {
		return listResultValidations;
	}
	
    private Validation getValidationAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Validation validationAnnotation = method.getAnnotation(Validation.class);
        return (validationAnnotation);
    }
	
	private ChecksResult getValResultFromMethodData() {
		ChecksResult valResult = getValidationResultFromObjectMethodReturn();
		valResult.setPathMethod(methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());
		modifyValidationResultAccordingAnnotationParams(valResult);
		return valResult;
	}
	
    private ChecksResult getValidationResultFromObjectMethodReturn() {
    	TestCaseTM testCaseInThread = TestCaseTM.getTestCaseInExecution();
    	StepTM step = testCaseInThread.getLastStep();
    	ChecksResult valResult = ChecksResult.getNew(step);
    	if (resultMethod!=null) {
    		//One Validation
	        if (resultMethod instanceof Boolean) {
	        	ResultValidation validation = new ResultValidation(1);
	        	validation.setOvercomed((Boolean)resultMethod);
	        	valResult.add(validation);
	        	return valResult;
	        }
	        //Many Validations
	        if (resultMethod instanceof ChecksResult) {
	        	valResult = (ChecksResult)resultMethod;
	        	return valResult;
	        }
	        
	        throw (new RuntimeException(
	        	"The return of a method marked with @Validation annotation must be of type boolean or " + 
	        	ChecksResult.class.getName()));
    	} else {
    		valResult.add(new ResultValidation(1));
    		return valResult;
    	}
    }

    private void modifyValidationResultAccordingAnnotationParams(ChecksResult valResult) {
    	//Only exists annotations in the "One Validation" case
    	if (valResult.size()>0) {
	    	if ("".compareTo(valResult.get(0).getDescription())==0) {
	    		MatcherWithMethodParams matcher = MatcherWithMethodParams.from(joinPoint);
	    		String descripValidationMatched = matcher.match(valAnnotation.description());
	    		valResult.get(0).setDescription(descripValidationMatched);
	    	}
	    	
	    	if (valResult.get(0).getLevelResult()==State.Undefined &&
	    		valAnnotation.level()!=null) {
	    		valResult.get(0).setLevelResult(valAnnotation.level());
	    	}
	    	
	    	if (valAnnotation.avoidEvidences()) {
	    		valResult.get(0).setAvoidEvidences(true);
	    	}
    	}
    }
}