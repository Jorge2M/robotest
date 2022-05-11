package com.mng.robotest.test.utils;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.generic.stackTrace;

public class WebDriverMngUtils {
	
	/**
	 * Cargamos el errorPage y de allí extraemos el nodo
	 */
	public static String getNodeFromErrorPage(final WebDriver driver) throws Exception {

		//Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella 
		String windowHandle = ErrorStorer.loadErrorPage(driver);
		String nodo = "";
		
		try {
			nodo = driver.findElement(By.xpath("//h2[text()[contains(.,'IP :')]]/following::p")).getText();
		} catch (Exception e) {
			throw e;
		} finally {
			// Cerramos la pestaña
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.close('" + Thread.currentThread().getName() + "');");

			// Restauramos la pantalla a la que apunta webdriver
			driver.switchTo().window(windowHandle);
		}
		
		return nodo;
	}

	public static stackTrace stackTaceException(WebDriver driver, ITestContext context) throws Exception {
		stackTrace resExcep = new stackTrace();
		resExcep.setException("");
		resExcep.setExiste(false);
		resExcep.setRepetida(false);
		resExcep.setNumExcepciones(0);
		String stackTrace = getStackTraceFromErrorPage(driver);
		resExcep.setException(stackTrace);
		if (stackTrace.trim().compareTo("")!=0) {
			resExcep.setExiste(true);
			int numExcepciones = 0;
			if (context.getAttribute(stackTrace) != null) {
				numExcepciones = (Integer)context.getAttribute(stackTrace);
				resExcep.setRepetida(true);
			}
			numExcepciones+=1;
			context.setAttribute(stackTrace, Integer.valueOf(numExcepciones));
			resExcep.setNumExcepciones(numExcepciones);
		}
			
		return resExcep;
	}	
	
	/**
	 * Cargamos el errorPage y de allí extraemos el Stack Trace
	 */
	public static String getStackTraceFromErrorPage(final WebDriver driver) throws Exception {
		//Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella 
		String windowHandle = ErrorStorer.loadErrorPage(driver);
		String stackTrace = "";
		
		try {
			stackTrace = driver.findElement(By.xpath("//h2[text()[contains(.,'Stack Trace :')]]/following::p")).getText();
		} catch (Exception e) {
			throw e;
		} finally {
			// Cerramos la pestaña
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.close('" + Thread.currentThread().getName() + "');");

			// Restauramos la pantalla a la que apunta webdriver
			driver.switchTo().window(windowHandle);
		}
		
		return stackTrace;
	}
   

	

}
