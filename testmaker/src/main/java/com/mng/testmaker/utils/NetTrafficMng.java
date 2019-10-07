package com.mng.testmaker.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.utils.controlTest.fmwkTest;
import com.mng.testmaker.utils.controlTest.fmwkTest.TypeEvidencia;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

/**
 * NetTraffic Manager based into BrowserMobProxy Class.
 * Stored in each Thread -> 1 proxy for each TestNG Method (test case)
 * Project info: https://github.com/lightbody/browsermob-proxy
 *
 */
public class NetTrafficMng {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	static ThreadLocal<BrowserMobProxy> proxyInThread;
	static int initPort = 1000;
	static int maxSizeListPorts = 20;
	static LinkedList<Integer> listPortsAssigned; 
	final static String nameProxyInContext = "BrowserMobProxy";
	
	public NetTrafficMng() {
		if (proxyInThread==null) {
			proxyInThread = new ThreadLocal<>();
		}
			
		BrowserMobProxy proxy = getProxy();
		if (proxy==null || ((BrowserMobProxyServer)proxy).isStopped()) {
			//Creación del proxy
	        if (proxy==null) {
		        proxy = forceCreateProxy();
	        } else {
	        	int initPort = proxy.getPort();
		        proxy = new BrowserMobProxyServer();
		        proxy.setTrustAllServers(true);
	        	proxy.start(checkoutPort(initPort));
	        }
	        
			proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.REQUEST_HEADERS);
	        proxyInThread.set(proxy);
			pLogger.info("Created Proxy NetTraffic with port " + proxy.getPort());
		}
	}
	
	private BrowserMobProxy forceCreateProxy() {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		boolean started = false;
		int i=0;
		while (!started && i<5) {
			int port = 0;
			try {
		        proxy.setTrustAllServers(true);
				port = checkoutPort();
				proxy.start(port);
				started = true;
			}
			catch (RuntimeException e) {
				//NOTA: No debería (miramos de rotar los puertos), pero si en algún momento se produce el RuntimeException por un "java.net.BindException: Address already in use: bind"
				//entonces se produce un problema según el cuál todo funciona OK pero el Java no acaba. Como si se quedara algún Thread enganchado 
				pLogger.info(e.getClass() + " in start of proxy in port " + port + ". " + e.getMessage());
				port = checkoutPort();
				i+=1;
			}
		}
		
		return proxy;
	}
	
	public static BrowserMobProxy getProxy() {
		if (proxyInThread!=null) {
			return proxyInThread.get();
		}
		return null;
	}
	
	public static void destroyProxy() {
		if (proxyInThread!=null) {
			proxyInThread.remove();
		}
	}
	
	private int checkoutPort(int numPort) {
		for (int i=0; i<listPortsAssigned.size(); i++) {
			if (listPortsAssigned.get(i)==numPort) {
				listPortsAssigned.remove(i);
			}
		}
	
		listPortsAssigned.addLast(numPort);
		return numPort;
	}
	
	public int checkoutPort() {
		synchronized(NetTrafficMng.class) {
			if (listPortsAssigned==null) {
				listPortsAssigned = new LinkedList<>();
			}
			
			if (!listPortsAssigned.isEmpty()) {
				if (listPortsAssigned.size()>=maxSizeListPorts) {
					int port = listPortsAssigned.getFirst();
					listPortsAssigned.removeFirst();
					listPortsAssigned.addLast(port);
					return port;
				}
				
				int port = listPortsAssigned.getLast() + 1;
				listPortsAssigned.addLast(port);
				return port;
			}	
				
			int port = initPort;
			listPortsAssigned.addLast(port);
			return port;
		}
	}
	
	public void resetAndStartNetTraffic() {
		getProxy().newHar();
	}
	
	public static void stopNetTrafficThread() {
		BrowserMobProxy proxy = getProxy();
		if (proxy!=null) {
			if (!((BrowserMobProxyServer)proxy).isStopped()) {
				int port = proxy.getPort();
				proxy.stop();
				destroyProxy();
				pLogger.info("Stop Proxy NetTraffic with port " + port);
			}
		}
	}
	
	public void storeHarInFile(String nameFileHar) throws Exception {
        Har har = getProxy().getHar();
        File fileHar = new File(nameFileHar);
        har.writeTo(fileHar);
	}
	
	public void copyHarToHarp(String nameFileHar) throws Exception {
		File fileHar = new File(nameFileHar);
        File fileHarp = new File(nameFileHar.replace(fmwkTest.getSufijoEvidencia(TypeEvidencia.har), fmwkTest.getSufijoEvidencia(TypeEvidencia.harp)));
        InputStream inHar = new FileInputStream(fileHar);
        OutputStream outHarp = new FileOutputStream(fileHarp);
        outHarp.write("onInputData(".getBytes());
        try {
           IOUtils.copy(inHar, outHarp);
           outHarp.write(");".getBytes());
        }
        finally {
            IOUtils.closeQuietly(inHar);
            IOUtils.closeQuietly(outHarp);
        }
	}
}