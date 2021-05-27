package net.leay.context.support;

import net.leay.MyTest.Cat;
import net.leay.context.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


/**
 * @description: TODO
 * Created by hqweay on 2019/8/29 21:56
 */
public class ClassPathXmlApplicationContextTest {
  private static final Logger logger = LogManager.getLogger(ClassPathXmlApplicationContext.class);

  @Test
  public void testXmlPathApplicationContext() throws Exception {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/defaults.xml");
    Cat myTest = (Cat) applicationContext.getBean("MiMi");


    logger.debug("获取到 property " + myTest.getName());
    logger.debug("获取到 ref 的 property " + myTest.getDog().getName());
  }

}