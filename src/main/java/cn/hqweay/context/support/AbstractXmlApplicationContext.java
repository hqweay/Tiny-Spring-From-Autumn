package cn.hqweay.context.support;

import cn.hqweay.beans.factory.support.DefaultListableBeanFactory;
import cn.hqweay.beans.factory.xml.XmlBeanDefinitionReader;
import cn.hqweay.core.io.Resource;

import java.io.IOException;

/**
 * @description:
 *                这个抽象类整合了
 *                org/springframework/context/support/AbstractRefreshableApplicationContext.java
 *                定义的一些属性和方法。
 *
 * Created by hqweay on 2019/8/29 20:47
 */
public abstract class AbstractXmlApplicationContext extends AbstractApplicationContext {


  /**
   * In Spring，这个属性定义在
   * org/springframework/context/support/AbstractRefreshableConfigApplicationContext.java
   * 而且维护是一个数组。
   * 我们先不考虑复杂情况，就只能传一个配置文件进来。
   */
  private String configLocation;

  /**
   * Loads the bean definitions via an XmlBeanDefinitionReader.
   * In Spring，这个方法定义在 org/springframework/context/support/AbstractRefreshableApplicationContext.java
   */
  @Override
  protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception {
    // Create a new XmlBeanDefinitionReader for the given BeanFactory.
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

    // 这里不要也可以
    // 因为内部使用的是 ClassRelativeResourceLoader
    // 在判断没有 resourceLoader 时会新建一个~~
    // 这里直接把实现了 ResourceLoader 接口的 ApplicationContext 当作 ResourceLoader 传入了~~
    beanDefinitionReader.setResourceLoader(this);

    //initBeanDefinitionReader(beanDefinitionReader);
    // 加载了哦
    loadBeanDefinitions(beanDefinitionReader);
  }

  /**
   * Load the bean definitions with the given XmlBeanDefinitionReader.
   */
  protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws Exception {
    Resource configResource = getConfigResources();
    if (configResource != null) {
      reader.loadBeanDefinitions(configResource);
    }

    String configLocation = getConfigLocation();
    if (configLocation != null) {
      reader.loadBeanDefinitions(configLocation);
    }
  }

  // org.springframework.context.support.AbstractRefreshableConfigApplicationContext#getConfigLocations
  protected String getConfigLocation() {
    return (this.configLocation != null ? this.configLocation : null);
  }

  // Resource 同理
  protected Resource getConfigResources() {
    return null;
  }

  /**
   * In Spring，定义在
   * org.springframework.context.support.AbstractRefreshableConfigApplicationContext#setConfigLocations
   *
   * Set the config locations for this application context.
   * <p>If not set, the implementation may use a default as appropriate.
   */
  public void setConfigLocations(String location) {
    if (location != null) {
      assert(location != null) : "Config locations must not be null";
      this.configLocation = new String(location);
    }
    else {
      this.configLocation = null;
    }
  }


}
