package com.mng.testmaker.listeners;

import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.*;


public class MyTransformer implements IAnnotationTransformer {
    
    @SuppressWarnings("rawtypes")
	@Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod!=null && "invoke".equals(testMethod.getName())) {
            annotation.setInvocationCount(5);
        }    
    }  
}


