# 说明

bean 的定义，创建，解析。

## 包结构说明

org.springframework.beans.factory包提供基本的功能来管理和操作bean



## Bean 的定义

![](https://www.ibm.com/developerworks/cn/java/j-lo-spring-principle/image004.png)

## Bean 的解析

![](https://www.ibm.com/developerworks/cn/java/j-lo-spring-principle/image005.png)

当然还有具体对 tag 的解析这里并没有列出。


  

属性  AttributeAccessorSupport


# BeanDefinitionRegistry

Registry

用于 Reader 解析 beans 时，对 bean 进行管理。
比如 bean 的注册、删除、查找。

把 bean 的管理维护与 bean 的来源解耦了！！
可以把 xml 定义的 beans 和注解定义的 beans 都放在 Registry 来管理！！

可以把来源不同的 beans 统一管理。（把 beans 的管理与来源解耦）
            例. 可以把 xml 定义的 beans 和注解定义的 beans 都放在 Registry 来管理。


不对不对
BeanDefinitionRegitry 是 Reader 用的

在 BeanFactory 里有一个 Map 用来管理 BeanDefinition