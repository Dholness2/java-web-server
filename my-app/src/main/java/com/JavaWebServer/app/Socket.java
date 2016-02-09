package com.JavaWebServer.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Socket implements ClientSocket {

  private java.net.Socket socket;

  public Socket (java.net.Socket socket) {
    this.socket = socket;
  }

  public Request getRequest() {
    try{
      BufferedReader request = new BufferedReader(getStreamReader()); 
      return new Request(request.readLine());  
    } catch (IOException e) {
      System.out.println("cant read request"+ e);
      return null;
    }
  }

  public void sendResponse(String response) {
    PrintWriter responder =  new PrintWriter(getOutputStream(),true);
    responder.println(response);
  }

  public void close () {
    try {
      this.socket.close();
    } catch (IOException e) {
      System.out.println("Can't Close Client Socket" + e);
    }
  } 

  private InputStream getInputStream() {
    try { 
      return socket.getInputStream();
    } catch(IOException e) {
      System.out.println("can't open InputStream"+ e);
      return null;
    }
  }

  private OutputStream getOutputStream() {
    try {
      return socket.getOutputStream();
    } catch(IOException e) {
      System.out.println("can't open OuputStream"+ e);
      return null;
    }
  }

  private InputStreamReader getStreamReader () throws IOException {
    InputStream input = this.socket.getInputStream();
    InputStreamReader stream = new InputStreamReader(input);
    return stream;
  }
}