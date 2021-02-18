package gate.util;

import gate.Utils;
import junit.framework.TestCase;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class TestResolveUrl extends TestCase {

  static class RedirectingHandler implements HttpRequestHandler {

    InetAddress myAddress;
    int port;

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
      response.setStatusCode(303);
      try {
        String path = request.getRequestLine().getUri();
        if(path.matches("/loop/[0-3]")) {
          // simulates a redirection loop
          int nextNum = (path.charAt(6) - '0' + 1) % 4;
          String nextPath = "/loop/" + nextNum;
          System.err.println("Redirecting to " + nextPath);
          response.addHeader("Location", new URI("http", null, myAddress.getHostAddress(), port, nextPath, null, null).toString());
        } else if(path.startsWith("/infinite/")) {
          // simulates a redirection loop
          String nextPath = path + "/x";
          System.err.println("Redirecting " + path + " to " + nextPath);
          response.addHeader("Location", new URI("http", null, myAddress.getHostAddress(), port, nextPath, null, null).toString());
        } else if(path.equals("/redirect-to-file")) {
          response.addHeader("Location", new URI("http", null, myAddress.getHostAddress(), port, "/file", null, null).toString());
        } else if(path.equals("/file")) {
          System.err.println("Attempting to redirect to a file: URL");
          response.addHeader("Location", "file:/etc/hosts");
        }
      } catch(URISyntaxException e) {
        throw new IOException(e);
      }
    }
  }

  HttpServer server;
  URL baseUrl;

  public void setUp() throws Exception {
    RedirectingHandler handler = new RedirectingHandler();
    server = ServerBootstrap.bootstrap()
            .setLocalAddress(InetAddress.getLoopbackAddress())
            .registerHandler("*", handler).create();
    server.start();
    handler.myAddress = server.getInetAddress();
    handler.port = server.getLocalPort();
    baseUrl = new URL("http", server.getInetAddress().getHostAddress(), server.getLocalPort(), "");
    System.err.println("Started server at " + baseUrl);
  }

  public void testRedirectLoop() throws Exception {
    URL url = new URL(baseUrl, "/loop/0");
    try {
      URL newUrl  = Utils.resolveURL(url);
      fail("resolveURL should have failed due to redirect loop");
    } catch(IOException e) {
      // exception expected
    }
  }

  public void testTooManyRedirects() throws Exception {
    URL url = new URL(baseUrl, "/infinite/x");
    try {
      URL newUrl  = Utils.resolveURL(url);
      fail("resolveURL should have failed due to too many redirects");
    } catch(IOException e) {
      // exception expected
    }
  }

  public void testRedirectToFile() throws Exception {
    URL url = new URL(baseUrl, "/redirect-to-file");
    URL newUrl  = Utils.resolveURL(url);
    assertEquals("Redirection should have stopped at http://.../file", new URL(baseUrl, "/file"), newUrl);
  }

  public void tearDown() throws Exception {
    server.stop();
    server.awaitTermination(20, TimeUnit.SECONDS);
  }
}
