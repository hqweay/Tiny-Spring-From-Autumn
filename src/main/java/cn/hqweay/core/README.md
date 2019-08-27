# 说明

定义资源的访问方式
如何把资源包装起来


比如把 bean 信息的来源与解析解耦。

来源可以是本地的，网络传递的...

可以是 xml，定义个 xml 解析。
也可以是 json 啊，自定定义解析方式就是了。


# 断言

java 提供了 assert
注意：断言不应该以任何方式改变程序的状态。
简单的说，如果希望在不满足某些条件时阻止代码的执行，就可以考虑用断言来阻止它。


# todo
reader
reader 可以往后放放

BeanFactory


# Resource
FileSystemResource：以文件的绝对路径方式进行访问
ClassPathResourcee：以类路径的方式访问
ServletContextResource：web应用根目录的方式访问

FileSystemResource 效果类似于Java中的File
ClassPathResource 效果类似于this.getClass().getResource("/").getPath();
ServletContextResource 效果类似于request.getServletContext().getRealPath("");

# ResourceLoader
资源加载器

ClassRelativeResourceLoader 返回 ClassPathResource

# ResourceResolver
用于解析路径到对应的资源。。。吧？