package org.tnmk.replacing.all.checkdependencies;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckAccessibleLink {
  public static boolean isAccessibleURL(String urlLink) {
    try {
      URL url = new URL(urlLink);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");
      int responseCode = connection.getResponseCode();
      return responseCode != HttpURLConnection.HTTP_NOT_FOUND;
    } catch (MalformedURLException e) {
      throw new UnsupportedOperationException("Link is malformed " + urlLink);
    } catch (IOException e) {
      return false;
    }
  }
}
