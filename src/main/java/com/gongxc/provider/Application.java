package com.gongxc.provider;

import com.gongxc.impl.GreetingsServiceImpl;
import com.gongxc.inter.GreetingsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

public class Application {
    //private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) throws Exception {
//        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
//        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
//        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
//        service.setInterface(GreetingsService.class);
//        service.setRef(new GreetingsServiceImpl());
//        service.export();
//        new CountDownLatch(1).await();

        GreetingsService greetingsService = new GreetingsServiceImpl();

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("provider");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://192.168.0.50:2181");
        //        registry.setUsername("aaa");
        //        registry.setPassword("bbb");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setHost("192.168.0.50");
        protocol.setPort(20880);
        //protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<GreetingsService> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(GreetingsService.class);
        service.setRef(greetingsService);
        service.setVersion("1.0.0");

// 暴露及注册服务
        service.export();

        System.out.println("dubbo service started");

    }
}
