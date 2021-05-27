package net.leay.MyTest;

import net.leay.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * Created by hqweay on 2019/8/22 17:00
 */
public class MyTest {

  public static void main(String[] args) {
    String name = null;



  final List<PropertyValue> propertyValueList = new ArrayList<>();
  propertyValueList.add(new PropertyValue("01", "01"));
  propertyValueList.add(new PropertyValue("02", "02"));

  propertyValueList.set(0, new PropertyValue("03", "03"));

  for(PropertyValue pv : propertyValueList){
      System.out.println(pv.getName() + pv.getValue());
  }



    System.out.println(name);
  }
}
