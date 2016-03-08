package com.JavaWebServer.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import java.util.LinkedHashMap;

public class GetDirectoryTest {

  private Map <String, String> directoryLinks = new LinkedHashMap <String, String> ();
  private StatusCodes codes = new StatusCodes();
  private  final String CRLF = "\r\n";
  private  final String CONTENTYPE = "text/html";
  private  String directory = "/Users/don/desktop/cob_spec/public/";

  private String buildHeader (String body, String type) {
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length:";
    int length = body.getBytes().length;
    return (codes.OK +CRLF+typeHeader
        + type+CRLF+contentLength
        + length);
  }

  @Test
  public void handleResponseTest(){
    RestMethod testGetDir = new GetDirectory(codes,directory);
    byte [] response = testGetDir.handleRequest(new Request());
    String body = "<!DOCTYPE html><html><body><a href=\"/file1\">file1</a><br>"+CRLF+
                   "<a href=\"/file2\">file2</a><br>"+CRLF+
                   "<a href=\"/form\">form</a><br>"+CRLF+
                   "<a href=\"/image.gif\">image.gif</a><br>"+CRLF+
                   "<a href=\"/image.jpeg\">image.jpeg</a><br>"+CRLF+
                   "<a href=\"/image.png\">image.png</a><br>"+CRLF+
	           "<a href=\"/partial_content.txt\">partial_content.txt</a><br>"+CRLF+
                   "<a href=\"/patch-content.txt\">patch-content.txt</a><br>"+CRLF+
		   "<a href=\"/text-file.txt\">text-file.txt</a><br></body></html>";
    String expected = buildHeader(body, CONTENTYPE)+CRLF+CRLF+body;
    assertEquals(expected, new String(response));
  }
}