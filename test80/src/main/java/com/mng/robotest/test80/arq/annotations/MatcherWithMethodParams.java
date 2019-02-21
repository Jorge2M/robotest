package com.mng.robotest.test80.arq.annotations;

import java.lang.reflect.Method;
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
    	String litToReturn = litWithTags;
    	Pattern p = Pattern.compile("\\#\\{([^}]*)\\}");
    	Matcher m = p.matcher(litWithTags);
    	while (m.find()) {
    	  String group = m.group(1);
    	  String valueParameter = getStringValueParameterFromMethod(group);
    	  if (valueParameter==null) {
    		  valueParameter="null";
    	  }
    	  litToReturn = litToReturn.replace("#{" + group + "}", valueParameter);
    	}
    	
    	return (litToReturn);
    }
    
    private String getStringValueParameterFromMethod(String paramNameInDescrValidation) {
    	try {
    		TagData tagData = new TagData(paramNameInDescrValidation);
	    	Object[] signatureArgs = joinPoint.getArgs();
	    	String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
	    	for (int i=0; i<parameterNames.length; i++) {
	    		if (parameterNames[i].compareTo(tagData.nameParameter)==0) {
	    			return (getStringFromParameter(tagData, signatureArgs[i]));
	    		}
	    	}
    	}
    	catch (Exception e) {
    		return "null";
    	}
    	
    	return paramNameInDescrValidation;
    }
    
    private String getStringFromParameter(TagData tagData, Object parameter) throws Exception {
		if ("".compareTo(tagData.methodWithoutParams)!=0) {
			for (Method methodFromParam : parameter.getClass().getMethods()) {
				if (tagData.methodWithoutParams.compareTo(methodFromParam.getName())==0) {
					return (methodFromParam.invoke(parameter).toString());
				}
			}
		}
	
		return (parameter.toString());
    }
    
    /**
     * Obtiene los datos de un String del tipo "parametroX.methodY()";
     *
     */
    class TagData {
        public String nameParameter = "";
        public String methodWithoutParams = "";
        
        private TagData(String varAndMethod) {
        	Pattern p = Pattern.compile("(.*)\\.(.*)\\(\\)");
        	Matcher m = p.matcher(varAndMethod);
        	if (m.find()) {
          	  nameParameter = m.group(1);
          	  methodWithoutParams = m.group(2).replace(")","").replace("(","");
          	}
        	else {
        		nameParameter = varAndMethod;
        	}
        }
    }
}
