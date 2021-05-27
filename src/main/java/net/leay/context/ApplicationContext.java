package net.leay.context;

import net.leay.beans.factory.BeanFactory;
import net.leay.core.io.ResourceLoader;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/29 20:35
 */
public interface ApplicationContext extends BeanFactory, ResourceLoader {
  /**
   * In Spring，定义在
   * org/springframework/context/ConfigurableApplicationContext.java
   * @throws IllegalStateException
   */
  void refresh() throws IllegalStateException, Exception;


}
