package cn.hqweay.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: TODO
 *                根据类所在的路径层级寻找 resources 文件夹，以 resources 为根路径来寻找资源。
 * Created by hqweay on 2019/8/15 16:54
 */
public class ClassPathResource extends AbstractResource{
  private final String path;

  public ClassPathResource(String path) {
    this.path = path;
  }

  /**
   * 注意 getClass().* 的方法。
   * */
  @Override
  public InputStream getInputStream() throws IOException {
    return this.getClass().getResourceAsStream(this.path);
  }


}
