package cn.hqweay.beans;


/**
 * @description: 定义 PropertyValues 的方法
 * Created by hqweay on 2019/8/9 16:57
 */
public interface PropertyValues extends Iterable<PropertyValue> {
  /**
   * Return an array of the PropertyValue objects held in this object.
   */
  PropertyValue[] getPropertyValues();

  /**
   * Return the property value with the given name, if any.
   * @param propertyName the name to search for
   * @return the property value, or {@code null} if none
   */

  PropertyValue getPropertyValue(String propertyName);

  /**
   * Does this holder not contain any PropertyValue objects at all?
   */
  boolean isEmpty();

  /**
   * Is there a property value (or other processing entry) for this property?
   * @param propertyName the name of the property we're interested in
   * @return whether there is a property value for this property
   */
  boolean contains(String propertyName);
}
