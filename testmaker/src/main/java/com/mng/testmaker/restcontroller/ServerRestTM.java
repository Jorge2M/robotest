package com.mng.testmaker.restcontroller;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import com.mng.testmaker.domain.CreatorSuiteRun;

//For renewal robotest.pro.mango.com certificate manually:
//	-> Execute Powershell with Administrator Privileges
//	-> Path C:\\users\jorge.muñoz\Programas\win-acme
//	-> Exec .\wacs.exe --force
//  -> Option "S Renew specific"
//	-> Certificate in C:\ProgramData\win-acme\acme-v02.api.letsencrypt.org\Certificates
//[INFO] Adding renewal for [Manual] robotest.pro.mango.com
//[INFO] Next renewal scheduled at 02/04/2020 10:21:39

//Probably the automation of the renewal is not possible because is utilized --manual method with TXT autentication (dns01)
//(https://community.letsencrypt.org/t/automate-renewal-for-lets-encrypt-certificate/73972/10)
//Also, the client wacs.exe is not instaled in the windows machine because a lack of .dll
//If we want to automate the certificate renewal perhaps I must install .\wacs.exe in the Windows machine and choose the http01 autentication

public class ServerRestTM {
	
	private static ServerRestTM serverRestTM = null; 
	
	private final int httpPort;
	private final int httpsPort;
	private final Server jettyServer;
	private final HttpConfiguration httpConfiguration;
	private final CreatorSuiteRun creatorSuiteRun;
	private final Class<? extends RestApiTM> restApiTM;
	private final Class<? extends Enum<?>> suiteEnum;
	private final Class<? extends Enum<?>> appEnum;

	private ServerRestTM(int httpPort, int httpsPort, CreatorSuiteRun creatorSuiteRun, 
						 Class<? extends RestApiTM> restApiTM, 
						 Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) throws Exception {
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		this.creatorSuiteRun = creatorSuiteRun;
		this.restApiTM = restApiTM;
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
		jettyServer = new Server();
		httpConfiguration = new HttpConfiguration();
	}
	
	public static ServerRestTM getInstance(int httpPort, int httpsPort, CreatorSuiteRun creatorSuiteRun, 
			   							   Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) throws Exception {
		return getInstance(httpPort, httpsPort, creatorSuiteRun, RestApiTM.class, suiteEnum, appEnum);
	}
	
	public static ServerRestTM getInstance(int httpPort, int httpsPort, CreatorSuiteRun creatorSuiteRun, 
										   Class<? extends RestApiTM> restApiTM, 
										   Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) 
	throws Exception {
		if (serverRestTM==null) {
			serverRestTM = new ServerRestTM(httpPort, httpsPort, creatorSuiteRun, restApiTM, suiteEnum, appEnum);
		}
		if (serverRestTM.getHttpPort()!=httpPort || serverRestTM.getHttpsPort()!=httpsPort) {
			serverRestTM.getJettyServer().stop();
			serverRestTM.getJettyServer().destroy();
			serverRestTM = new ServerRestTM(httpPort, httpsPort, creatorSuiteRun, restApiTM, suiteEnum, appEnum);
		}
		return serverRestTM;
	}
	
	public static ServerRestTM getServerRestTM() {
		return serverRestTM;
	}
	
	public int getHttpPort() {
		return httpPort;
	}
	public int getHttpsPort() {
		return httpsPort;
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
		setServerHttpConnector();
		setServerJerseyHandler();
		setServerHttpsConnector();
		try {
			jettyServer.start();
			System.out.println("Started Jetty Server at http/https ports: " + httpPort + "/" + httpsPort);
			jettyServer.join();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jettyServer.destroy();
		}
	}
	
	private void setServerHttpConnector() {
		httpConfiguration.setSecureScheme("https");
		httpConfiguration.setSecurePort(httpsPort);
		ServerConnector http = new ServerConnector(jettyServer, new HttpConnectionFactory(httpConfiguration));
		http.setPort(httpPort);
		jettyServer.addConnector(http);
	}
	private void setServerHttpsConnector() {
		SslContextFactory sslContextFactory = new SslContextFactory(ServerRestTM.class.getResource("/EiLuNHJWNEmFjcsNj3YRig-cache.pfx").toExternalForm());
		sslContextFactory.setKeyStorePassword("yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=");
		HttpConfiguration httpsConfiguration = new HttpConfiguration(httpConfiguration);
		httpsConfiguration.addCustomizer(new SecureRequestCustomizer());
		ServerConnector httpsConnector = new ServerConnector(jettyServer,
			new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
			new HttpConnectionFactory(httpsConfiguration));
		httpsConnector.setPort(httpsPort);
		jettyServer.addConnector(httpsConnector);
	}
	
	private void setServerJerseyHandler() {
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setHandler(getHandlers());
		ResourceConfig config = new ResourceConfig();
		config.register(restApiTM);
		config.register(LoggingFeature.class);
		servletHandler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");
		jettyServer.setHandler(servletHandler);
	}
	
	private HandlerWrapper getHandlers() {
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resource_handler.setResourceBase(".");
		return resource_handler;
	}
}
