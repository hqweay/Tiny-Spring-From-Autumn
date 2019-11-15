package cn.hqweay.core.io;

import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * Created by hqweay on 2019/8/15 16:45
 */
public class FileSystemResourceLoaderTests {
  @Test
  public void getResource(){

    try {
      // 绝对路径
      System.out.println(
          new FileSystemResourceLoader()
              .getResource("D:\\Autumn\\src\\main\\resources\\test.txt")
              .getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
