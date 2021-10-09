## HeShenMQ
[![version](https://img.shields.io/badge/java--version-1.8-orange)](https://www.java.com)
[![version](https://img.shields.io/badge/io-netty-blue)](https://github.com/netty/netty)
[![version](https://img.shields.io/badge/license-MIT-green)](https://github.com/MortyCode/HsMq)
[![version](https://img.shields.io/badge/version-1.0--SNAPSHOT-brightgreen)](https://github.com/MortyCode/HsMq)
```
 _   _   _____       ___  ___   _____    
| | | | /  ___/     /   |/   | /  _  \   
| |_| | | |___     / /|   /| | | | | |   
|  _  | \___  \   / / |__/ | | | | | |   
| | | |  ___| |  / /       | | | |_| |_  
|_| |_| /_____/ /_/        |_| \_______|  
```
### 功能简述
1. 基于Netty来实现的消息中间件
2. 使用Server作为服务端
3. 生产者发送消息到服务端后将消息投递到多个MessageQueue中，消费者订阅一个或者多个Queue
4. 消费者每次取拉取10条消息后放入队列，异步消费
5. 消费后会提交消费记录到本地，定时任务会定时的同步到服务端
6. 在重启后，消费记录也会同步到消费者端


---
### 开发日志：
06.09 实现了消息的发送以及接受，开始开发消息存储

06.12 开始实现持久化,通过NIO操作文件存储

10.09 实现消息消费进度持久化，多消费者消费功能

10.10 合并消费者发送者为客户端 

10.10 实现多消费组消费模式

### 待开发
1. 集群模式
2. 负载均衡
