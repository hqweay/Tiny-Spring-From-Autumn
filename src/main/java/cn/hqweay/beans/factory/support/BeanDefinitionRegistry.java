package cn.hqweay.beans.factory.support;

import cn.hqweay.beans.factory.config.BeanDefinition;

/**
 * @description: 可以把来源不同的 beans 统一管理。（把 beans 的管理与来源解耦）
 *               例. 可以把 xml 定义的 beans 和注解定义的 beans 都通过 Registry 来管理。
 *     Created by hqweay on 2019/8/14 16:06
 */
public interface BeanDefinitionRegistry {

  /**
   * 注册/添加
   * throw BeanDefinitionStoreException
   * Exception thrown when a BeanFactory encounters an invalid bean definition:
   * e.g. incomplete or contradictory bean metadata.
   */
  void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

  /**
   * 删除
   */
  void removeBeanDefinition(String beanName) throws Exception;

  /**
   * 获取
   * throw NoSuchBeanDefinitionException
   */
  BeanDefinition getBeanDefinition(String beanName) throws Exception;

  boolean containsBeanDefinition(String beanName);

  /**
   * 判断 beanName 是否已经被注册。
   * @param beanName
   * @return
   */
  boolean isBeanNameInUse(String beanName);

  /**
   * Return the number of beans defined in the registry.
   * @return the number of beans defined in the registry
   */
  int getBeanDefinitionCount();
}
