package net.leay.beans.factory.support;

/**
 * @description: 实际使用的到的 beanDefinition
 * Created by hqweay on 2019/8/9 16:16
 */
public class RootBeanDefinition extends AbstractBeanDefinition {

  public RootBeanDefinition() {
    super();
  }

  public RootBeanDefinition(Class<?> beanClass){
    super();
    setBeanClass(beanClass);
  }

  public RootBeanDefinition(String beanClassName) {
//  传参 beanClassName 不需要调用 super()，因为 super() 的目的是为 propertyValues 赋值 null。
//  现在只传了个 beanClassName，还没到初始化属性（propertyValues）的时候。
//  也因此，上面传参 beanClass 的方法需要调用 super();
//    super();
    setBeanClassName(beanClassName);
  }

}
