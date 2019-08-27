package cn.hqweay.beans.factory.config;

/**
 * @description: 用于浅拷贝。
 * Created by hqweay on 2019/8/22 19:51
 */
@Deprecated
public class RuntimeBeanNameReference implements BeanReference {
  @Override
  public String getBeanName() {
    return null;
  }
}
