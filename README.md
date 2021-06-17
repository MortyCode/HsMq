## HeShenMQ
[![version](https://img.shields.io/badge/java--version-1.8-orange)]()
[![version](https://img.shields.io/badge/io-netty-blue)](https://github.com/netty/netty)
[![version](https://img.shields.io/badge/license-MIT-green)]()
[![version](https://img.shields.io/badge/version-1.0--SNAPSHOT-brightgreen)]()
---
![a](zimage/hs.jpeg) 
---
```
 _   _   _____       ___  ___   _____    
| | | | /  ___/     /   |/   | /  _  \   
| |_| | | |___     / /|   /| | | | | |   
|  _  | \___  \   / / |__/ | | | | | |   
| | | |  ___| |  / /       | | | |_| |_  
|_| |_| /_____/ /_/        |_| \_______|  
```
### 功能简述
1. 基于Netty来实现
2. 参考了JMS和AMQP两种协议，以及RocketMQ,但是并非照搬
3. 开发中

### 功能简述
![a](zimage/hsmq.png) 

---
### 开发日志：
6.09 实现了消息的发送以及接受，开始开发消息存储

6.12 开始实现持久化,通过NIO操作文件存储
