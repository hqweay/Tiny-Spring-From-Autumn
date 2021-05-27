package net.leay.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/15 15:00
 */
public interface InputStreamSource {
  InputStream getInputStream() throws IOException;
}
