package com.JavaWebServer.app;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class App {
  private static final int PORT_INDEX = 0;
  private static final int DIR_INDEX = 1;
  private static final String [] KEYS = {"-p","-d"};
  
  private static String loggerPath = System.getProperty("user.dir")+"logs";
  private static  StatusCodes status = new StatusCodes ();
  private static String serverName = "http://localhost:";
  
  public static HashMap routeDirectory(){
    HashMap<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    routes.put("/file1",routeMethods(new String [] {"GET", "POST"}));
    routes.put("/",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/text-file.txt",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/form",routeMethods(new String [] {"POST","PUT"}));
    routes.put("/method_options",routeMethods(new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    routes.put("/redirect",routeMethods(new String [] {"GET"}));
    routes.put("/image.jpeg",routeMethods(new String[] {"GET"}));
    routes.put("/image.gif",routeMethods(new String[] {"GET"}));
    routes.put("/image.png",routeMethods(new String[] {"GET"}));
    routes.put("/file1",routeMethods(new String[] {"GET"}));
    routes.put("/file2",routeMethods(new String[] {"GET"}));
    routes.put("/parameters?",routeMethods(new String[] {"GET"}));
    routes.put("/form",routeMethods(new String[] {"GET","POST","PUT","DELETE"}));
    routes.put("/patch-content.txt", routeMethods(new String [] {"PATCH", "GET"}));
    routes.put("/logs", routeMethods(new String[] {"GET"}));
    routes.put("/partial_content.txt", routeMethods(new String[] {"GET"}));
    return routes;
  }

  public static HashMap getRoutes (String directory, int port) {
    boolean unprotected = false;
    boolean protectedRoute = true;
    
    HashMap<String, RestMethod> routes = new HashMap<String,RestMethod>();
    routes.put("POST /file1",new PutPost(status.OK,"/file1",directory,new FileEditor()));
    routes.put("GET /", new GetDirectory(status,directory));
    routes.put("PUT /", new PutPost(status.OK, "/",directory,new FileEditor()));
    routes.put("GET /image.jpeg", new Get(status,"/image.jpeg","image/jpeg",directory,unprotected));
    routes.put("GET /image.gif", new Get(status,"/image.gif","image/gif",directory,unprotected));
    routes.put("GET /image.png", new Get(status,"/image.png","image/png",directory,unprotected));
    routes.put("GET /text-file.txt", new Get(status, "/text-file.txt","text/plain",directory,unprotected));
    routes.put("Put /text-file.txt", new PutPost(status.OK,"text",directory,new FileEditor()));
    routes.put("OPTIONS /method_options", new Options(status.OK));
    routes.put("GET /method_options", new Get(status.OK));
    routes.put("POST /method_options", new PutPost(status.OK,"/method",directory,new FileEditor()));
    routes.put("HEAD /method_options", new Head(status.OK));
    routes.put("PUT /method_options", new PutPost(status.OK,"/method",directory,new FileEditor()));
    routes.put("GET /redirect", new GetRedirect(status,port,serverName));
    routes.put("GET /file1", new Get(status,"/file1","text/plain", directory,unprotected));
    routes.put("GET /file2", new Get(status,"/file2","text/plain", directory,unprotected));
    routes.put("GET /parameters?", new Params(status.OK,"parameters?"));
    routes.put("GET /form", new Get(status,"/form", "text/plain", directory,unprotected));
    routes.put("POST /form", new PutPost(status.OK,"/form",directory,new FileEditor()));
    routes.put("PUT /form", new PutPost(status.OK,"/form",directory,new FileEditor()));
    routes.put("DELETE /form", new Delete(status.OK,"/form",directory,new FileEditor()));
    routes.put("GET /partial_content.txt", new GetPartialContent(status,"/partial_content.txt","text/plain",directory));
    routes.put("GET /patch-content.txt", new Get(status,"/patch-content.txt","text/plain",directory,unprotected));
    routes.put("PATCH /patch-content.txt", new Patch(status,"patch-content",directory,new FileEditor(), new SHA1Encoder()));
    routes.put("GET /logs", new Get(status,"","text/plain",loggerPath, protectedRoute));
    return routes;
  }

  private static ArrayList<String> routeMethods(String [] methods) {
    return new ArrayList<String>(Arrays.asList(methods));
  }

  public static void main( String[] args) throws Exception {
    Map<String, String> Options = OptionsParser.parse(args,KEYS);
    String directory = Options.get(KEYS[DIR_INDEX]);
    int port = Integer.parseInt(Options.get(KEYS[PORT_INDEX]));
    
    HashMap <String, RestMethod> routes = getRoutes(directory, port);
    Responder responder = new Responder(routeDirectory(), routes);
    
    ServerSocket serverSocket = new ServerSocket(port);
    Logger logger = new Logger(loggerPath);
    
    Server app = new Server(port,serverSocket,responder,logger);
    app.run();
  }
}
