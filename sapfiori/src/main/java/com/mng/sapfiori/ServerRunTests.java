package com.mng.sapfiori;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

public class ServerRunTests {
	
	public static void main(String[] args) throws Exception {
		Server jettyServer = new Server(8888);
		jettyServer.setHandler(getJerseyHandler());
		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
	
	private static Handler getJerseyHandler() {
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setHandler(getHandlers());
		ResourceConfig config = new ResourceConfig();
		config.packages("com.mng.sapfiori.rest");
		config.register(LoggingFeature.class);
		config.addProperties(getProperties());
		servletHandler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");
		return servletHandler;
	}
	
	private static HandlerWrapper getHandlers() {
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resource_handler.setResourceBase(".");
		return resource_handler;
	}
	
	private static Map<String, Object> getProperties() {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
		properties.put("com.sun.jersey.config.feature.Debug", Boolean.TRUE);
		properties.put(ServerProperties.TRACING, "ALL");
		properties.put(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
		return properties;
	}
}
