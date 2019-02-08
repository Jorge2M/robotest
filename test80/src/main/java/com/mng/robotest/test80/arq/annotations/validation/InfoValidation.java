package com.mng.robotest.test80.arq.annotations.validation;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

public class InfoValidation {

	private final JoinPoint joinPoint;
	private final Validation valAnnotation;
	private final Object resultMethod;
	private final ListResultValidation listResultValidations;
	private final DatosStep datosStep;
	
	private InfoValidation(JoinPoint joinPoint, Object resultMethod) {
		this.joinPoint = joinPoint;
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.resultMethod = resultMethod;
		this.listResultValidations = getValResultFromMethodData();
		this.datosStep = getDatosStepParam();
	}
	
	private InfoValidation(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
		this.valAnnotation = getValidationAnnotation(joinPoint);
		this.listResultValidations = getValResultFromMethodData();
		this.resultMethod = null;
		this.datosStep = getDatosStepParam();
	}
	
	public static InfoValidation from(JoinPoint joinPoint, Object resultMethod) {
		return (new InfoValidation(joinPoint, resultMethod));
	}
	
	public static InfoValidation from(JoinPoint joinPoint) {
		return (new InfoValidation(joinPoint));
	}
	
	public ListResultValidation getListResultValidation() {
		return listResultValidations;
	}
	
	public DatosStep getDatosStep() {
		return datosStep;
	}
	
    private DatosStep getDatosStepParam() {
    	Object[] signatureArgs = joinPoint.getArgs();
    	for (Object signatureArg: signatureArgs) {
    		if (signatureArg!=null && signatureArg instanceof DatosStep) {
    			return ((DatosStep)signatureArg);
    		}
    	}
    	
    	throw (new RuntimeException("A parameter of Type DatosStep is mandatory in method with @Validation annotation"));
    }
	
    private Validation getValidationAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Validation validationAnnotation = method.getAnnotation(Validation.class);
        return (validationAnnotation);
    }
	
	private ListResultValidation getValResultFromMethodData() {
		ListResultValidation valResult = getValidationResultFromObjectMethodReturn();
		modifyValidationResultAccordingAnnotationParams();
		return valResult;
	}
	
    private ListResultValidation getValidationResultFromObjectMethodReturn() {
    	ListResultValidation valResult = ListResultValidation.getNew(datosStep);
    	if (resultMethod!=null) {
	        if (resultMethod instanceof Boolean) {
	        	valResult.get(0).setOvercomed((Boolean)resultMethod);
	        }
	        
	        if (resultMethod instanceof ListResultValidation) {
	        	valResult = (ListResultValidation)resultMethod;
	        }
	        
	        throw (new RuntimeException("The return of a method marked with @Validation annotation must be of type boolean or ValidationResult"));
    	}
    	
    	return valResult;
    }
    
    private void modifyValidationResultAccordingAnnotationParams() {
    	if ("".compareTo(listResultValidations.get(0).getDescription())==0) {
    		String descripValidationMatched = getValidationDescriptionMatchingWithMethodParameters();
    		listResultValidations.get(0).setDescription(descripValidationMatched);
    	}
    	
    	if (listResultValidations.get(0).getLevelResult()==State.Undefined &&
    		valAnnotation.level()!=null) {
    		listResultValidations.get(0).setLevelResult(valAnnotation.level());
    	}
    }
    
    private String getValidationDescriptionMatchingWithMethodParameters() {
    	String descrToReturn  = valAnnotation.description();
    	Pattern p = Pattern.compile("\\#\\{([^}]*)\\}");
    	Matcher m = p.matcher(descrToReturn);
    	while (m.find()) {
    	  String group = m.group(1);
    	  String valueParameter = getStringValueParameterFromMethod(group);
    	  descrToReturn = descrToReturn.replace("#{" + group + "}", valueParameter);
    	  System.out.println(m.group(1));
    	}
    	
    	return (descrToReturn);
    }
    
    private String getStringValueParameterFromMethod(String paramNameInDescrValidation) {
    	Object[] signatureArgs = joinPoint.getArgs();
    	String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
    	for (int i=0; i<parameterNames.length; i++) {
    		if (parameterNames[i].compareTo(paramNameInDescrValidation)==0) {
    			return (signatureArgs[i].toString());
    		}
    	}
    	
    	return null;
    }
}