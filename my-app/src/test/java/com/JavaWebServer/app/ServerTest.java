package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.net.Socket;

import java.io.*;

public class ServerTest  {
  Server testServer;
  ServerSocket testSocket;

  @Before 
  public void setupServer () throws Exception {
    ArrayList<String> routeMethods = new ArrayList<String>(); 
    routeMethods.add("GET");
    routeMethods.add("Post");
    HashMap<String,ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    routes.put("/",routeMethods);
    Responder  testResponder =  new Responder(routes);
    int  port = 9094;
    testSocket =  new ServerSocket(port);
    testServer = new Server(port,testSocket,testResponder);
  }

  @After
  public void turnOffServer() {
    testSocket.close();
    testServer.off();
  }

  @Test
  public void TestisServerOnEqualsTrue () {
    assertTrue(testServer.isServerOn());
    }
  @Test
  public void TestisServerOnEqualsFalse () {
    testServer.off();
    assertFalse(testServer.isServerOn());
  }

  @Test
  public void TestServerTurnsoff () {
    testServer.off();
    assertFalse(testServer.isServerOn());
  }

 @Test 
  public void  TestServerRun () throws Exception {
    Thread testThread = new Thread(testServer);
    testThread.start();
    testThread.sleep(100); 

    java.net.Socket clientTestSocket = new Socket("localhost", 9094);
    PrintWriter outPut = new PrintWriter( clientTestSocket.getOutputStream(), true);
    outPut.println("GET / HTTP/1.1");
    String message = readServerResponse(clientTestSocket); 
    assertEquals("HTTP/1.1 200 ok", message);
    System.out.println("Client: Message Recieved " + message);
  }


  private void clientGetRequest(Socket clientTestSocket)  throws Exception{
    PrintWriter outPut = new PrintWriter( clientTestSocket.getOutputStream(), true);
    outPut.println("GET / HTTP/1.1");
  }

 private String readServerResponse(Socket clientTestSocket) throws Exception {
   InputStreamReader stream = new InputStreamReader(clientTestSocket.getInputStream());
   BufferedReader response = new BufferedReader(stream);
   return readResponse(response);
 }

 private String readResponse (BufferedReader buffer) throws Exception {
    String message ="";
    String line;
    while ((line = buffer.readLine()) != null) {
      message += line;
    }
    return message;
 }
}
