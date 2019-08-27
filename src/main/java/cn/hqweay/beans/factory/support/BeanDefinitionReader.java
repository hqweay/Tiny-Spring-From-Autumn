package cn.hqweay.beans.factory.support;

import cn.hqweay.core.io.Resource;
import cn.hqweay.core.io.ResourceLoader;

/**
 * @description:
 * Created by hqweay on 2019/8/15 18:35
 */
public interface BeanDefinitionReader {

  /**
   * Return the bean factory to register the bean definitions with.
   * <p> 这里说的是返回一个 BeanFactory 哦。</>
   * <p>The factory is exposed through the BeanDefinitionRegistry interface,
   * encapsulating the methods that are relevant for bean definition handling.
   */
  BeanDefinitionRegistry getRegistry();

  ResourceLoader getResourceLoader();

  /**
   * Load bean definitions from the specified resource.
   * @param resource the resource descriptor
   * @return the number of bean definitions found
   * throws BeanDefinitionStoreException; in case of loading or parsing errors
   */
  int loadBeanDefinitions(Resource resource) throws Exception;

  /** throws BeanDefinitionStoreException   */
  int loadBeanDefinitions(String location) throws Exception;


}
