package net.leay.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/15 15:55
 */
@Deprecated
public class UrlResource extends AbstractResource {
  /**
   * Original URL, used for actual access.
   */
  private final URL url;

  /**
   * Create a new {@code UrlResource} based on the given URL object.
   * @param url a URL
   */
  public UrlResource(URL url) {
    assert (url != null) : "URL must not be null";
    this.url = url;
  }

  /**
   * 通过给定的 URL 获取一个 InputStream
   * */
  @Override
  public InputStream getInputStream() throws IOException {
    URLConnection con = this.url.openConnection();
    con.connect();
    try {
      return con.getInputStream();
    }
    catch (IOException ex) {
      // Close the HTTP connection (if applicable).
      if (con instanceof HttpURLConnection) {
        ((HttpURLConnection) con).disconnect();
      }
      throw ex;
    }
  }
}
