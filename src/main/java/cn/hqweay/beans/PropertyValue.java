package cn.hqweay.beans;



/**
 * @description:
 * PropertyValue 用于存放 Bean 的属性。
 * 注意 当属性 为 Bean
 * Created by hqweay on 2019/8/9 16:56
 */
public class PropertyValue {

  private final String name;

  // 定义为 Object，是为了应对属性可能为 Bean 的引用的情况（这种情况下指向一个实例
  private final Object value;

  public PropertyValue(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return this.name;
  }

  public Object getValue() {
    return this.value;
  }
}
