package com.mng.robotest.test80.arq.listeners;

import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.*;

@SuppressWarnings("javadoc")
public class MyTransformer implements IAnnotationTransformer {
    
    @SuppressWarnings("rawtypes")
	@Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod!=null && "invoke".equals(testMethod.getName())) {
            annotation.setInvocationCount(5);
        }    
    }  
}


