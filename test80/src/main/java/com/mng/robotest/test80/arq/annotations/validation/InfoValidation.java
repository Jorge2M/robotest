package com.mng.robotest.test80.arq.annotations.validation;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mng.robotest.test80.arq.annotations.MatcherWithMethodParams;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

public class InfoValidation {

	private final JoinPoint joinPoint;
	private final Validation valAnnotation;
	private final Object resultMethod;
	private final ChecksResult listResultValidations;
	
	private InfoValidation(JoinPoint joinPoint, Object resultMethod) {
		this.joinPoint = joinPoint;
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.resultMethod = resultMethod;
		this.listResultValidations = getValResultFromMethodData();
	}
	
	private InfoValidation(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
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
	
    private DatosStep getDatosStepParam() {
    	Object[] signatureArgs = joinPoint.getArgs();
    	for (Object signatureArg: signatureArgs) {
    		if (signatureArg!=null && signatureArg instanceof DatosStep) {
    			return ((DatosStep)signatureArg);
    		}
    	}
    	
    	if (TestCaseData.getDatosLastStep()!=null) {
    		return (TestCaseData.getDatosLastStep());
    	}
    	
    	throw (new RuntimeException("A parameter of Type DatosStep is mandatory in method with @Validation annotation"));
    }
	
    private Validation getValidationAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Validation validationAnnotation = method.getAnnotation(Validation.class);
        return (validationAnnotation);
    }
	
	private ChecksResult getValResultFromMethodData() {
		ChecksResult valResult = getValidationResultFromObjectMethodReturn();
		modifyValidationResultAccordingAnnotationParams(valResult);
		return valResult;
	}
	
    private ChecksResult getValidationResultFromObjectMethodReturn() {
    	DatosStep datosStep = getDatosStepParam();
    	ChecksResult valResult = ChecksResult.getNew(datosStep);
    	if (resultMethod!=null) {
	        if (resultMethod instanceof Boolean) {
	        	ResultValidation validation = new ResultValidation(1);
	        	validation.setOvercomed((Boolean)resultMethod);
	        	valResult.add(validation);
	        	return valResult;
	        }
	        
	        if (resultMethod instanceof ChecksResult) {
	        	valResult = (ChecksResult)resultMethod;
	        	return valResult;
	        }
	        
	        throw (new RuntimeException("The return of a method marked with @Validation annotation must be of type boolean or ValidationResult"));
    	}
    	else {
    		valResult.add(new ResultValidation(1));
    		return valResult;
    	}
    }
    
    private void modifyValidationResultAccordingAnnotationParams(ChecksResult valResult) {
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
    	}
    }
    

}