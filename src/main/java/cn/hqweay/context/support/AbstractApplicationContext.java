package cn.hqweay.context.support;

import cn.hqweay.beans.factory.support.DefaultListableBeanFactory;
import cn.hqweay.context.ApplicationContext;
import cn.hqweay.core.io.ClassPathResource;
import cn.hqweay.core.io.Resource;
import cn.hqweay.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/29 20:38
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

  /**
   * In Spring，这里维护的是 ResourcePatternResolver（接口）下的实例。
   * resolver 用户解析路径，我们就简单点，不搞这个了...
   * */
  private ResourceLoader resourceLoader;

  /**
   * In Spring，这个定义在
   * org/springframework/context/support/AbstractRefreshableApplicationContext.java
   *
   * Bean factory for this context.
   * */
  private DefaultListableBeanFactory beanFactory;

  /** Synchronization monitor for the "refresh" and "destroy". */
  private final Object startupShutdownMonitor = new Object();

  @Override
  public Object getBean(String name) throws Exception {
    return getBeanFactory().getBean(name);
  }


  @Override
  public Resource getResource(String location) {
    // 这里实现一下
//    return this.resourceLoader.getResource(location);
    return new ClassPathResource(location);
  }

  /**
   * In Spring，这个方法虽然定义在这，但是是在
   * org/springframework/context/support/AbstractRefreshableApplicationContext.java 实现的。
   *
   * 这里返回的是 ConfigurableListableBeanFactory（接口）。
   * 但是这个接口只有一个实现类， DefaultListableBeanFactory。
   * 所以我们就直接返回 DefaultListableBeanFactory 吧...哈哈~
   *
   * */
  public DefaultListableBeanFactory getBeanFactory() throws IllegalStateException{
    //todo synchronized 一下~
    if (this.beanFactory == null) {
      throw new IllegalStateException("BeanFactory not initialized or already closed - " +
              "call 'refresh' before accessing beans via the ApplicationContext");
    }
    return this.beanFactory;
  };

  @Override
  public void refresh() throws Exception {
    // 锁一下。refresh() 时不能进行启动、销毁容器的操作。
    synchronized (this.startupShutdownMonitor) {
      // 解析 Bean 定义，注册到 BeanFactory（Registry）。
      // 这时的 Bean 还未初始化。（调用 getBean() 时才会初始化）
      DefaultListableBeanFactory beanFactory = obtainFreshBeanFactory();
    }
  }


  /**
   * Tell the subclass to refresh the internal bean factory.
   * @see #getBeanFactory()
   */
  protected DefaultListableBeanFactory obtainFreshBeanFactory() throws Exception {
    refreshBeanFactory();
    return getBeanFactory();
  }

  /**
   *
   * In Spring，这个方法虽然定义在这，但是是在
   * org/springframework/context/support/AbstractRefreshableApplicationContext.java 实现的。
   *
   * This implementation performs an actual refresh of this context's underlying
   * bean factory, shutting down the previous bean factory (if any) and
   * initializing a fresh bean factory for the next phase of the context's lifecycle.
   */
  protected final void refreshBeanFactory() throws Exception {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    loadBeanDefinitions(beanFactory);
    this.beanFactory = beanFactory;
  }

  protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
          throws Exception;




}
