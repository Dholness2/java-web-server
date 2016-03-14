package com.JavaWebServer.app;

import java.io.PrintWriter;

public class PutPost implements RestMethod {
  private String responseStatus;
  private String path;
  private String formField;
  private FileEditor editor;

  public PutPost(String response, String path, FileEditor editor) {
    this.responseStatus = response;
    this.editor = editor;
    this.path = path;
  }

  public PutPost(String response) {
   this.responseStatus = response;
  }

  public  byte [] handleRequest(Request request) {
    String body = request.getBody();
    if(body != null){
      editFile(body);
    }
    return this.responseStatus.getBytes();
  }

  public void editFile(String body) {
   this.editor.edit(this.path, body);
  }
}