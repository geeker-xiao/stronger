package io.netty.buffer;


    /**

     Abstraction of a byte buffer - the fundamental data structure to represent a low-level binary and text message.
     抽象字节缓冲区 - 表示低级二进制和文本消息的基本数据结构。

     Netty uses its own buffer API instead of NIO {java.nio.ByteBuffer} to represent a sequence of bytes.
     Netty使用自己的缓冲API而不是NIO {java.nio.ByteBuffer}来表示字节序列。


     This approach has significant advantage over using { java.nio.ByteBuffer}.
     与使用{java.nio.ByteBuffer}相比，这种方法具有明显的优势。


     Netty's new buffer type ( io.netty.buffer.ByteBuf ) has been designed from ground up to address the problems of {java.nio.ByteBuffer}
     Netty的新缓冲区类型（io.netty.buffer.ByteBuf）经过精心设计，旨在解决{java.nio.ByteBuffer}的问题，

     and to meet the daily needs of network application developers.
     并满足网络应用程序开发人员的日常需求。

     address the problems(解决问题)



     To list a few cool features:
     列出一些很酷的功能：

     <ul>
         <li>You can define your buffer type if necessary.</li>
         如有必要，您可以定义缓冲区类型。

         <li>Transparent zero copy is achieved by built-in composite buffer type.</li>
         透明零拷贝是通过内置复合缓冲区类型实现的。

         <li>A dynamic buffer type is provided out-of-the-box, whose capacity is
         expanded on demand, just like { java.lang.StringBuffer}.</li>

         动态缓冲区类型是开箱即用的，其容量可根据需要进行扩展，就像JDK 自身提供的 {java.lang.StringBuffer}一样。


         <li>There's no need to call the  flip() method anymore.</li>
         无需再调用flip（）方法。

         <li>It is often faster than ByteBuffer.</li>
         它通常比ByteBuffer快。

     </ul>

     <h3>Extensibility</h3> 扩展性
     */


    /**
      ByteBuf has rich set of operations optimized for rapid protocol implementation.
      ByteBuf具有针对快速协议实现而优化的丰富操作集。

      For example,

      ByteBuf provides various operations for accessing unsigned values and strings
      and searching for certain byte sequence in a buffer.
      ByteBuf提供各种操作来访问无符号值和字符串，并在缓冲区中搜索某些字节序列。

      You can also extend or wrap existing buffer type to add convenient accessors.
      您还可以扩展或包装现有缓冲区类型以添加​​方便的访问器。

      The custom buffer type still implements ByteBuf interface rather than introducing an incompatible type.
      自定义缓冲区类型仍然实现ByteBuf接口，而不是引入不兼容的类型。

     */

    /**

      To lift up the performance of a network application to the extreme,
      为了将网络应用程序的性能提升到极致，

      you need to reduce the number of memory copy operation.
      你需要减少内存复制操作的次数

      You might have a set of buffers that could be sliced and combined to compose a whole message.
      您可能有一组缓冲区可以切片并组合以组成整个消息。

      Netty provides a composite buffer which allows you to create a new buffer from the
      arbitrary number of existing buffers with no memory copy.
      Netty提供了一个复合缓冲区，允许您从任意数量的现有缓冲区创建一个没有内存副本的新缓冲区。

      For example,
      a message could be composed of two parts; header and body.
      一条信息可以由两部分组成;标题和正文。

      In a modularized application,
      the two parts could be produced by different modules and assembled later when the message is sent out.
      在模块化的应用程序中，这两个部分可以由不同的模块生成，并在稍后发送消息时组装。

      <pre>
      +--------+----------+
      | header |   body   |
      +--------+----------+
      </pre>

     */

      /**

      If ByteBuffer were used, you would have to create a new big
      buffer and copy the two parts into the new buffer.
      如果使用了ByteBuffer，则必须创建一个新的大缓冲区并将这两个部分复制到新缓冲区中。


      Alternatively, you can perform a gathering write operation in NIO,
      或者，您可以在NIO中执行收集写入操作

      but it restricts you to represent the composite of buffers as an array of ByteBuffers rather than a single buffer,
      breaking the abstraction and introducing complicated  state management.
      但它限制您将缓冲区组合表示为ByteBuffers数组而不是单个缓冲区，从而打破抽象并引入复杂的状态管理。

      Moreover, it's of no use if you are not going to read or write from an NIO channel.
      此外，如果您不打算从NIO频道读取或写入，则没有用处。


       */


      /**


      // The composite type is incompatible with the component type.
      复合类型与组件类型不兼容。

      ByteBuffer[] message = new ByteBuffer[] { header, body };


      By contrast,ByteBuf does not have such caveats because it is fully extensible and has a built-in composite buffer type.
      相比之下，ByteBuf没有这样的警告，因为它是完全可扩展的并且具有内置的复合缓冲区类型。

       */



      /**

       // The composite type is compatible with the component type.
       复合类型与组件类型兼容。
      io.netty.buffer.ByteBuf message = io.netty.buffer.Unpooled.wrappedBuffer(header, body);

      // Therefore, you can even create a composite by mixing a composite and an ordinary buffer.
      因此，您甚至可以通过混合复合和普通缓冲区来创建复合。

      io.netty.buffer.ByteBuf messageWithFooter = io.netty.buffer.Unpooled.wrappedBuffer(message, footer);

      Because the composite is still a  ByteBuf, you can access its content easily,
      由于复合仍然是ByteBuf，因此您可以轻松访问其内容，

      and the accessor method will behave just like it's a single buffer even if the region you want to access spans over multiple components.
      即使您要访问的区域跨越多个组件，访问器方法也会像单个缓冲区一样运行。


      The unsigned integer being read here is located across body and footer.
      这里读取的无符号整数位于正文和页脚之间。

      messageWithFooter.getUnsignedInt( messageWithFooter.readableBytes() - footer.readableBytes() - 1);

       */


    /**

     <h3>Automatic Capacity Extension</h3> 自动容量扩展

     Many protocols define variable length messages,
     许多协议定义可变长度消息，

     which means there's no way to determine the length of a message until you construct the message
     这意味着在构造消息之前无法确定消息的长度

     or it is difficult and inconvenient to calculate the length precisely.
     或者精确计算长度是困难和不方便的。

     It is just like when you build a { java.lang.String}.
     就像你构建一个{java.lang.String}一样。

     You often estimate the length of the resulting string and let {java.lang.StringBuffer} expand itself on demand.
     您经常估计结果字符串的长度，并让{java.lang.StringBuffer}按需扩展。
     estimate(估计)

     <pre>
     A new dynamic buffer is created.
     创建一个新的动态缓冲区。

     Internally, the actual buffer is created lazily to avoid potentially wasted memory space.
     在缓冲区内部，延迟创建实际缓冲区以避免可能浪费的内存空间。

     io.netty.buffer.ByteBuf b =  io.netty.buffer.Unpooled.buffer(4);

     When the first write attempt is made, the internal buffer is created with the specified initial capacity (4).
     当进行第一次写入尝试时，将使用指定的初始容量创建内部缓冲区（4）。
     b.writeByte('1');

     b.writeByte('2');
     b.writeByte('3');
     b.writeByte('4');

     When the number of written bytes exceeds the initial capacity (4),
     the internal buffer is reallocated automatically with a larger capacity.
     当写入的字节数超过初始容量（4）时，内部缓冲区将自动重新分配，容量更大。

     b.writeByte('5');
     </pre>


     */

    /**
     <h3>Better Performance</h3>
     更好的性能

     Most frequently used buffer implementation of
     {io.netty.buffer.ByteBuf} is a very thin wrapper of a byte array
     最常用的{io.netty.buffer.ByteBuf}缓冲区实现是一个非常薄的字节数组包装器

     (i.e. {@code byte[]}).
     Unlike ByteBuffer, it has no complicated boundary check and index compensation,
     与ByteBuffer不同，ByteBuf没有复杂的边界检查和索引补偿

     and therefore it is easier for a JVM to optimize the buffer access.
     因此，JVM更容易优化缓冲区访问。


     More complicated buffer implementation is used only for sliced or composite buffers,
     and it performs as well as ByteBuffer.
     更复杂的缓冲区实现仅用于切片或复合缓冲区，并且它的性能与ByteBuffer一样。
     */

//public class ByteBuf介绍 {
    //}
