# Lock 和Condition

### > Lock 用于解决互斥问题，
- 阻塞队列队列中的节点都想获取锁
### > Condition 用于解决同步问题。

- 条件队列则是由condition形成的条件队列，线程被await操作挂起后就会被放入条件队列，这个队列中的节点都被挂起，他们都等待被signal进入阻塞队列再次获取锁
- 当前线程被await后，就会被挂起放入条件队列中



## Synchronized无法解决的问题,为什么需要Lock？

- synchronized 无法中断
- synchronized 无法设置超时
- synchronized 无法非阻塞的获取锁



### Lock的可见行

- Lock的可见性通过AQS的 state 变量来实现
- Lock 的加锁和解锁 都会操作 state 变量，而state 变量是有volitile修饰，根据haper-before原则，多个线程之间共享资源是可见的。 



### 可重入锁

- 同一个线程可以重复获取同一把锁
- 通过AQS的占用的线程id来实现，每次加锁 state +1 ,解锁 -1 



### 公平锁

- 公平锁，唤醒的策略就是谁等待的时间长，就唤醒谁，很公平；
- 非公平锁，则不提供这个公平保证，有可能等待时间短的线程反而先被唤醒。
- 通过CLH队列实现



### 读写锁

- 允许多个读线程同时访问，但是读写互斥，写写互斥
#### 实现原理

- 将AQS的state 分为了两个部分，高位和低位，高位表示读，低位表示写



- 如果当前没有写锁或读锁时，第一个获取锁的线程都会成功，无论该锁是写锁还是读锁
- 如果当前已经有了读锁，那么这时获取写锁将失败，获取读锁有可能成功也有可能失败
- 如果当前已经有了写锁，那么这时获取读锁或写锁，如果线程相同（可重入），那么成功；否则失败



- **释放锁**
   - 如果当前是写锁被占有了，只有当写锁的数据降为0时才认为释放成功；否则失败。因为只要有写锁，那么除了占有写锁的那个线程，其他线程即不可以获得读锁，也不能获得写锁
   - 如果当前是读锁被占有了，那么只有在写锁的个数为0时才认为释放成功。因为一旦有写锁，别的任何线程都不应该再获得读锁了，除了获得写锁的那个线程。





## Condition
### 原理
#### ConditionObject#await

1. 将当前线程构造为条件节点加入条件队列尾部
1. 释放锁
1. 挂起当前线程并等待进入阻塞队列
1. 线程被signal唤醒进入阻塞队列 
1. 进入阻塞队列自旋获取锁，其实到这里应该明白了，上一节中所谓的资源就是锁
#### ConditionObject#signal

1. 将节点从条件队列转移到阻塞队列后，然后将该节点自旋加入阻塞队列尾部，然后csa替换为状态为signal，然后执行unpark 唤醒线程



#### 
#### Condition的方法
| 方法 |  |  |
| --- | --- | --- |
| await() | 使当前线程加入 await() 等待队列中，并释放当锁，当其他线程调用signal()会重新请求锁。与Object.wait()类似。 | 此时调用thread.interrupt()会报错 |
| awaitUninterruptibly() | 调用该方法的前提是，当前线程已经成功获得与该条件对象绑定的重入锁，否则调用该方法时会抛出IllegalMonitorStateException.
调用该方法后，结束等待的唯一方法是其它线程调用该条件对象的signal()或signalALL()方法。等待过程中如果当前线程被中断，该方法仍然会继续等待，同时保留该线程的中断状态。  | 调用thread.interrupt()则不会报错 |
| await(long time, 
TimeUnit unit) | 与await()基本一致，唯一不同点在于，指定时间之内没有收到signal()或signalALL()信号或者线程中断时该方法会返回false;其它情况返回true |  |
| awaitUntil(Date deadline) | 适用条件与行为与awaitNanos(long nanosTimeout)完全一样，唯一不同点在于它不是等待指定时间，而是等待由参数指定的某一时刻。
 |  |
| signal() | 唤醒一个在 await()等待队列中的线程。与Object.notify()相似
 |  |
| signalAll() | 唤醒 await()等待队列中所有的线程。与object.notifyAll()相似
 |  |



### wait 和 lock.newCondition().await()的不同点
| 对比项 | synchronized | lock的condition |
| --- | --- | --- |
| 前置条件 | 获取对象锁 | 调用lock.lock() 获取锁
调用lock.newCondition() 获取Condition |
| 等待队列个数 | 一个 | 多个队列，
可以创建多个等待队列，例如在阻塞队列中，可以创建 队列空condition 和 队列满condition 两个 condition |
| 当前线程释放锁并进入等待队列，在等待过程中不响应中断 | 不支持 | 支持，使用awaitUninterruptibly()方法 |
| 当前线程释放锁并进入等待队列到未来指定时间 | 不支持 | 支持 |

#### 



















