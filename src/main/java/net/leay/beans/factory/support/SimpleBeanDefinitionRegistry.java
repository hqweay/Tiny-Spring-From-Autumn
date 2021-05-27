package net.leay.beans.factory.support;

import net.leay.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 注册机的实现。（维护 bean 的定义）
 *               本质上是一个线程安全的 Map。
 * Created by hqweay on 2019/8/14 16:17
 */
public class SimpleBeanDefinitionRegistry implements BeanDefinitionRegistry {

  /** Map of bean definition objects, keyed by bean name. */
  private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    assert (beanName != null && beanName != "") : "'beanName' must not be empty";
    assert (beanDefinition != null) : "beanDefinition must not be null";
    this.beanDefinitionMap.put(beanName, beanDefinition);
  }

  @Override
  public void removeBeanDefinition(String beanName) throws Exception {
    if(this.beanDefinitionMap.remove(beanName) == null){
      throw new Exception("没找到 'beanName' 对应的 bean 定义");
    }
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) throws Exception {
    BeanDefinition bd = this.beanDefinitionMap.get(beanName);
    if(bd == null){
      throw new Exception("没找到 'beanName' 对应的 bean 定义");
    }
    return bd;
  }

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return this.beanDefinitionMap.containsKey(beanName);
  }

  @Override
  public boolean isBeanNameInUse(String beanName) {
    // 在 Spring 里有一个 Alias 与 BeanDefinitionMap 一起表示注册信息，所以 containsBeanDefinition 与 isBeanNameInUse 分开判断。
    return containsBeanDefinition(beanName);
  }

  @Override
  public int getBeanDefinitionCount() {
    return this.beanDefinitionMap.size();
  }
}
