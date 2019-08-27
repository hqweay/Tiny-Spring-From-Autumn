package cn.hqweay.beans;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @description: 管理 PropertyValues
 * Created by hqweay on 2019/8/11 14:11
 */
public class MutablePropertyValues implements PropertyValues{
//  已处理属性。不太理解干啥的。
//  private Set<String> processedProperties;
  private final List<PropertyValue> propertyValueList;

  public MutablePropertyValues() {
    this.propertyValueList = new ArrayList<>(0);
  }

  public MutablePropertyValues( List<PropertyValue> propertyValueList) {
//    这里却使用无参构造函数。
    this.propertyValueList =
            (propertyValueList != null ? propertyValueList : new ArrayList<>());
  }

  @Override
  public PropertyValue[] getPropertyValues() {
    // 不需要强转，只需要在转换的时候将需要的类型当成参数传入，java工具即可给我们返回我们想要的类型。
    // 0 这个位置的参数，不足会自动补，多余会用 null 占
    return this.propertyValueList.toArray(new PropertyValue[0]);
  }

  @Override
  public PropertyValue getPropertyValue(String propertyName) {
    for(PropertyValue pv : this.propertyValueList){
      if(pv.getName().equals(propertyName)){
        return pv;
      }
    }
    return null;
  }

  public Object get(String propertyName){
    PropertyValue pv = getPropertyValue(propertyName);
    return (null != pv ? pv.getValue() : null);
  }


  @Override
  public Iterator<PropertyValue> iterator() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return this.propertyValueList.isEmpty();
  }

  /**
   * Add a PropertyValue object, replacing any existing one for the
   * corresponding property or getting merged with it (if applicable).
   * @param pv the PropertyValue object to add
   * @return this in order to allow for adding multiple property values in a chain
   */
  public MutablePropertyValues addPropertyValue(PropertyValue pv) {
    for (int i = 0; i < this.propertyValueList.size(); i++) {
      PropertyValue currentPv = this.propertyValueList.get(i);
      // 如果重复了，覆盖
      if (currentPv.getName().equals(pv.getName())) {
        //todo  覆盖
        this.propertyValueList.set(i, new PropertyValue(pv.getName(), pv.getValue()));
        return this;
      }
    }
    this.propertyValueList.add(pv);
    return this;
  }

  @Override
  public boolean contains(String propertyName) {
    return getPropertyValue(propertyName) != null;
  }
}
