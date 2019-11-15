package cn.hqweay.beans.factory.config;

/**
 * @description: TODO
 * Created by hqweay on 2019/8/19 20:36
 */
public interface BeanReference {
  /**
   * Return the target bean name that this reference points to (never {@code null}).
   */
  String getBeanName();
}
