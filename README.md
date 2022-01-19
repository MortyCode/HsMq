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

### 待开发
1. 集群模式
2. 负载均衡

### 一点小记录
项目位置：[https://github.com/MortyCode/HsMq.git](https://github.com/MortyCode/HsMq.git)
# 1. 协议
| 字段 | Length | HeadLength | head | DataLength | data |
| --- | --- | --- | --- | --- | --- |
| 解释 | 总消息体长度 | 消息头长度 | 消息体   |  |  |
| 长度 | int(4个字节) | int(4个字节) | HeadLength的值 | int(4个字节) | DataLength的值 |

- 解码 	LengthObjectDecode
- 编码 	LengthObjectEncode
### Head

- msgType: 请求类型 Req/Resp
### Data

- 协议消息体序列化 Req对象/Resp对象
# 2 . 服务端
## 2.1 服务端Reactor Handel
### 2.1.1 基于模板模式实现一个简单命令处理
```java
   private static final Map<OperationEnum, BaseExecutor<?>> enumExecutorMap ;

   ....策略初始化操作
   
   public static HsEecodeData executor(HsDecodeData hsDecodeData){
        HsReq<?> hsReq = (HsReq<?>) hsDecodeData.getData();

        OperationEnum operationEnum = hsReq.getOperationEnum();
        if (operationEnum==null){
            ... 异常处理
        }
        BaseExecutor<?> baseExecutor = enumExecutorMap.get(operationEnum);
        return baseExecutor.executorReq(hsReq);
    }
```
### 2.1.2 处理模板

- 这里有一个细节,这里有两个方法，**executor**方法的泛型是**T 标识一个确定的类型**,**executor0** 方法泛型的是**? 标识一个不确定的类型**.
- 因为从HsDecodeData获取的对象是Object类型,并不知道此时类型是什么，所以convertReq的意义就是校验请求对象的数据是不是处理器需要的数据类型，并且转换。
```java
public abstract class BaseExecutor<T> {

    public static MessageStore messageStore = new MessageStore();
    public abstract HsResp<?> executor(HsReq<T> hsReq);
    public abstract HsReq<T> convertReq(HsReq<?> hsReq);

    public HsEecodeData executorReq(HsReq<?> hsReq){
        HsReq<T> fixedHsReq = convertReq(hsReq);
        if (fixedHsReq==null){
            return HsEecodeData.typeError();
        }
        
        HsResp<?> hsResp = executor(fixedHsReq);
        hsResp.setReqType(hsReq.getOperation());
        hsResp.setReqId(hsReq.getReqId());
        return HsEecodeData.resp(hsResp);
    }

}
```


### 2.1.3 目前处理器

- SendMessageExecutor 接受发送消息处理
- PullExecutor 拉去消息处理
- CommitOffsetExecutor 提交偏移量处理
- TopicDataExecutor 获取Topic的信息处理



## 2.2. 消息存储 SendMessageExecutor
### 2.2.1 消息存储

- 消息体首先存储到一个公共存储消息位置，目前是在 mq_1文件之中
- 然后将返回的消息偏移量等信息存储到消息队列中，队列数为初始化的时候指定, 位置为/queue/目录下
- 然后消息消费的时候，会从queue中拉取消息
```java
public HsResp<?> saveMessage(SendMessage sendMessage){
....
  MessageDurability messageDurability = MessageStorage.saveMessage(sendMessage);
  boolean push = ConsumerQueueManger.pushConsumerQueue(sendMessage, messageDurability);
.....
        return resp;
}
```


### 2.2.1 消息体存储
#### 存储格式
| ​
| Length | Data | Length | Data | ...... |
| --- | --- | --- | --- | --- | --- |
| 解释 | 下一条数据长度 | 消息内容 | 下一条数据长度 | 消息内容 | ...... |
| 占用 | int(4个字节) | Length长度 | int(4个字节) | Length长度 | ...... |

#### 消息内容
```java
public class SendMessage implements Serializable {
    private static final long serialVersionUID = -20210610L;
    private String msgId;
    private String topic;
    private String tag;
    private String key;
    private String body;
}
```
#### 简介

- 使用RandomAccessFile来进行存储，返回消息位点，以及偏移量
- RandomAccessFile可以在任意位置进行快速的读写
#### 存储代码
```java
public static synchronized MessageDurability save(String fileName,Object object) 
    throws IOException, InterruptedException{

    RandomAccessFile rws = new RandomAccessFile(fileName, "rw");
    FileChannel fileChannel = rws.getChannel();

    byte[] bytes = ObjectByteUtils.toByteArray(object);
    if (bytes==null){
        throw new FileException("文件转化异常：object not can cast bytes");
    }

    ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length+4);
    byteBuffer.putInt(bytes.length);
    byteBuffer.put(bytes);
    byteBuffer.flip();

    MessageDurability messageDurability = new MessageDurability();
    messageDurability.setLength(byteBuffer.limit());
    messageDurability.setOffset(fileChannel.size());

    fileChannel.position(fileChannel.size());
    fileChannel.write(byteBuffer);
    fileChannel.force(true);
    fileChannel.close();

    return messageDurability;
}
```
### 2.2.2 消息消费队列存储
#### 存储格式
| ​
| Offset | Length | TagHashcode | Index | ...... |
| --- | --- | --- | --- | --- | --- |
| 解释 | 偏移量 | 消息长度 | 消息tagHashCode | 这个队列里面的第几个消息 | ...... |
| 占用 | long(8个字节) | int(4个字节) | int(4个字节) | long(8个字节) | ...... |



## 2.3  消息拉取 PullExecutor
### 2.3.1 消息队列queue

- 消息在存储到** mq_n **之后，会将消息分配到 消息队列之后，然后消费者在拉取消息的时候，会指定queueId来进行拉取数据。
- 拉取消息的话，会首先读取queue的信息，读取出指定偏移量的n条数据的信息，然后去 **mq_n **去查询
```java
public List<PullMessage> pullMessage(Pull pull){
    //读取n条数据
  List<MessageDurability> data = 
      MessageDurabilityStorage.readMessageQueue(pull.getQueueId(),
                                                pull.getTopic(), 
                                                pull.getOffset());
  if (data.size()==0){
      return null;
  }
    //然后在去根据元信息读取消息
  return MessageStorage.readMessages(data);
}
```
## 2.4 消息消费位点保存 CommitOffsetExecutor
### 2.4.1 消费位点存储

- 消费位点存储使用JSON格式存储，格式为offSetMap下面的key代表消费者,Value代表每个队列的消费点
```java
{
    "offSetMap": {
        "CConsumer": {
            "0": 358,
            "1": 363,
            "2": 363,
            "3": 359
        },
        "AConsumer": {
            "0": 331,
            "1": 336,
            "2": 335,
            "3": 332
        },
        "RConsumer": {
            "0": 486,
            "1": 492,
            "2": 492,
            "3": 487
        },
        "DConsumer": {
            "0": 358,
            "1": 363,
            "2": 363,
            "3": 359
        },
        "BConsumer": {
            "0": 350,
            "1": 355,
            "2": 355,
            "3": 351
        }
    }
}
```
## 2.4 Topic信息返回 TopicDataExecutor

- 将Topic对应的队列数量，消费量等返回给客户端
```java
        messageQueueData.setTopic(data.getTopic());
        messageQueueData.setConsumerKey(data.getConsumerKey());
        messageQueueData.setQueueSize(topicListener.getQueueSize());
        messageQueueData.setOffSetMap(QueueOffsetStorage.getOffSetMap(data.getTopic(),data.getConsumerGroup()));
```
# 3. 客户端
## 3.1 消息发送

- 消息发送目前很简单，组装数据后直接调用netty的writeAndFlush，添加了一个 Listener 以及一个超时的判断。
- 目前实现比较简单，因为这里其实并不是MQ系统的重点
```java
    public static SendMessageResult sendMsg(SendMessage sendMessage){
        SendMessageResult sendMessageResult = new SendMessageResult();
        try {
            HsEecodeData hsEecodeData = new HsEecodeData();
           	....

            resultMap.put(hsReq.getReqId(), sendMessageResult);

            MessageClient.channelFuture
                    .channel()
                    .writeAndFlush(hsEecodeData)
                    .addListener((future)->{
                        if (future.isSuccess()) {
                            sendMessageResult.setSendDone(true);
                        } else {
                            sendMessageResult.setSendDone(false);
                            sendMessageResult.setRespDesc("消息发送失败");
                        }
                    });

            long nanosTimeout = TimeUnit.SECONDS.toNanos(3);
            final long deadline = System.nanoTime() + nanosTimeout;

            while (true) {
                if (nanosTimeout<0){
                    sendMessageResult.setRespDesc("发送超时");
                    sendMessageResult.setMessageResult(-1);
                    break;
                }
                if (sendMessageResult.getMessageResult() != null) {
                    break;
                }
                nanosTimeout = deadline - System.nanoTime();
            }
            return sendMessageResult;
        } catch (Exception e) {
            sendMessageResult.setMessageResult(-2);
            sendMessageResult.setSendDone(false);
            e.printStackTrace();
        }
        return sendMessageResult;
    }
```
## 3.2 消息消费
### 3.2.1 消费者初始化
#### 目前消费者存储结构
```java
Map<String, Map<String,AbstractConsumer>>

{
	"consumerGroup": {
    	"Topic": Consumer, 
        "Topic": Consumer, 
    },
	"consumerGroup": {
    	"Topic": Consumer, 
        "Topic": Consumer, 
    },
}

系统内有多个消费组，一个消费组内有多个Topic对应的消费者
```
#### 初始化消费者

- 消息管理器  ConsumerMessageQueue
- 注册拉取消息任务
- 注册执行器任务,异步的从消息管理器中拉取数据进行消费
- 注册定时任务,定时向服务端提交消费偏移量
```java
    public static void registeredConsumer(String topic,String consumerGroup){
        
        ThreadPoolExecutor executor = ExecutorService.getExecutor();
        //创建消费者
        ConsumerMessageQueue consumerMessageQueue = new ConsumerMessageQueue(topic,consumerGroup);
        //注册到管理器中
        consumerMessageQueueMap.put(consumerKey(topic,consumerGroup),consumerMessageQueue);
        //注册拉取消息任务
        executor.execute(new PullMessageTask(channelFuture , consumerGroup, consumerMessageQueue));
        //注册执行器任务
        executor.execute(new ExecutorMessageTask(channelFuture ,consumerMessageQueue));
        //定时任务
        channelFuture.channel().eventLoop().scheduleWithFixedDelay(()->{
            //定时任务,定时向服务端提交消费偏移量
            new Thread(new CommitOffsetTask(channelFuture,consumerMessageQueue)).start();
        },1, 3L, TimeUnit.SECONDS);

    }
```


#### 初始化消费位点

- 首先向服务发出获取对应topic对应消费组的 TopicData 的请求
```java
    public static void initConsumerQueue(String consumerGroup){

        consumerMessageQueueMap.forEach((consumerKey,queue)->{
            HsEecodeData hsEecodeData = new HsEecodeData();
            .....
            hsReq.setOperation(OperationEnum.TopicData.getOperation());
            hsEecodeData.setData(hsReq);
 			channelFuture.channel().writeAndFlush(hsEecodeData).sync();
        });
    }
```

- 然后根据服务端的返回同步消费数据
- 会根据返回的
```java
    public void initQueue(MessageQueueData messageQueueData){
        Integer queueSize = messageQueueData.getQueueSize();
        for (int i=0;i<queueSize;i++){
            queueMap.put(i,new ConcurrentLinkedQueue<>());
        }
        Map<Integer, Long> serverOffSetMap = messageQueueData.getOffSetMap();
        if (serverOffSetMap!=null&&serverOffSetMap.size()>0){
            offSetMap.putAll(serverOffSetMap);
            lastMessageMap.putAll(serverOffSetMap);
        }
    }
```
## 3.3  目前还没有做消费的负载均衡
## ​


