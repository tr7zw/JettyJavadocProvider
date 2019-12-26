package io.codemc.javadocServer;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class WebServer {

    Server server = new Server(8080);

    HandlerCollection handlers = new HandlerCollection(true);
    
    public WebServer() throws Exception{
    	server.setHandler(handlers);
        server.start();
    }
    
    public Server getServer(){
    	return server;
    }
    
    public void addCachedJavadoc() throws Exception{
    	File cacheDir = new File("repo");
    	searchJavadoc(cacheDir);
    }
    
    private void searchJavadoc(File folder) throws Exception{
    	for(File f : folder.listFiles()){
    		if(f.isFile() && f.getName().endsWith(".jar")){
    			addJavadoc(f);
    			System.out.println("Added cached Javadoc '" + f.getName() + "'");
    		}else{
    			searchJavadoc(f);
    		}
    	}
    }
    
    public void addJavadoc(File jarFile) throws Exception{
    	 ContextHandler context = new ContextHandler();
         ResourceHandler resource_handler = new ResourceHandler();
        
         File version = jarFile.getParentFile();
         File name = version.getParentFile();
         File org = name.getParentFile();
         context.setContextPath("/" + org.getName() + "/" + name.getName() + "/" + version.getName() + "/");
         context.setHandler(resource_handler);
         resource_handler.setWelcomeFiles(new String[]{ "index.html" });
         resource_handler.setResourceBase("jar:file:/" + jarFile.getAbsolutePath() + "!/");
         
         handlers.addHandler(context);
         resource_handler.start();
         context.start();
    }
	
}
