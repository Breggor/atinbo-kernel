# dislock-spring-boot-starter

#### 介绍
基于redisson实现的spring boot starter分布式锁框架,实现了可重入锁、公平锁、联锁、红锁、读写锁等常用锁的方式，并支持集群模式下的redis

#### 使用说明

1. 创建Spring Boot项目
2. 在Spring Boot的项目配置文件application.yml中添加相应的配置，如：
   ```
    dislock: 
        pattern: single #redis模式配置，single：单机模式，cluster:集群模式，replicated:云托管模式,sentinel:哨兵模式，master_slave：主从模式
        # 不同的redis模式对应不同的配置方式，single-server对应的就是单机模式，具体参数意义可参考redisson的配置参数说明
        single-server: 
            address: 127.0.0.1
            port: 6379
            password: 123456
   ```
3. 在启动类Application上使用@EnableDisLock注解开启
    ```
   @EnableDisLock
   @SpringBootApplication
   public class Application {
       public static void main(String[] args) {
           SpringApplication.run(MemberApplication.class, args);
           try {
               System.in.read();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```
4. 在需要使用分布式锁的方法上面使用@Lock注解，锁的关键字使用@Key，如:
   ```
    @DisLock
	public void hello(String ces, @Key String orderNo) {
		System.out.println("hello");
	}
   ```
   如果需要配置不同类型的锁，可以直接变更@Lock的参数值即可，默认是可重入锁
   @DisLock提供四个参数可以配置：
   1. lockType:锁类型 
   2. leaseTime:加锁时间
   3. waitTime:最长等待时间
   4. timeUnit:锁时长单位