package net.leay.beans.factory.xml;

import net.leay.beans.PropertyValue;
import net.leay.beans.factory.config.BeanDefinition;
import net.leay.beans.factory.config.RuntimeBeanReference;
import net.leay.beans.factory.support.AbstractBeanDefinitionReader;
import net.leay.beans.factory.support.BeanDefinitionRegistry;
import net.leay.beans.factory.support.RootBeanDefinition;
import net.leay.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * @description: 加载 xml 方式的 bean 定义 （xml 路径，或者 xml Resource） 解析，向 registry 注册。
 *
 *  Created by hqweay on 2019/8/15 18:43
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

  // 实际使用时，BeanFactory 也实现了 Registry 接口。
  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
    super(registry);
  }

  @Override
  public int loadBeanDefinitions(String location) throws Exception {
    // 通过抽象类的 resourceLoader 获取 resource
    Resource resource = getResourceLoader().getResource(location);
    return loadBeanDefinitions(resource);
  }

  @Override
  public int loadBeanDefinitions(Resource resource) throws Exception {
    try {
      // 获取 InputStream
      InputStream inputStream = resource.getInputStream();
      try {
        // 加载 BeanDefinitions
        return doLoadBeanDefinitions(inputStream);
      } finally {
        // 关闭 InputStream
        inputStream.close();
      }

    } catch (Exception e) {

    }
    ;
    return 0;
  }

  /** Actually load bean definitions from the specified XML file. */
  protected int doLoadBeanDefinitions(InputStream inputStream) throws Exception {
    /** 获取一个 Document 解析~~ Spring 里，解析使用了一个 DocumentLoader，这里算了，直接来吧... */
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = factory.newDocumentBuilder();

    // 这里调试的时候，显示为 [#document: null]
    // 尼玛。原因是 Document 没有实现 toString() 方法，所以显示为空，实际上可以往下执行并获取到元素。
    Document document = documentBuilder.parse(inputStream);

    // 解析并注册 Bean
    return registerBeanDefinitions(document);
  }

  /** 解析 xml，然后注册~ In Spring, throws BeanDefinitionStoreException in case of parsing errors */
  public int registerBeanDefinitions(Document doc) throws Exception {
    // 在 Spring 里，假定会多次加载 bean 定义的配置文件。
    // 所以先判断判断 registry（注册机）已有的数据。

    // Spring 读取 doc，又用了一个 BeanDefinitionDocumentReader
    // 获取已有 BeanDefinition 数量
    int countBefore = getRegistry().getBeanDefinitionCount();

    // 在 Spring 里，这个方法本在 接口 BeanDefinitionDocumentReader / 实现类 DefaultBeanDefinitionDocumentReader
    // 中实现
    doRegisterBeanDefinitions(doc.getDocumentElement());

    // 返回新注册的 BeanDefinition 数量
    return getRegistry().getBeanDefinitionCount() - countBefore;
  }

  /** 真正的解析是极为复杂的。 比如 import 等标签，甚至有自定义标签，各种路径的判断，还用到监听机制... */
  protected void doRegisterBeanDefinitions(Element root) {
    // 在 Spring 里，这个方法用到了切面，进行前置，后置工作（比如校验）。
    // 这里就简，直接转换。
    parseBeanDefinitions(root);
  }

  // 解析 Bean 的定义
  protected void parseBeanDefinitions(Element root) {
    NodeList nl = root.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node node = nl.item(i);
      if (node instanceof Element) {
        Element ele = (Element) node;
        // 怎么说？？
        // parseDefaultElement(ele, delegate);
        // 先实例化 Bean，在为 Property 赋值
        // 如果 Property 是 Bean 呢？
        // 更复杂哪门办？作为 Property 的 Bean 的 Property 又是 Bean 呢？
        // 慢慢来，先到这一步
        // 根据 name 来做判断
        processBeanDefinition(ele);
      }
    }
  }

  /**
   * Process the given bean element, parsing the bean definition and registering it with the
   * registry. see DefaultBeanDefinitionDocumentReader in Spring
   *
   * <p>解析 Bean 标签。
   */
  protected void processBeanDefinition(Element ele) {
    // 获取定义的 Bean 名称
    String name = ele.getAttribute("name");
    // 获取 bean 对应的类
    String className = ele.getAttribute("class");

    // 生成 BeanDefinition
    // 这里生成的确实是 RootBeanDefinition
    BeanDefinition beanDefinition = new RootBeanDefinition();

    // 在这给 property 赋值
    beanDefinition.setBeanClassName(className);
    parsePropertyElements(ele, beanDefinition);


    // 注册
    // registry 存的是 beanName 与 beanDefinition 的键值对
    // beanDefinition 维护一个 PropertyValues，存的是 property。
    getRegistry().registerBeanDefinition(name, beanDefinition);
  }

  /** 这里传入 propertyValues 对 Bean 的属性进行遍历。 */
  protected void parsePropertyElements(Element ele, BeanDefinition beanDefinition) {
    NodeList propertyNode = ele.getElementsByTagName("property");
    for (int i = 0; i < propertyNode.getLength(); i++) {
      Node node = propertyNode.item(i);

      // 先检查 Property 标签（name 属性）
      parsePropertyElement(node, beanDefinition);

      parsePropertyValue((Element) node, beanDefinition);
    }
  }

  /** Parse property sub-elements of the given bean element. */
  public void parsePropertyElement(Node node, BeanDefinition bd) {
    if (node instanceof Element) {
      Element ele = (Element) node;
      String propertyName = ele.getAttribute("name");

      if (propertyName == null || propertyName.length() <= 0) {
        throw new IllegalArgumentException("Tag 'property' must have a 'name' attribute");
      }

      // 还判断 beanDefinition 的 PropertyValues 是否存在 propertyName
      if(bd.getPropertyValues().contains(propertyName)){
        throw new IllegalArgumentException("Multiple 'property' definitions for property '" + propertyName + "'");
      }
    }
  }

  /**
   * 这里传入 PropertyValue
   *
   * <p>In Spring，这段逻辑写在这个类 org/springframework/beans/factory/xml/BeanDefinitionParserDelegate.java
   * 的方法 parsePropertyValue() 里。 // and see parsePropertyElement()
   *
   * <p>真正解析属性的地方。构造 BeanDefinition / 把属性存在 PropertyValues。
   *
   * <p>In Spring, Get the value of a property element. May be a list etc. 我们不考虑列表的情况。
   */
  protected void parsePropertyValue(Element ele, BeanDefinition beanDefinition) {
    String elementName = ele.getAttribute("name");

    // Should only have one child element: ref, value.

    boolean hasRefAttribute = ele.hasAttribute("ref");
    boolean hasValueAttribute = ele.hasAttribute("value");

    if (hasValueAttribute && hasRefAttribute) {
      throw new IllegalArgumentException(
              elementName + " is only allowed to contain either 'ref' attribute OR 'value' attribute");
    }

    if (hasRefAttribute) {
      String refName = ele.getAttribute("ref");

      if (refName.length() <= 0) {
        throw new IllegalArgumentException(elementName + " contains empty 'ref' attribute");
      }

      // 添加 ref
      // 注意 ref 指向另一个定义的 bean 的 name
      // mark RuntimeBeanReference 的解析在
      // org/springframework/beans/factory/support/BeanDefinitionValueResolver.java
      // resolveValueIfNecessary
      // todo 咋整啊
      // 如果只传一个 refName，那啥时候赋值 beanType 的呢？
      // 等容器需要实例化时，再通过 refName 去注册机寻找对应的 Class
      RuntimeBeanReference ref = new RuntimeBeanReference(refName);
      beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(elementName, ref));

      // 实例化的时候，就拿 ref 去 propertyValues 找，找不到就报错（或 null，哈哈），找到就实例化。

    }else if(hasValueAttribute){
      // value
      String value = ele.getAttribute("value");
      if (value != null && value.length() > 0) {
        // todo 注意还有问题没得
        // 这里用的 MutablePropertyValues。
        // 因为 addPropertyValue() 的逻辑是会覆盖重名的对象，故若配置文件里有同名属性，会以后面定义的属性为准。
        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(elementName, value));
      }
    }else{
      throw new IllegalArgumentException(elementName + " must specify a ref or value");
    }

  }
}
