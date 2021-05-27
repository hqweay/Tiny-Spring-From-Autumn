package net.leay.core.io;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/15 17:04
 */
public class ClassRelativeResourceLoader extends DefaultResourceLoader {
  @Override
  public Resource getResource(String location) {

    return new ClassPathResource(location);
  }


  /** Spring 搞了一个内部类 ClassRelativeContextResource 实现 ContextResource 接口*/
}
