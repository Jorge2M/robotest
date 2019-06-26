package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.Registro;

import org.testng.ITestContext;

public class ListRegistrosEspanyaMasivos {
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"AppEcom", "countrys", "lineas"})
    public Object[] createInstances(String appEStr, String listaPaisesStr, String lineas, ITestContext context) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        AppEcom appE = AppEcom.valueOf(appEStr);
        try {
        	for (int i=0; i<100; i++) {
	            listTests.add(new Registro(i));
	            System.out.println(
	                "Creado Test #" + i
	            );
        	}
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    
        return (listTests.toArray(new Object[listTests.size()]));
    }	
}
