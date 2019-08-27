package cn.hqweay.core.io;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/15 16:37
 */
@Deprecated
public class FileSystemResourceLoader extends DefaultResourceLoader {
  @Override
  public Resource getResource(String location) {
    return new FileSystemResource(location);
  }
}
