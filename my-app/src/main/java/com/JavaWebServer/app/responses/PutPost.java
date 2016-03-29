package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.helpers.FileEditor;

public class PutPost implements Response {
  private String responseStatus;
  private String path;
  private String fileName;
  private FileEditor editor;
  private String directory;

  public PutPost(String response, String fileName, String directory, FileEditor editor) {
    this.responseStatus = response;
    this.editor = editor;
    this.path = path;
    this.fileName = fileName;
    this.directory = directory;
  }

  public  byte [] handleRequest(Request request) {
    String body = request.getBody();
    if(body != null){
      editFile(body);
    }
    return this.responseStatus.getBytes();
  }

  private void editFile(String body) {
   this.editor.edit(getPath(), body);
  }

  private String getPath() {
    return (this.directory + this.fileName);
  }
}
