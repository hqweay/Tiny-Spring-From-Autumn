package net.leay.beans.factory.support;

import net.leay.beans.MutablePropertyValues;
import net.leay.beans.factory.config.BeanDefinition;

/**
 * @description:
 * Created by hqweay on 2019/8/9 16:11
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {
//  AbstractBeanDefinition继承自BeanMetadataAttributeAccessor类，底层使用了一个LinkedHashMap保存metadata。
//  这个metadata具体是做什么暂时还不知道。
  /** Map with String keys and Object values. */
//  private final Map<String, Object> attributes = new LinkedHashMap<>();

//  这里注意，beanClass 是个引用，既用于保存（指向）类名，又用于保存（指向）类的 Class 对象。
//  这用法牛皮。
  private volatile Object beanClass;

  //  用于存属性。
  private MutablePropertyValues propertyValues;


//  Return the resource that this bean definition came from.
//  看样子这里的 resource 指的是这个 BeanDefinition 对象的来源。
//  private Resource resource;

  protected AbstractBeanDefinition() {
    this(null);
  }

  protected AbstractBeanDefinition(MutablePropertyValues pvs) {
    this.propertyValues = pvs;
  }

  @Override
  public void setBeanClassName(String beanClassName) {
    this.beanClass = beanClassName;
  }

  @Override
  public String getBeanClassName() {
    Object beanClassObject = this.beanClass;
    if(beanClassObject instanceof Class){
      return ((Class<?>) beanClassObject).getName();
    }else{
      return (String)beanClassObject;
    }
  }

  public void setBeanClass(Class<?> beanClass){
    this.beanClass = beanClass;
  }

  public Class<?> getBeanClass() throws IllegalStateException {
    Object beanClassObject = this.beanClass;
    if(beanClassObject == null){
      throw new IllegalStateException("No bean class specified on bean definition");
    }

    // 不能这样玩啊
    if(!(beanClassObject instanceof  Class)){
      throw new IllegalStateException("Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
    }

    return (Class<?>)beanClassObject;
  }

  @Override
  public MutablePropertyValues getPropertyValues() {
      if (this.propertyValues == null) {
        this.propertyValues = new MutablePropertyValues();
      }
      return this.propertyValues;
  }

  /**
   * Return whether this definition specifies a bean class.
   */
  public boolean hasBeanClass() {
    return (this.beanClass instanceof Class);
  }


}
