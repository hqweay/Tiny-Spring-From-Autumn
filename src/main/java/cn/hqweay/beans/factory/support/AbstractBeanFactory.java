package cn.hqweay.beans.factory.support;

import cn.hqweay.beans.factory.BeanFactory;
import cn.hqweay.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: TODO  这个抽象类主要实现 Bean 的加载
 * Created by hqweay on 2019/8/15 17:36
 */
public abstract class AbstractBeanFactory implements BeanFactory {


  /**
   * In Spring，这个单例缓存定义在
   * org/springframework/beans/factory/support/DefaultSingletonBeanRegistry.java
   * 实现。
   * Cache of singleton objects: bean name to bean instance. */
  // 本来是 private 的，但是我们移过来了，就改为包权限吧...
  protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);


  // In Spring, 传入的参数为 name，然后转换为 beanName。
  // 我们直接传 beanName 就好。
  @Override
  public Object getBean(String beanName) throws Exception {
    return doGetBean(beanName);
  }

  /**
   * throws BeansException
   * */
  protected <T> T doGetBean(final String beanName) throws Exception{
    // todo getBean 哦

    Object bean;

    // 从缓存（单例缓存）中获得 Bean
    // getSingle 这个方法是很麻烦的，因为 Spring 可能多线程运作，可能会出现查找 Bean 的时候另一边正在加载 Bean 的情况等。
    Object sharedInstance = getSingleton(beanName);


    if(sharedInstance != null){
      // In Spring，这里的实例化对象可能为 FactoryBean。
      // 我们不考虑这种情况先。
      bean = sharedInstance;
    }else{
      // 如果缓存没有 bean，就继续检查是不是原型模式等...
      // 这里不做考虑
      // 然后通过 beanName 获取 beanDefinition。调用子类方法了吧...
      // In Spring，这里用了一个 mergedBeanDefinitions。
      try{
        RootBeanDefinition mdb = (RootBeanDefinition) getBeanDefinition(beanName);

        // 创建 Bean 实例
        // 调用了子类的方法
        sharedInstance = createBean(mdb);

      }catch (Exception e){

      }
      bean = sharedInstance;

    }

    return (T) bean;
  }





  /**
   * In,Spring 这个方法在 org/springframework/beans/factory/config/SingletonBeanRegistry.java 定义
   * 在 org/springframework/beans/factory/support/DefaultSingletonBeanRegistry.java 实现
   * 在这个方法中，主要解决了单例 Bean 的循环依赖问题。
   */
  protected Object getSingleton(String beanName){
    // 从单例缓冲中加载 bean
    Object singletonObject = this.singletonObjects.get(beanName);

    // 中间一段判断的逻辑

    return singletonObject;
  }

  /**
   * throws BeanCreationException In,Spring 在这个类实现
   * org/springframework/beans/factory/support/AbstractAutowireCapableBeanFactory.java
   *
   * Create a bean instance for the given merged bean definition (and arguments).
   * The bean definition will already have been merged with the parent definition
   * in case of a child definition.
   *
   * 上面是原说明。但是我们没有用 mergedBeanDefinitions。
   */
  protected abstract Object createBean(RootBeanDefinition beanDefinition) throws Exception;

  /**
   * throws BeansException
   */
  protected abstract BeanDefinition getBeanDefinition(String beanName) throws Exception;

  /**
   * Resolve the bean class for the specified bean definition,
   * resolving a bean class name into a Class reference (if necessary)
   * and storing the resolved Class in the bean definition for further use.
   *
   * throws CannotLoadBeanClassException
   */

  protected Class<?> resolveBeanClass(final RootBeanDefinition mbd)
          throws Exception{
    try {
      if (mbd.hasBeanClass()) {
        return mbd.getBeanClass();
      } else {
        return doResolveBeanClass(mbd);
      }
    }catch (ClassNotFoundException e){
      e.printStackTrace();
      throw e;
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }
  }

  private Class<?> doResolveBeanClass(RootBeanDefinition mbd)
          throws ClassNotFoundException {
    String className = mbd.getBeanClassName();
    if (className != null) {
      return Class.forName(className);
    }else {
      throw new ClassNotFoundException("无法实例化 Class 对象");
    }
  }
}
