# Thread_Testing

測試Multi-Thread執行方法

1.ThreadPoolExecutor:
以當前device為online的核心數量來初始化Thread Pool大小

2.Executors.newCachedThreadPool()
未指定Thread Pool大小預設實作上為0, Pool Size為Integer.MAX_VALUE, 因此有可能在使用時會先經歷Thread初始化的時間
