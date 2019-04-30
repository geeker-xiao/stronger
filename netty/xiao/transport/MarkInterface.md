
# transport模块 

## 常用的标记接口
* Serializable
    - `标记类是否支持序列化的,所谓的序列化就是将对象的各种信息转换成可以存储或者传输的一种形式`
* Cloneable
    - `这样的好处是以接口的形式标记对象是否拥有复制能力`
    * 
      `按道理来说的话每一个类都应该可以运行clone方法才对呀，为什么还需要这样一个标记接口呢？这样的好处是以接口的形式标记对象是否拥有某种能力。想一想，如果不通过标记接口的形式，我们在平时的开发中，会怎么去实现呢？ 一般来说都是通过增加变量或者设置枚举来达到控制的效果，这样或许能解决问题，但是往往不能从面向对象的角度来优雅的解决问题。想想接口的作用是什么？接口就是用来标记某个类拥有了哪些功能、特性，而标记接口则是在面向对象的角度来看，更高级的一种抽象：即使你拥有这个方法也不行，因为你没有这个功能的标记接口，所以在调用clone方法的过程中，如果对象没有实现Cloneable接口，那么虚拟机就会抛出一个CloneNotSupportedException异常。`
* RandomAccess
    - `这个接口的作用是判断集合是否能快速访问，也就是通过索引下标能否快速的移动到对应的元素上`
* EventListener
  - `所有事件侦听器接口都必须扩展的标记接口。`