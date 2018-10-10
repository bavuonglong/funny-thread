# Funny Thread

### Thread Safe

A piece of code is thread-safe if it manipulates shared data in a manner that guarantees safe execution by multi threads at the same time.


Some way to make our program thread-safe
1. synchronization ----> it will lock on Object or Class to make sure that only one thread is executing **the synchronized code**
2. use of Atomic Wrapper classes, such as AtomicInteger
3. use of Lock, such as ReentrantLock
4. using thread-safe collection classes, such as ConcurrentHashMap
5. using volatile keyword on variables to make every thread read the data from memory, not from thread cache.


