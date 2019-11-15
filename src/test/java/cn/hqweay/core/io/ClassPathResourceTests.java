package cn.hqweay.core.io;

import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * Created by hqweay on 2019/8/22 15:54
 */
public class ClassPathResourceTests {

  @Test
  public void test(){
    try {
      ClassPathResource classPathResource = new ClassPathResource("/defaults.xml");
      System.out.println(classPathResource.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}