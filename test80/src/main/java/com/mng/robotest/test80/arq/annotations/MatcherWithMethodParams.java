package com.mng.robotest.test80.arq.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class MatcherWithMethodParams {
	
	private final JoinPoint joinPoint;
	
	private MatcherWithMethodParams(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}
	
	public static MatcherWithMethodParams from(JoinPoint joinPoint) {
		return (new MatcherWithMethodParams(joinPoint));
	}
	
    public String match(String litWithTags) {
    	String litToReturn = "";
    	Pattern p = Pattern.compile("\\#\\{([^}]*)\\}");
    	Matcher m = p.matcher(litWithTags);
    	while (m.find()) {
    	  String group = m.group(1);
    	  String valueParameter = getStringValueParameterFromMethod(group);
    	  litToReturn = litWithTags.replace("#{" + group + "}", valueParameter);
    	}
    	
    	return (litToReturn);
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
