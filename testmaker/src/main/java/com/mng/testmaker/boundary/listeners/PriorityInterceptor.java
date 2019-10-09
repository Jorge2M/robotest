package com.mng.testmaker.boundary.listeners;

import java.util.*;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import com.mng.testmaker.utils.controlTest.FmwkTest;


public class PriorityInterceptor implements IMethodInterceptor {
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);

    //Reorganizamos el orden de los métodos en base a la prioridad definida desde la factoría (parámetro del test)
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods,ITestContext context) {
        List<IMethodInstance> result = new ArrayList<>();
        int array_index=0;
        for (IMethodInstance m : methods)
	        result.add(m);
        
        int posFinal=result.size()-1;
	    
        //Now iterate through each of these test methods
        for (IMethodInstance m : methods) {
            try {               
                Field f;
                try {
                    //Get the FIELD object from - Methodobj->method->class->field
                    f = m.getMethod().getRealClass().getField("prioridad");
                    
                    //Get the object instance of the method object              
                    array_index = f.getInt(m.getInstances()[0]);
                }
                catch (NoSuchFieldException e) {
                    //Si no tiene prioridad lo vamos situando al final (prioridad mínima)
                    array_index = posFinal;
                    posFinal-=1;
                }
            } 
            catch (Exception e) {
                pLogger.error("Problem managing Priority", e);
            }           

            result.set(array_index, m);           
        }
	
        return result;
    }
}
