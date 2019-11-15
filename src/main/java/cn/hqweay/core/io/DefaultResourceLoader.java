package cn.hqweay.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/15 15:45
 */
public class DefaultResourceLoader implements ResourceLoader{

  /**
   * Spring 里，通过 path（也就是这里的 location），比如 "/resource/my.xml" 获取 Resource 时，
   * 会使用 ResourceResolver 进行前缀匹配。
   * 具体的加载情况很复杂，
   */
  @Override
  public Resource getResource(String location) {

    return null;
  }
}
