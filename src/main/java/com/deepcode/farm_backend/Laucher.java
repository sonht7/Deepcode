package com.deepcode.farm_backend;

import javax.ws.rs.ext.RuntimeDelegate;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;

public class Laucher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.register(MyResource.class);
		resourceConfig.register(CategoryResource.class);
		resourceConfig.register(ProductGroupResource.class);
		resourceConfig.register(FarmResource.class);
		
		HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(resourceConfig, HttpHandler.class);
		HttpServer server = HttpServer.createSimpleServer(null, 8089);
		server.getServerConfiguration().addHttpHandler(handler);
		try {
			server.start();
			System.out.println("Press any key to stop the server...");
			System.in.read();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
