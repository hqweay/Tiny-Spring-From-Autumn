package net.leay.beans.factory.support;

import net.leay.beans.MutablePropertyValues;
import net.leay.beans.PropertyValue;
import net.leay.beans.factory.config.BeanDefinition;
import net.leay.beans.factory.config.BeanReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 *     <p>BeanDefinition 维护 BeanFactory，把 BeanDefinition 注册到 BeanFactory 的 Registry 里。
 *     <p>在 Spring 里，DefaultListableBeanFactory 需要实现注册机的功能。 对此，通过实现 BeanDefinitionRegistry 接口，来
 *     beanDefinitionMap 的相关方法，
 *     <p>也就是说，在 Spring 里，DefaultListableBeanFactory 不仅提供工厂方法，还实现了注册机的功能。
 *     <p>相对于 SimpleBeanDefinitionRegistry ，DefaultListableBeanFactory 是一个具有注册功能的完整 bean 工厂。
 *     <p>PS：为了简单，我尝试直接维护一个 SimpleBeanDefinitionRegistry 实例。
 *     <p>Created by hqweay on 2019/8/15 17:51
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory
    implements BeanDefinitionRegistry {

  private static final Logger logger = LogManager.getLogger(DefaultListableBeanFactory.class);

  /** Map of bean definition objects, keyed by bean name. */
  //  private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

  /** 用一个 Registry 实例来实现 BeanDefinitionRegistry 的方法~~ */
  private BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();

  /**
   * In Spring 中，singletonObjects 在 DefaultSingletonBeanRegistry 维护。 PS：那我也可以在
   * SimpleBeanDefinitionRegistry 维护啊。
   */
  /** Cache of singleton objects: bean name to bean instance. */
  //  在抽象类 AbstractBeanFactory 维护
  // private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

  /** List of bean definition names, in registration order. */
  private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

  public DefaultListableBeanFactory() {
    super();
  }

  // todo 把这个方法放在 AbstractBeanFactory 实现
  // 就在这实现算了
  @Override
  public Object getBean(String beanName) throws Exception {
    // Spring 有一个 NamedBeanHolder 维护 BeanName － instance
    // 再来个这玩意，太复杂了，直接返回实例好了。
    // todo
    BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanName);
    if (beanDefinition == null) {
      throw new IllegalArgumentException("找不到 BeanDefinition：" + beanName);
    }
    // beanDefinition.getClass().newInstance();
    // 怎么通过 beanName 找到实例呢？
    // 应该说，怎么找，怎么创造？然后存在哪？
    // 是通过 NameBeanHolder 的 getBeanInstance()　来获取的
    // Spring 先看看能不能找到存在的 NameBeanHolder，若找到就通过 NameBeanHolder 的 getBeanInstance() 来尝试获取 Bean

    // 如果不适用这种方式，直接 create 的话，岂不是每次都要新建一个返回？
    // 不行啊，必须找个办法保存一下。

    // 在每个 BeanDefinition 里保存一个实例
    // 不必，可以维护一个 Bean 实例的 Map
    // 实际上，Spring 在这里就使用了 DefaultSingletonBeanRegistry 维护的 map
    // 我直接把这个 map 放这里好了

    // 查一下 singletonObjects 有木有，有就直接返回，没有就实例再返回，oh yeah。
    // Spring 这一步是在 getSingle() 里通过工厂方法实现的
    Object bean = singletonObjects.get(beanName);
    if (bean == null) {
      // 这里锁一下
      // 不能简单实例化，还得初始化属性之类的呢

      // 初始化属性又可能会遇到循环引用等问题。
      /**
       * In Spring，AbstractAutowireCapableBeanFactory 有个 createBeanInstance() 将 BeanDefinition 转换为
       * BeanWrapper BeanWrapper 是对 Bean 的包装
       */
      bean = createBean((RootBeanDefinition) beanDefinition);
      singletonObjects.put(beanName, bean);
      return bean;
    } else {
      return bean;
    }
  }

  //  protected Object getSingleton(String beanName){
  //
  //  }

  /**
   * In Spring， 在 AbstractAutowireCapableBeanFactory 实现。
   *
   * <p>createBean 还涉及到「属性的赋值」。
   */
  @Override
  protected Object createBean(RootBeanDefinition mbd) throws Exception {
    // 真正的实例化，这里肯定得涉及到 BeanDefinition 里的属性了
    // Object bean = (beanDefinition).getBeanClass().newInstance();

    // 实例化~~
    //    Object bean = Class.forName(beanDefinition.getBeanClassName()).newInstance();
    // todo 2019-08-18 22：11然后赋值属性，就到这里吧，打把王者荣耀先
    // 这里就是依赖注入了啊

    RootBeanDefinition mbdToUse = mbd;
    // 应该是通过 resolveBeanClass 获取到了真正的 beanClass
    // 果然如此啊他妈的
    Class<?> resolvedClass = resolveBeanClass(mbd);

    // 有啥用我还没搞清楚
    // if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
    // In Spring，这里对 mbd 进行了深拷贝，但是我们的 mbd 很简单，没那么多属性，就不必了...
    // mbdToUse = new RootBeanDefinition(mbd);
    //   mbdToUse = new RootBeanDefinition();
    //   mbdToUse.setBeanClass(resolvedClass);
    // }

    Object bean;
    try {
      bean = resolvedClass.newInstance();
    } catch (Exception e) {
      logger.trace("实例化失败了，堆栈信息：");
      logger.trace(e);
      throw e;
    }

    logger.trace("开始初始化 bean 的属性。");
    applyPropertyValues(bean, mbd.getPropertyValues());

    return bean;
  }

  /**
   * Apply the given property values, resolving any runtime references to other beans in this bean
   * factory. Must use deep copy, so we don't permanently modify this property.
   *
   * <p>In Spring,see AbstractAutowireCapableBeanFactory
   */
  protected void applyPropertyValues(Object bean, MutablePropertyValues pvs) throws Exception {
    if (pvs.isEmpty()) {
      return;
    }
    //    MutablePropertyValues mpvs = null;
    //    List<PropertyValue> original;
    if (pvs instanceof MutablePropertyValues) {
      for (PropertyValue pv : pvs.getPropertyValues()) {
        String propertyName = pv.getName();
        Object originalValue = pv.getValue();
        try {
          Field field = bean.getClass().getDeclaredField(propertyName);
          field.setAccessible(true);

          // 判断是 bean
          // 咋判断啊
          // todo  根据 Reader 来啊
          if (originalValue instanceof BeanReference) {
            String beanName = ((BeanReference) originalValue).getBeanName();

            Object pvBean = getSingleton(beanName);
            // todo 感觉应该获取 pvBean 的深拷贝...
            if (pvBean == null) {
              if (!containsBeanDefinition(beanName)) {
                throw new Exception("没有 [" + beanName + "] 的定义");
              }
              pvBean = getBean(beanName);
            }
            // 需要再判断下吗？
            originalValue = pvBean;
          }

          // 修改字段值
          field.set(bean, originalValue);

        } catch (NoSuchFieldException e) {
          throw new NoSuchFieldException("找不到属性");
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          throw new IllegalAccessException("设置不了属性");
        }
      }
    }
  }

  /** 下面是实现注册机的相关方法 */
  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
  }

  @Override
  public void removeBeanDefinition(String beanName) throws Exception {
    beanDefinitionRegistry.removeBeanDefinition(beanName);
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) throws Exception {
    return beanDefinitionRegistry.getBeanDefinition(beanName);
  }

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return beanDefinitionRegistry.containsBeanDefinition(beanName);
  }

  @Override
  public boolean isBeanNameInUse(String beanName) {
    return isBeanNameInUse(beanName);
  }

  @Override
  public int getBeanDefinitionCount() {
    return beanDefinitionRegistry.getBeanDefinitionCount();
  }
}
