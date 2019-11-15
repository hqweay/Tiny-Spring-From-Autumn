package cn.hqweay.core.io;

import org.junit.Test;

/**
 * @description:
 * Created by hqweay on 2019/8/15 16:13
 */
public class DefaultResourceLoaderTests {
  @Test
  public void getResource(){
    System.out.println(new DefaultResourceLoader().getResource("https://hqweay.cn"));
  }
}
