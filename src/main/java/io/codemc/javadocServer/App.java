package io.codemc.javadocServer;

public class App 
{

    public static void main(String[] args) throws Exception
    {
        WebServer server = new WebServer();
        server.addCachedJavadoc();
        server.getServer().join();
    }
}
