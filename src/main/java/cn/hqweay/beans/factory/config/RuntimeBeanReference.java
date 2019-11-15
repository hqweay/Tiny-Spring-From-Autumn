package cn.hqweay.beans.factory.config;

/**
 * @description: 用于深拷贝。
 * Created by hqweay on 2019/8/19 20:36
 */
public class RuntimeBeanReference implements BeanReference {
  private final String beanName;
  private final Class<?> beanType;



  public RuntimeBeanReference(String beanName, Class<?> beanType) {
    this.beanName = beanName;
    this.beanType = beanType;
  }

  /**
   * Create a new RuntimeBeanReference to the given bean name.
   * @param beanName name of the target bean
   */
  public RuntimeBeanReference(String beanName) {
    this(beanName, null);
  }

  /**
   * Create a new RuntimeBeanReference to a bean of the given type.
   * @param beanType type of the target bean
   * @since 5.2
   */
  public RuntimeBeanReference(Class<?> beanType) {
    this(null, beanType);
  }

  @Override
  public String getBeanName() {
    return this.beanName;
  }

  // Return the requested bean type if resolution by type is demanded.
  public Class<?> getBeanType() {
    return beanType;
  }
}
