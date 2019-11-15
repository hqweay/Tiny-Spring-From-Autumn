package cn.hqweay.beans.factory;

/**
 * @description:
 *               来管理 Bean 的实例化
 * Created by hqweay on 2019/8/15 17:13
 */
public interface BeanFactory {
//  throws BeansException
  Object getBean(String name) throws Exception;
}
