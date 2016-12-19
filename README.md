# aopproxy
## spring的AOP功能有两种模式：CGLIB模式(基于类，子类继承) 和 JDKDynamicProxy模式(基于接口，动态代理)
## XmemcachedClient的方法都是final的，添加AOP功能时，只能使用JDKDynamicProxy
## spring有个配置proxy-target-class=true时，会强制使用CGLIB模式
## 很多老系统，已经设置成true了。或者，有的业务代码，只能使用CGLIB
## 为了解决这个冲突，自定义了一个BeanPostProcessor，专门对付XmemcachedClient，使用JDKDynamicProxy模式
