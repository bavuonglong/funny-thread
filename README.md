# Funny Thread

### Thread Safe

A piece of code is thread-safe if it manipulates shared data in a manner that guarantees safe execution by multi threads at the same time.


Some way to make our program thread-safe
1. synchronization ----> it will lock on Object or Class to make sure that only one thread is executing **the synchronized code**
2. use of Atomic Wrapper classes, such as AtomicInteger
3. use of Lock, such as ReentrantLock
4. using thread-safe collection classes, such as ConcurrentHashMap
5. using volatile keyword on variables to make every thread read the data from memory, not from thread cache.

### Lock API
1. Lock API provides all the functionality of synchronized keyword, with additional ways to create Condition for locking, timeout for waiting
a lock. Some important method is *lock()*, *unLock()*, *tryLock()* and *lockInterruptibly()*. With synchronized keyword, we can end up with waiting infinite due to it does not provide time out mechanism.
2. It is mandatory for using try finally block to make sure lock will be release, unlike synchronized.
3. Synchronization can cover only method or block, whereas Lock can lock on one method and unlock on another method
4. Synchronization does not provide fairness while Lock has, so that longest waiting thread gets the lock first
5. We can create different condition lock, so that different thread can use different condition for locking

### Dead lock
Dead lock is a programming situation when two or more actions are waiting each other to finish
To avoid dead lock, we should:
1. Avoid nested locks
2. If nested locks is necessary, then consider to lock different lock on the same order.


