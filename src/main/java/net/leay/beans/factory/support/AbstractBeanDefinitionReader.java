package net.leay.beans.factory.support;

import net.leay.core.io.ClassRelativeResourceLoader;
import net.leay.core.io.ResourceLoader;

/**
 * @description:
 * Created by hqweay on 2019/8/15 18:38
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
  private final BeanDefinitionRegistry registry;

  private ResourceLoader resourceLoader;
//  private ClassRelativeResourceLoader resourceLoader;

  protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
    assert (registry != null) : "BeanDefinitionRegistry must not be null";
    this.registry = registry;
    // Determine ResourceLoader to use.
    // 这种用法是为了方便以后如果有一个既实现了 registry 接口又实现了 ResourceLoader 接口的类的实例，
    // 直接用这个实例作为 resourceLoader。
    if (this.registry instanceof ResourceLoader) {
      this.resourceLoader = (ResourceLoader) this.registry;
    }
    else {
      // 用 ClassResourceLoader 如何？
      this.resourceLoader = new ClassRelativeResourceLoader();
    }
  }


  public final BeanDefinitionRegistry getBeanFactory() {
    return this.registry;
  }


  @Override
  public final BeanDefinitionRegistry getRegistry() {
    return this.registry;
  }


  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public ResourceLoader getResourceLoader() {
    return this.resourceLoader;
  }

  /** 在 AbstractBeanDefinitionReader 做的处理主要是让可变参数方法 LoadBeanDefinitions 调用单参函数。
   *  但是这里我只用了单参函数，故在抽象类里没必要做啥。
   * */

}
