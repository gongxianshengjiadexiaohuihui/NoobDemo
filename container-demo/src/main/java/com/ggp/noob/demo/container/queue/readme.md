# Queue和List的区别到底在哪里
    Queue添加了对多线程友好的API offer  peek  poll  锁
    BlockingQueue又添加了对线程友好的put和take，这两个实现了阻塞操作