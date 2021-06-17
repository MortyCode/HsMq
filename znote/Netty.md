# 定义
     netty是一个异步的，事件驱动的，用来做高性能高可用的网络框架。
## 优点

1. 框架设计优雅，底层网络模型切换方便
1. 提供多种标准的协议，安全，编码解码器的支持
1. 在Dubbo RocketMQ 等等中都有使用
# 高性能的原因
## 1. 异步非阻塞通信

- 采用Reactor来设计和实现，可以同时处理多个channel，并且读写都是异步的，可以大幅度的提高io线程的允许效率
## 2. 零拷贝
### 1. DirectBuffer

- Netty的接受和发送使用DirectBuffer，使用堆外内存直接进行进行Socket的读写，不需要字节缓冲区的二次拷贝。
- 传统的堆存储进行Socket读写会将堆内存的数据复制到直接内存之后才可以写入Socket，
- 相比于直接使用直接内存，少一次复制
### 2. 组合Buffer

- Netty提供了组合Buffer的功能，可以方便的聚合Buffer,避免了传统内存拷贝的buffer合并方法
### 3. 文件传输采用transferTo方法

- transferTo方法将数据从FileChannel对象传送到可写的字节通道
- 由native方法实现，使用系统函数sendfile()调用，实现了数据直接从内核的读缓冲区传输到套接字缓冲区，避免了用户态(User-space) 与内核态(Kernel-space) 之间的数据拷贝。
- 可以避免传统的循环写的内存拷贝问题，例如用户态和内核态的切换，文件过大等等问题
## 3. 内存池

- ByteBuf的内存分配使用内存池机制，重复的利用缓冲区的内存，提高性能。
- 在大数据量的情况下，性能于直接内存分配性能有明显提高
## 4. 多种的Reactor模型
### - Reactor单线程模型

- 一个线程同时负责Socket的连接以及任务的分发处理
### - Reactor多线程模型

- 一个线程成为Reactor处理器接受请求
- 网络IO的读写由另一个线程池进行负责，由这些线程来负责处理读写，解码编码，发送。
- 一个线程可以负责多个链路，但是一个链路只能由一个线程负责
### - 主从Reactor多线程模型

- 主线程池处理客户端的连接请求，只负责客户端的登陆，验证，连接等操作。
- 从线程池负责处理链路的读写，解码编码，发送等后续内容。
# 
# Netty的使用
## 1. Bootstrap






### 2. ChannelOption
| **option** | **desc** | **value** |
| --- | --- | --- |
| _TCP_NODELAY_ | 启动TCP_NODELAY，就意味着禁用了Nagle算法，允许小包的发送.
Nagle算法通过减少需要传输的数据包，来优化网络。在内核实现中，数据包的发送和接受会先做缓存，分别对应于写缓存和读缓存。 | true/false |
| _SO_KEEPALIVE_ | 该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。
当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。 | true/false |
| _SO_SNDBUF_ | 操作发送缓冲区大小,发缓冲区用于保存发送数据，直到发送成功。 | 不同操作系统不同 |
| _SO_RCVBUF_ | 接受缓冲区大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功 | 不同操作系统不同 |
| _CONNECT_TIMEOUT_MILLIS_ | 连接超时 |  |

### 



























