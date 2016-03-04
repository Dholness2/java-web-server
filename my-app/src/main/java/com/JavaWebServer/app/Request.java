package com.JavaWebServer.app;
import java.util.Arrays;

public class Request {
  final String  PROTOCOL = "HTTP/1.1";
  final String  ROUTESlASH = "/";
  private String request;

  public Request () {

  }

  public boolean isParams() {
    return this.request.split(" ")[1].contains("?");
  }

  public String getRoute() {
    if (isParams()){
      return getParamsRoute();
    } else {
      return this.request.split(" ")[1];
    }
  }

  public String getParams() {
    return request.split(" ")[1].split("\\?")[1];
  }

  public String getParamsRoute() {
    String route = this.request.split(" ")[1];
    return route.substring(0,(route.indexOf("?") + 1));
  }
  public String getRequest(){
    if (isParams()){
      return (getMethod() + " "+ getParamsRoute());
    }
    return this.request.replace(PROTOCOL,"").trim();
  }

  public void setMessage(String message) {
    this.request = message;
  }

  public String getMethod() {
    return this.request.split(" ")[0];
  }

  public boolean validRequest() {
    if (this.request.trim().isEmpty()) {
      return false;
    }else if ((validRoute() == false) ||(validProtocol() == false)) {
      return false;
    } else {
      return true;
    }
  }

  private boolean validRoute() {
    return this.request.split(" ")[1].startsWith("/");
  }

  private boolean validProtocol() {
    return this.request.contains(PROTOCOL);
  }
}
