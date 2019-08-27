package cn.hqweay.beans.factory.config;


import cn.hqweay.beans.MutablePropertyValues;

/**
 * @description: BeanDefinition 只定义操作 BeanClassName 的方法。
 *               在抽象类 AbstractBeanDefinition 才定义操作 BeanClass 的方法。
 * Created by hqweay on 2019/8/9 16:09
 */
public interface BeanDefinition {

  /**
   * Specify the bean class name of this bean definition.
   */
  void setBeanClassName(String beanClassName);

  /**
   * Return the current bean class name of this bean definition.
   */
  String getBeanClassName();


  // 获取 Bean 的属性，方法定义在这里是为了 SPI 调用
  MutablePropertyValues getPropertyValues();
}
