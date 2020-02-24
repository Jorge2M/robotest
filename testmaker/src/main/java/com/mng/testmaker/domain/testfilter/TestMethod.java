package com.mng.testmaker.domain.testfilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestMethod {
	Annotation annotation;
    Method method;
    
    public TestMethod(Test annotationTest, Method method) {
        this.annotation = annotationTest;
        this.method = method;
    }
    public TestMethod(Factory annotationFactory, Method method) {
        this.annotation = annotationFactory;
        this.method = method;
    }
    
    public String getDescription() {
    	if (annotation instanceof Test) {
    		return ((Test)annotation).description();
    	} else {
    		return ((Factory)annotation).description();
    	}
    }
    public String[] getGroups() {
    	if (annotation instanceof Test) {
    		return ((Test)annotation).groups();
    	} else {
    		return ((Factory)annotation).groups();
    	}
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    public TestMethodData getData() {
    	TestMethodData testData = new TestMethodData();
    	testData.setTestCaseName(getMethod().getName());
    	testData.setTestCaseDescription(getDescription());
    	return testData;
    }  
}
