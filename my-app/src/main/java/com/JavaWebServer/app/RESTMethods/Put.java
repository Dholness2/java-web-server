package com.JavaWebServer.app;

public class Put  implements RestMethod {
  private final String METHODKEY= "PUT";
  private final String ACCEPTED ="HTTP/1.1 200 ok";

  public Put() {}

  public String handleRequest(Request request) {
     return ACCEPTED;
  }
}
