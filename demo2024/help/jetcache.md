# JetCache是基于Java开发的统一API和使用简单的缓存框架

```

它提供的注解比Spring Cache中的注解更加强大。JetCache中的注释支持原生TTL，两级缓存和分布式自动刷新，也可以手动编写实例。
目前有四个实现：RedisCache、TairCache（在Github上不是开源的）、CaffeineCache（在内存中的）和一个简单的LinkedHashMapCache（在内存中的）
```

https://www.cnblogs.com/cao-lei/p/14506069.html


```

    @Cached 注解用于向缓存中放数据 ，适合GET/find查询操作

    @CacheRefresh 注解用于在指定时间后就重新向数据库查询数据并放到缓存中

    @CacheUpdate 注解用于在更新数据库数据时，同步更新到缓存中，适合update更新操作

   @CacheInvalidate 注解用于在删除数据库数据时，同步将缓存中的对应数据删除,适合delete删除操作
```