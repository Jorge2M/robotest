package com.mng.testmaker.restcontroller;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.mng.testmaker.domain.CreatorSuiteRun;

public class ServerRestTM {
	
	private static ServerRestTM serverRestTM = null; 
	
	private final int port;
	private final Server jettyServer;
	private final CreatorSuiteRun creatorSuiteRun;
	private final Class<? extends RestApiTM> restApiTM;
	private final Class<? extends Enum<?>> suiteEnum;
	private final Class<? extends Enum<?>> appEnum;

	private ServerRestTM(int port, CreatorSuiteRun creatorSuiteRun, 
						 Class<? extends RestApiTM> restApiTM, 
						 Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) throws Exception {
		this.port = port;
		this.creatorSuiteRun = creatorSuiteRun;
		this.restApiTM = restApiTM;
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
		jettyServer = new Server(port);
	}
	
	public static ServerRestTM getInstance(int port, CreatorSuiteRun creatorSuiteRun, 
			   							   Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) throws Exception {
		return getInstance(port, creatorSuiteRun, RestApiTM.class, suiteEnum, appEnum);
	}
	
	public static ServerRestTM getInstance(int port, CreatorSuiteRun creatorSuiteRun, 
										   Class<? extends RestApiTM> restApiTM, 
										   Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) 
	throws Exception {
		if (serverRestTM==null) {
			serverRestTM = new ServerRestTM(port, creatorSuiteRun, restApiTM, suiteEnum, appEnum);
		}
		if (serverRestTM.getPort()!=port) {
			serverRestTM.getJettyServer().stop();
			serverRestTM.getJettyServer().destroy();
			serverRestTM = new ServerRestTM(port, creatorSuiteRun, restApiTM, suiteEnum, appEnum);
		}
		return serverRestTM;
	}
	
	public static ServerRestTM getServerRestTM() {
		return serverRestTM;
	}
	
	public int getPort() {
		return port;
	}
	public CreatorSuiteRun getCreatorSuiteRun() {
		return creatorSuiteRun;
	}
	public Class<? extends Enum<?>> getSuiteEnum() {
		return suiteEnum;
	}
	public Class<? extends Enum<?>> getAppEnum() {
		return appEnum;
	}
	public Server getJettyServer() {
		return this.jettyServer;
	}
	
	public void start() throws Exception {
		jettyServer.setHandler(getJerseyHandler());
		try {
			jettyServer.start();
			System.out.println("Started Jetty Server at port " + port);
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
	
	private Handler getJerseyHandler() {
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setHandler(getHandlers());
		ResourceConfig config = new ResourceConfig();
		//config.packages(RestApiTM.class.getPackage().getName());
		config.register(restApiTM);
		config.register(LoggingFeature.class);
		servletHandler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");
		return servletHandler;
	}
	
	private HandlerWrapper getHandlers() {
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resource_handler.setResourceBase(".");
		return resource_handler;
	}
}
