package com.gongxc.consumer;

import com.gongxc.inter.GreetingsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class Application {
//    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) {
//        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
//        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
//        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
//        reference.setInterface(GreetingsService.class);
//        GreetingsService service = reference.get();
//        String message = service.sayHi("dubbo");
//        System.out.println(message);
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("consumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://localhost:2181");
//        registry.setUsername("aaa");
//        registry.setPassword("bbb");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<GreetingsService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(GreetingsService.class);
        reference.setVersion("1.0.0");

        // 和本地bean一样使用xxxService
        GreetingsService greetingsService = reference.get(); //
        greetingsService.sayHello();
        System.out.println(greetingsService.sayHi("你好"));
    }
}
