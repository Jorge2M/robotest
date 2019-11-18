package com.mng.sapfiori;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.mng.sapfiori.rest.RestTestMaker;

public class ServerRunTests {
	
	public static void main(String[] args) throws Exception {
		Server jettyServer = new Server(8888);
		jettyServer.setHandler(getJerseyHandler(RestTestMaker.class));
		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}

	private static Handler getJerseyHandler(Class<?> classToLoad) {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		handler.setContextPath("/");
		ServletHolder servlet = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		servlet.setInitOrder(0);
        servlet.setInitParameter(
        		"jersey.config.server.provider.packages", 
        		"com.mng.sapfiori.rest");
//		servlet.setInitParameter(
//				"jersey.config.server.provider.classnames",
//				classToLoad.getCanonicalName());
		return handler;
	}
}
