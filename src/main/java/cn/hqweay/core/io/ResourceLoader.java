package cn.hqweay.core.io;

/**
 * @description: TODO
 *               ResourceLoader 用于加载 Resource 的策略。
 *               把 Resource 的来源与创建解耦。比如可以根据 path 创建 Resource。
 * Created by hqweay on 2019/8/15 15:35
 */
public interface ResourceLoader {

  /**
   * Spring 里对 getResource() 要求得很严格。 比如要实现
   *
   * <p><ul>
   *   <li>Must support fully qualified URLs, e.g. "file:C:/test.dat". *
   *   <li>Must support classpath pseudo-URLs, e.g. "classpath:test.dat". *
   *   <li>Should support relative file paths, e.g. "WEB-INF/test.dat". * (This will be
   *       implementation-specific, typically provided by an * ApplicationContext implementation.)
   * </ul>
   *
   * 我们根据相对路径到 resources 资源文件夹下去找就行了。
   */
  Resource getResource(String location);
}
