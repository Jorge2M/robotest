package com.mng.testmaker.domain.testfilter;

import java.lang.reflect.Method;
import org.testng.annotations.Test;


public class TestMethod {
    Test annotationTest;
    Method method;
    
    public TestMethod(Test annotationTest, Method method) {
        this.annotationTest = annotationTest;
        this.method = method;
    }
    
    public Test getAnnotationTest() {
        return this.annotationTest;
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    public TestMethodData getData() {
    	TestMethodData testData = new TestMethodData();
    	testData.setTestCaseName(getMethod().getName());
    	testData.setTestCaseDescription(getAnnotationTest().description());
    	return testData;
    }  
}
