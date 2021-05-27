package net.leay.context.support;

import net.leay.core.io.Resource;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/29 21:22
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
  // In Spring，这里是一个数组。
  private Resource configResources;


  /**
   * Create a new ClassPathXmlApplicationContext, loading the definitions
   * from the given XML files and automatically refreshing the context.
   */
  public ClassPathXmlApplicationContext(String configLocation) throws Exception {
    this(configLocation, true);
  }


  public ClassPathXmlApplicationContext(String configLocation, boolean refresh) throws Exception {

    setConfigLocations(configLocation);

    if (refresh) {
      refresh();
    }
  }
}
