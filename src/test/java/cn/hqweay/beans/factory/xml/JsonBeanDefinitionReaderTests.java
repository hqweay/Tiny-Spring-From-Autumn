package cn.hqweay.beans.factory.xml;

import cn.hqweay.MyTest.Cat;
import cn.hqweay.beans.factory.json.JsonBeanDefinitionReader;
import cn.hqweay.beans.factory.support.DefaultListableBeanFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/22 15:26
 */
public class JsonBeanDefinitionReaderTests {

  public static final Logger logger = LogManager.getLogger(JsonBeanDefinitionReaderTests.class);

  @Test
  public void Test(){
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    JsonBeanDefinitionReader jsonBeanDefinitionReader = new JsonBeanDefinitionReader(beanFactory);

    try {
      int count = jsonBeanDefinitionReader.loadBeanDefinitions("/defaults.json");

      Cat myTest = (Cat) beanFactory.getBean("MiMi");

      logger.debug("从配置文件读取到 "+ count +" 条定义");
      logger.debug("获取到 property " + myTest.getName());
      logger.debug("获取到 ref 的 property " + myTest.getDog().getName());

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

}